/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
public interface ValidateForCheckoutChain {

	boolean validate(Order order);

}
