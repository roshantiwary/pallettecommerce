/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;

/**
 * @author amall3
 *
 */
@Component
public class ValidateChain {

	@Autowired
	private List<ValidateForCheckoutChain> validators;

	@PostConstruct
	public void init() {
		Collections.sort(validators, AnnotationAwareOrderComparator.INSTANCE);
	}

	/**
	 * 
	 * @param order
	 */
	public boolean validateForCheckout(Order order) {
		boolean success = Boolean.TRUE;
		for (ValidateForCheckoutChain validator : validators) {
			if (!validator.validate(order)) {
				success = Boolean.FALSE;
				break;
			}
		}
		return success;
	}
}
