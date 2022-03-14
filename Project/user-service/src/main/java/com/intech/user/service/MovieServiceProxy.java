/**
 * 
 */
package com.intech.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.intech.user.dto.MovieDTO;

/**
 * @author Akash Budhwani
 *
 */
@FeignClient(name="movie-service")
public interface MovieServiceProxy {
	
	@GetMapping(value = "/api/movies/movie/{movieId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public MovieDTO getMovieDetails(@PathVariable("movieId") long movieId);

}
