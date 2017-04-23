/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pallette.domain.Order;

/**
 * This component is part of Submit Order Validation Chain and validates the
 * profile associated with the Order.
 * 
 * @author amall3
 *
 */

@Component
public class ValidateOrderProfile extends ValidateOrderForSubmitOrder {

	private static final Logger log = LoggerFactory.getLogger(ValidateOrderProfile.class);

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public boolean validate(Order order) {

		log.debug("Inside ValidateOrderProfile.validate()");
		boolean isValidationSuccess = Boolean.FALSE;

		String profileId = order.getProfileId();
		if (StringUtils.isEmpty(profileId)) {
			log.debug("No Profile is set in Order");
			return isValidationSuccess;
		}

		isValidationSuccess = Boolean.TRUE;

		return isValidationSuccess;
	}

}
