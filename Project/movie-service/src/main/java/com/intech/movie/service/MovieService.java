package com.intech.movie.service;

import java.util.List;
import java.util.Optional;

import com.intech.movie.dto.MovieDTO;
import com.intech.movie.model.Movie;

public interface MovieService {
	
	public Movie saveMovie(MovieDTO movieDTO);
	
	public Optional<Movie> findMovieById(long movieId);
	
	public Optional<Movie> findMovieByMovieNameIgnoreCase(String movieName);
	 
	public List<Movie> getAllMovieDetails();
	
	public void deleteMovieDetailsById(long movieId);
	
	public Movie updateMovieDetails(MovieDTO movieDTO, Movie movie);
	
	public MovieDTO convertEntitytoDTO(Movie movie);
	
	public Movie convertDtoToEntity(MovieDTO movieDTO);

}
