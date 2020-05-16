/**
 * 
 */
package com.pallette.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */

//@Transactional
public interface OrderRepository extends PagingAndSortingRepository<Order, String> {

	/**
	 * Method that queries the Mongo DB for a particular Order Id.
	 */
//	public Order findOne(Long orderId);

	/**
	 * The void delete(Order order) method deletes the order entry that is given
	 * as a method parameter.
	 */
	public void delete(Order order);

	/**
	 * The Order save(Order order) method saves a new Order entry to the
	 * database and returns the the saved Order entry.
	 */
	
	public List<Order> findOrderByStateAndProfileId(String state, String profileId);
	
}
