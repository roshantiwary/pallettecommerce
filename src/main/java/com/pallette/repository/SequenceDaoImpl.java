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

import com.pallette.constants.SequenceConstants;
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
	
	private static final Logger log = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private MongoOperations mongoOperation;

	@Override
	public String getNextOrderSequenceId(String key) throws SequenceException {
		
		log.debug("Inside SequenceDaoImpl.getNextOrderSequenceId()");
		String sequenceId = "";

		// get sequence id from the "sequence" document.
		Query query = new Query(Criteria.where(SequenceConstants._ID).is(key));

		// increase sequence id by 1
		Update update = new Update();
		//order_seq being the property name in "sequence" document.
		update.inc(SequenceConstants.ORDER_SEQ, 1);

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

		sequenceId =  Long.toString(seqId.getOrderSeq());
		
		String orderSequenceId = new StringBuilder().append(SequenceConstants.ORDER).append(sequenceId).toString();
		return orderSequenceId;
	}

	@Override
	public String getNextProfileSequenceId(String key) throws SequenceException {
		
		log.debug("Inside SequenceDaoImpl.getNextProfileSequenceId()");
		String sequenceId = "";

		// get sequence id from the "sequence" document.
		Query query = new Query(Criteria.where(SequenceConstants._ID).is(key));

		// increase sequence id by 1
		Update update = new Update();
		//profile_seq being the property name in "sequence" document.
		update.inc(SequenceConstants.PROFILE_SEQ, 1);

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

		sequenceId =  Long.toString(seqId.getProfileSeq());
		
		String orderSequenceId = new StringBuilder().append(SequenceConstants.PROFILE).append(sequenceId).toString();
		return orderSequenceId;
	}

}
