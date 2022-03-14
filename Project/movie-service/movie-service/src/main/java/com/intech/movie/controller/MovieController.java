/**
 * 
 */
package com.intech.movie.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.intech.movie.dto.MovieDTO;
import com.intech.movie.model.Movie;
import com.intech.movie.service.MovieService;
import com.intech.movie.service.RatingsServiceProxy;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Akash Budhwani
 *
 */
@Slf4j
@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private RatingsServiceProxy ratingsProxy;

	@PostMapping(value = "/movie", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveMovieDetails(@RequestBody MovieDTO movieDTO) {

		log.info("Inside MovieController :: saveMovieDetails()");
		try {

			if (movieService.findMovieByMovieNameIgnoreCase(movieDTO.getMovieName()).isPresent())
				return new ResponseEntity<>("Movie data already exists", HttpStatus.CREATED);

			Movie movie = movieService.saveMovie(movieDTO);
			MovieDTO movieDTOdetails = movieService.convertEntitytoDTO(movie);
			return new ResponseEntity<>(movieDTOdetails, HttpStatus.CREATED);
		} catch (Exception ex) {
			log.error("Error in MovieController :: saveMovieDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}

	@GetMapping(value = "/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Retry(name = "getMovieDetails", fallbackMethod = "getMovieDetailsFallback")
	public MovieDTO getMovieDetails(@PathVariable("movieId") long movieId) {

		log.info("MovieController :: getMovieDetails()");
		try {
			Optional<Movie> movieDetails = movieService.findMovieById(movieId);
			//new RestTemplate().getForEntity("http://localhost:10011/api/users/user/8", String.class);
			MovieDTO movieDTOdetails = null;
			if (movieDetails.isPresent()) {
				Movie movie = movieDetails.get();
				movie.setMovieRatings(ratingsProxy.getMovieRatings(movie.getId()).getRatings());
				movieDTOdetails = movieService.convertEntitytoDTO(movie);
				return movieDTOdetails;
			}
			return movieDTOdetails;
		} catch (Exception ex) {
			log.error("Error in MovieController :: getMovieDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
	
	public MovieDTO getMovieDetailsFallback(Exception ex) {
		return new MovieDTO(0, "", "", "", 0.0f);
	}

	@GetMapping(value = "/allMovies", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MovieDTO> getAllMovieDetails() {

		log.info("MovieController :: getAllMovieDetails()");
		try {
			List<Movie> movieDetails = movieService.getAllMovieDetails();
			List<MovieDTO> returnValue = new ArrayList<>();
			if (!movieDetails.isEmpty()) {
				returnValue = movieDetails.stream().map(movieObj ->{
					movieObj.setMovieRatings(ratingsProxy.getMovieRatings(movieObj.getId()).getRatings());
					return  movieService.convertEntitytoDTO(movieObj);
				}).collect(Collectors.toList());
				return returnValue;
			}
			return returnValue;

		} catch (Exception ex) {
			log.error("Error in MovieController :: getAllMovieDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
	
	@DeleteMapping(value = "/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteMovieDetails(@PathVariable("movieId") long movieId) {

		log.info("MovieController :: deleteMovieDetails()");
		try {
			Optional<Movie> movieDetails = movieService.findMovieById(movieId);
			if (movieDetails.isPresent()) {
				movieService.deleteMovieDetailsById(movieId);
				return new ResponseEntity<>("Movie has been deleted", HttpStatus.OK);
			}
			return new ResponseEntity<>("Movie details does not exists", HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error in MovieController :: deleteMovieDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
	
	@PutMapping(value="/movie", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateMovieDetails(@RequestBody MovieDTO movieDTO) {

		log.info("MovieController :: updateMovieDetails()");
		try {
			Optional<Movie> movieDetails = movieService.findMovieById(movieDTO.getId());
			if (movieDetails.isPresent()) {
				Movie movie = movieService.updateMovieDetails(movieDTO, movieDetails.get());
				MovieDTO movieDTOdetails = movieService.convertEntitytoDTO(movie);
				return new ResponseEntity<>(movieDTOdetails, HttpStatus.OK);
			}
			return new ResponseEntity<>("Movie details does not exists", HttpStatus.OK);
		} catch (Exception ex) {
			log.error("Error in MovieController :: updateMovieDetails() " + ex.getMessage());
			throw new RuntimeException(this.getClass().getName());
		}
	}
}
