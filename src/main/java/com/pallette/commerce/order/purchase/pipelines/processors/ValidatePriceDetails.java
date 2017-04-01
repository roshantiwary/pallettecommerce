/**
 * 
 */
package com.pallette.commerce.order.purchase.pipelines.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.pallette.domain.Order;
import com.pallette.domain.OrderPriceInfo;

/**
 * @author amall3
 *
 */
@Component
public class ValidatePriceDetails extends ValidateOrderForCheckout {

	private static final Logger log = LoggerFactory.getLogger(ValidatePriceDetails.class);

	@Override
	public int getOrder() {
		return 1;
	}

	@Override
	public boolean validate(Order order) {
		log.debug("Inside ValidatePriceDetails.validate()");
		boolean isValidationSuccess=  Boolean.FALSE;
		
		OrderPriceInfo orderPriceInfo = order.getOrderPriceInfo();
		if (null == orderPriceInfo)
			return isValidationSuccess;
		
		double orderSubTotal = orderPriceInfo.getRawSubTotal();
		if(orderSubTotal < 0.0)
			return isValidationSuccess;
		
		
		return Boolean.TRUE;
	}

}
