/**
 * 
 */
package com.intech.user.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.intech.user.model.DatabaseSequence;

/**
 * @author Akash Budhwani
 *
 */
@Service
public class SequenceGeneratorService {
	
	
	@Autowired
	private MongoOperations mongoOperations;
	
	public long generateSequence(String seqName) {
		
		
		Query query = new Query(Criteria.where("_id").is(seqName));

		  //increase sequence id by 1
		  Update update = new Update();
		  update.inc("seq", 1);

		  //return new increased id
		  FindAndModifyOptions options = new FindAndModifyOptions();
		  options.returnNew(true).upsert(true);
		
		DatabaseSequence counter = mongoOperations.findAndModify(query, update, options, DatabaseSequence.class);
		
		return !Objects.isNull(counter) ? counter.getSeq() : 1;	
	}

}
