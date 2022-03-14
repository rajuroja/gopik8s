/**
 * 
 */
package com.intech.rating.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.intech.rating.dto.MovieRatingsDTO;
import com.intech.rating.dto.RatingsDTO;
import com.intech.rating.model.Ratings;
import com.intech.rating.repository.RatingsRepository;

/**
 * @author Akash Budhwani
 *
 */
@Service
public class RatingsServiceImpl implements RatingsService{

	@Autowired
	private RatingsRepository ratingsRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Override
	public Ratings saveMovieRatings(RatingsDTO ratingsDTO) {
		Ratings ratings = convertDtoToEntity(ratingsDTO);
		ratings.setId(sequenceGeneratorService.generateSequenc(Ratings.SEQUENCE));
		return ratingsRepository.save(ratings);
	}

	@Override
	public MovieRatingsDTO getMovieRatingByMovieId(long movieId) {
		GroupOperation groupByMovieId = Aggregation.group("movieId")
				  .avg("ratings").as("ratings");
		MatchOperation filterMovieId = Aggregation.match(new Criteria("movieId").is(movieId));
		Aggregation aggregation = Aggregation.newAggregation(filterMovieId,groupByMovieId);

		return mongoTemplate.aggregate(aggregation, "ratings", MovieRatingsDTO.class).getUniqueMappedResult();
	}


	@Override
	public List<Ratings> getMovieRatingsByUserId(long userId) {
		return ratingsRepository.findByUserId(userId);
	}

	@Override
	public Ratings convertDtoToEntity(RatingsDTO ratingsDTO) {
		Ratings ratings = new Ratings();
		ratings.setId(ratingsDTO.getId());
		ratings.setMovieId(ratingsDTO.getMovieId());
		ratings.setUserId(ratingsDTO.getUserId());
		ratings.setRatings(ratingsDTO.getRatings());
		ratings.setDate(new Date());
		return ratings;
	}

	@Override
	public RatingsDTO convertEntityToDTO(Ratings ratings) {
		RatingsDTO ratingsDTO = new RatingsDTO();
		ratingsDTO.setId(ratings.getId());
		ratingsDTO.setMovieId(ratings.getMovieId());
		ratingsDTO.setUserId(ratings.getUserId());
		ratingsDTO.setRatings(ratings.getRatings());
		ratingsDTO.setDate(new Date());
		return ratingsDTO;
	}

}
