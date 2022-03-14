/**
 * 
 */
package com.intech.rating.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.intech.rating.model.RatingsSequence;

/**
 * @author Akash Budhwani
 *
 */
@Service
public class SequenceGeneratorService {
	
	@Autowired
	MongoOperations mongoOperations;
	
	public long generateSequenc(String seqName) {
		
		Query query = new Query(Criteria.where("_id").is(seqName));
		
		Update update = new Update();
		update.inc("seq", 1);
		
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true).upsert(true);
		
		RatingsSequence counter = mongoOperations.findAndModify(query, update, options, RatingsSequence.class);
		
		return !Objects.isNull(counter) ? counter.getSeq() : 1;	
		
	}
	
	

}
