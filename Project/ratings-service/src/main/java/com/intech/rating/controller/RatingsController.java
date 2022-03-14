/**
 * 
 */
package com.intech.rating.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.intech.rating.dto.MovieRatingsDTO;
import com.intech.rating.dto.RatingsDTO;
import com.intech.rating.model.Ratings;
import com.intech.rating.service.RatingsService;
import com.intech.rating.service.UserServiceProxy;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Akash Budhwani
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/ratings")
public class RatingsController {

	@Autowired
	private RatingsService ratingsService;

	@Autowired
	private UserServiceProxy userProxy;

	@PostMapping(value = "/rating", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addMovieRatings(@RequestBody RatingsDTO ratingsDTO) {
		try {
			log.info("Inside RatingsController :: addMovieRatings()");

			if (userProxy.getUserDetails(ratingsDTO.getUserId()) != null) {
				Ratings ratings = ratingsService.saveMovieRatings(ratingsDTO);
				RatingsDTO ratingsDTOdetails = ratingsService.convertEntityToDTO(ratings);
				return new ResponseEntity<>(ratingsDTOdetails, HttpStatus.CREATED);
			}
			return new ResponseEntity<>("User does not exists", HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error in RatingsController :: addMovieRatings() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}

	@GetMapping(value = "/userRatings/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Retry(name = "getUserRatings", fallbackMethod ="getUserRatingsFallback" )
	//@CircuitBreaker(name = "getUserRatings", fallbackMethod ="getUserRatingsFallback" )
	//@RateLimiter(name = "default")
	public List<RatingsDTO> getUserRatings(@PathVariable("userId") long userId) {
		try {
			log.info("Inside RatingsController :: getUserRatings()");
			List<Ratings> usrRatings = ratingsService.getMovieRatingsByUserId(userId);
			List<RatingsDTO> returnValue = new ArrayList<>();
			if (!usrRatings.isEmpty()) {
				returnValue = usrRatings.stream().map(ratingObj -> {
					return ratingsService.convertEntityToDTO(ratingObj);
				}).collect(Collectors.toList());

				return returnValue;
			}
			return returnValue;
		} catch (Exception ex) {
			log.error("Error in RatingsController :: getUserRatings() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
	
	public List<RatingsDTO> getUserRatingsFallback(Exception ex) {
		return Arrays.asList(
				new RatingsDTO(0,0,0,0,new Date()));
	}

	@GetMapping(value = "/movieRatings/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Retry(name = "getMovieRatings", fallbackMethod ="getMovieRatingsFallback" )
	public MovieRatingsDTO getMovieRatings(@PathVariable("movieId") long movieId) {
		try {
			log.info("Inside RatingsController :: getMovieRatings()");
			MovieRatingsDTO movieRatingsDTO = ratingsService.getMovieRatingByMovieId(movieId);
			if(movieRatingsDTO == null)
			{
				return new MovieRatingsDTO(movieId, 0.0f);
			}
			return movieRatingsDTO;
		} catch (Exception ex) {
			log.error("Error in RatingsController :: getMovieRatings() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
	
	public MovieRatingsDTO getMovieRatingsFallback(Exception ex) {
		return new MovieRatingsDTO(0, 0.0f);
	}

}
