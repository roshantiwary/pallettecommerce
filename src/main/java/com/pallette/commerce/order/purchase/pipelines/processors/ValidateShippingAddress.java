/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.Address;
import com.pallette.domain.Order;
import com.pallette.domain.ShippingGroup;

/**
 * @author amall3
 *
 */
@Component
public class ValidateShippingAddress extends ValidateOrderForSubmitOrder {

	private static final Logger log = LoggerFactory.getLogger(ValidateShippingAddress.class);

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public boolean validate(Order order) {

		log.debug("Inside ValidateShippingAddress.validate()");
		boolean isValidationSuccess = Boolean.FALSE;

		List<ShippingGroup> shippingGroups = order.getShippingGroups();
		if (null == shippingGroups) {
			log.debug("No Shipping Grp.");
			return isValidationSuccess;
		}

		if (shippingGroups.size() < 1)
			return isValidationSuccess;

		for (ShippingGroup shipGrp : shippingGroups) {
			
			if (CommerceConstants.HARD_GOOD_SHIPPING_GROUP.equalsIgnoreCase(shipGrp.getShippingGroupType())) {

				Address address = shipGrp.getAddress();
				if (null == address) {
					log.debug("Address Object is Not Set");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getAddress1())) {
					log.debug("Address1 is Empty");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getEmail())) {
					log.debug("Email is Empty");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getFirstName())) {
					log.debug("First Name is Empty");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getCity())) {
					log.debug("City is Empty");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getState())) {
					log.debug("State is Empty");
					return isValidationSuccess;
				}

				if (StringUtils.isEmpty(address.getPostalCode()))
					return isValidationSuccess;

				if (StringUtils.isEmpty(address.getPhoneNumber())) {
					log.debug("Phone Number is Empty");
					return isValidationSuccess;
				}
			}
		}

		isValidationSuccess = Boolean.TRUE;
		return isValidationSuccess;
	}

}
