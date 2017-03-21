/**
 * 
 */
package com.pallette.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.pallette.domain.SequenceId;
import com.pallette.exception.SequenceException;
import com.pallette.service.OrderService;

/**
 * <p>
 * Implementation class for generating sequential Order Id. This class consults
 * Mongo document "sequence" for getting the sequence id .
 * </p>
 * 
 * @author amall3
 *
 */
@Repository
public class SequenceDaoImpl implements SequenceDao {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public String getNextSequenceId(String key) throws SequenceException {
		
		logger.debug("Inside SequenceDaoImpl.getNextSequenceId()");
		String sequenceId = "";

		// get sequence id from the "sequence" document.
		Query query = new Query(Criteria.where("_id").is(key));

		// increase sequence id by 1
		Update update = new Update();
		//seq being the property name in "sequence" document.
		update.inc("seq", 1);

		// return new increased id
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.returnNew(true);

		SequenceId seqId = mongoOperation.findAndModify(query, update, options, SequenceId.class);

		// if no id, throws SequenceException
		// optional, just a way to tell user when the sequence id is failed to
		// generate.
		if (seqId == null) {
			throw new SequenceException("Unable to get sequence id for key : " + key);
		}

		sequenceId =  Long.toString(seqId.getSeq());
		
		String orderSequenceId = new StringBuilder().append("pe").append(sequenceId).toString();
		return orderSequenceId;
	}

}
