/**
 * 
 */
package com.intech.movie.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.intech.movie.model.Movie;

/**
 * @author Akash Budhwani
 *
 */
@Repository
public interface MovieRepository extends MongoRepository<Movie, Long> {

	Optional<Movie> findByMovieName(String movieName);

	Optional<Movie> findByMovieNameIgnoreCase(String movieName);

}
