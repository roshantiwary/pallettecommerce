/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
public interface ValidateForSubmitOrderChain {

	boolean validate(Order order);

}
