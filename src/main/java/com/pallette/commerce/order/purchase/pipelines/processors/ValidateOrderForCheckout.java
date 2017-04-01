/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
@Component
public abstract class ValidateOrderForCheckout implements ValidateForCheckoutChain, Ordered {

	private static final Logger log = LoggerFactory.getLogger(ValidateOrderForCheckout.class);

	/**
	 * 
	 */
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	public abstract boolean validate(Order order);
}
