/**
 * 
 */
package com.intech.rating.service;

import java.util.List;

import com.intech.rating.dto.MovieRatingsDTO;
import com.intech.rating.dto.RatingsDTO;
import com.intech.rating.model.Ratings;

/**
 * @author Akash Budhwani
 *
 */
public interface RatingsService {
	
	public Ratings saveMovieRatings(RatingsDTO ratingsDTO);
		
	public MovieRatingsDTO getMovieRatingByMovieId(long movieId);
	
	public List<Ratings> getMovieRatingsByUserId(long userId);
	
	public Ratings convertDtoToEntity(RatingsDTO ratingsDTO);
	
	public RatingsDTO convertEntityToDTO(Ratings ratings);

}
