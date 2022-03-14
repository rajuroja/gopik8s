/**
 * 
 */
package com.intech.rating.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.intech.rating.dto.MovieRatingsDTO;
import com.intech.rating.model.Ratings;

/**
 * @author Akash Budhwani
 *
 */
public interface RatingsRepository extends MongoRepository<Ratings, Long> {

	@Aggregation(pipeline = { "[{$match : { 'movieId' : '$?0' }},{$group: { _id: '$movieId', total: {$avg: $ratings }}}]" })
	MovieRatingsDTO avgRatings(long movieId);

	Optional<Ratings> findByMovieId(long movieId);

	List<Ratings> findByUserId(long userId);

}
