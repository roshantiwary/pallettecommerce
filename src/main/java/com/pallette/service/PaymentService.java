/**
 * 
 */
package com.pallette.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.commerce.contants.PaymentConstants;
import com.pallette.domain.Order;

/**
 * <p>
 * Service Class created for Payment Processing. This class is responsible for
 * processing the response received from 3rd Part Payment Integrations.
 * </p>
 * 
 * @author amall3
 *
 */
@Service
public class PaymentService {

	private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
	
	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private OrderService orderService;

	/**
	 * 
	 * @param parameterNames
	 * @param model 
	 */
	public void processPaymentResponse(Map<String, String> returnedValueMap, Model model) {
		
		log.debug("Inside PaymentService.processPaymentResponse()");

		if (null == returnedValueMap || returnedValueMap.isEmpty())
			return;
		
		//Populate and Return the Model Object.
		
		//Populate the shipment details.
		model.addAttribute(PaymentConstants.FIRSTNAME, returnedValueMap.get(PaymentConstants.FIRSTNAME));
		model.addAttribute(PaymentConstants.LASTNAME, returnedValueMap.get(PaymentConstants.LASTNAME));
		model.addAttribute(PaymentConstants.EMAIL, returnedValueMap.get(PaymentConstants.EMAIL));
		model.addAttribute(PaymentConstants.PHONE, returnedValueMap.get(PaymentConstants.PHONE));
		model.addAttribute(PaymentConstants.ADDRESS1, returnedValueMap.get(PaymentConstants.ADDRESS1));
		model.addAttribute(PaymentConstants.ADDRESS2, returnedValueMap.get(PaymentConstants.ADDRESS2));
		model.addAttribute(PaymentConstants.CITY, returnedValueMap.get(PaymentConstants.CITY));
		model.addAttribute(PaymentConstants.STATE, returnedValueMap.get(PaymentConstants.STATE));
		model.addAttribute(PaymentConstants.COUNTRY, returnedValueMap.get(PaymentConstants.COUNTRY));
		model.addAttribute(PaymentConstants.ZIPCODE, returnedValueMap.get(PaymentConstants.ZIPCODE));
		
		String submittedOrderId = returnedValueMap.get(PaymentConstants.UDF1);
		if (!StringUtils.isEmpty(submittedOrderId)) {
			 model.addAttribute(CommerceContants.ORDER_ID, returnedValueMap.get(PaymentConstants.UDF1));
			 
			Query query = new Query();
			query.addCriteria(Criteria.where(CommerceContants._ID).is(submittedOrderId));
			Order orderItem = mongoOperation.findOne(query, Order.class);
			if (null != orderItem) {
				model.addAttribute(PaymentConstants.ITEM_LIST, orderService.populateItemDetails(orderItem));
			}
		}
	    
		model.addAttribute(PaymentConstants.AMOUNT, returnedValueMap.get(PaymentConstants.AMOUNT));
		model.addAttribute(PaymentConstants.MODE, returnedValueMap.get(PaymentConstants.MODE));
	}
	

}
