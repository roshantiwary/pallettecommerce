/**
 * 
 */
package com.pallette.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */

@Transactional
public interface OrderRepository extends MongoRepository<Order, String> {

	/**
	 * Method that queries the Mongo DB for a particular Order Id.
	 */
	public Order findOne(String orderId);

	/**
	 * The void delete(Order order) method deletes the order entry that is given
	 * as a method parameter.
	 */
	public void delete(Order order);

	/**
	 * The Order save(Order order) method saves a new Order entry to the
	 * database and returns the the saved Order entry.
	 */
	public Order save(Order order);
	
}
