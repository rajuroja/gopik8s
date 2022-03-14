/**
 * 
 */
package com.intech.movie.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.intech.movie.dto.MovieDTO;
import com.intech.movie.model.Movie;
import com.intech.movie.repository.MovieRepository;

/**
 * @author Akash Budhwani
 *
 */
@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Override
	public Movie saveMovie(MovieDTO movieDTO) {
		Movie movie = convertDtoToEntity(movieDTO);
		movie.setId(sequenceGeneratorService.generateSequenc(Movie.SEQUENCE));
		return movieRepository.save(movie);
	}

	@Override
	public Optional<Movie> findMovieById(long movieId) {
		return movieRepository.findById(movieId);
	}

	@Override
	public Optional<Movie> findMovieByMovieNameIgnoreCase(String movieName) {
		return movieRepository.findByMovieNameIgnoreCase(movieName);
	}

	@Override
	public List<Movie> getAllMovieDetails() {
		return movieRepository.findAll();
	}

	@Override
	public void deleteMovieDetailsById(long movieId) {
		 movieRepository.deleteById(movieId);
	}

	@Override
	public Movie updateMovieDetails(MovieDTO movieDTO, Movie movie) {
		movie.setId(movieDTO.getId());
		movie.setMovieName(movieDTO.getMovieName());
		movie.setOverview(movieDTO.getOverview());
		movie.setRelaseYear(movieDTO.getRelaseYear());
		return movieRepository.save(movie);
	}

	@Override
	public MovieDTO convertEntitytoDTO(Movie movie) {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(movie.getId());
		movieDTO.setMovieName(movie.getMovieName());
		movieDTO.setOverview(movie.getOverview());
		movieDTO.setRelaseYear(movie.getRelaseYear());
		movieDTO.setMovieRatings(movie.getMovieRatings());
		return movieDTO;
	}

	@Override
	public Movie convertDtoToEntity(MovieDTO movieDTO) {
		Movie movie = new Movie();
		movie.setId(movieDTO.getId());
		movie.setMovieName(movieDTO.getMovieName());
		movie.setOverview(movieDTO.getOverview());
		movie.setRelaseYear(movieDTO.getRelaseYear());
		movie.setMovieRatings(movieDTO.getMovieRatings());
		return movie;
	}

}
