/**
 * 
 */
package com.intech.movie.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.intech.movie.dto.MovieRatingsDTO;


/**
 * @author Akash Budhwani
 *
 */
@FeignClient(name="ratings-service")
public interface RatingsServiceProxy {
	@GetMapping(value = "/api/ratings/movieRatings/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MovieRatingsDTO getMovieRatings(@PathVariable("movieId") long movieId);
}
