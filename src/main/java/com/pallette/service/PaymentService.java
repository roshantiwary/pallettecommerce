/**
 * 
 */
package com.pallette.service;

import java.util.Date;
import java.util.List;
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

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.commerce.contants.PaymentConstants;
import com.pallette.domain.Order;
import com.pallette.domain.PaymentGroup;
import com.pallette.domain.PaymentStatus;
import com.pallette.repository.OrderRepository;

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
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderService orderService;

	/**
	 * Method that processes the payment response and then submits the Order.
	 * It sets the Payment Grp , Payment Status attributes from the Payment Response.
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
			 model.addAttribute(CommerceConstants.ORDER_ID, returnedValueMap.get(PaymentConstants.UDF1));
			 
			Query query = new Query();
			query.addCriteria(Criteria.where(CommerceConstants._ID).is(submittedOrderId));
			Order orderItem = mongoOperation.findOne(query, Order.class);
			if (null != orderItem) {
				model.addAttribute(PaymentConstants.ITEM_LIST, orderService.populateItemDetails(orderItem));
				
				persistOrderDetails(orderItem , returnedValueMap);
				persistPaymentDetails(orderItem , returnedValueMap);
				orderRepository.save(orderItem);
			}
		}
	    
		model.addAttribute(PaymentConstants.AMOUNT, returnedValueMap.get(PaymentConstants.AMOUNT));
		model.addAttribute(PaymentConstants.MODE, returnedValueMap.get(PaymentConstants.MODE));
	}

	/**
	 * 
	 * @param orderItem
	 * @param returnedValueMap 
	 */
	private void persistPaymentDetails(Order orderItem, Map<String, String> returnedValueMap) {

		log.debug("Inside PaymentService.persistPaymentDetails() and Order Id is: ", orderItem.getId());
		
		List<PaymentGroup> paymentGroups = orderItem.getPaymentGroups();
		if (null != paymentGroups && !paymentGroups.isEmpty()) {
			for (PaymentGroup payGroup : paymentGroups) {
				
				double amount = Double.parseDouble(returnedValueMap.get(PaymentConstants.AMOUNT));
				payGroup.setAmountDebited(amount);
				
				double discount = Double.parseDouble(returnedValueMap.get(PaymentConstants.DISCOUNT));
				payGroup.setDiscount(discount);
				
				double netAmountDebit = Double.parseDouble(returnedValueMap.get(PaymentConstants.NET_AMOUNT_DEBIT));
				payGroup.setNetAmountDebit(netAmountDebit);
				
				payGroup.setEncryptedPaymentId(returnedValueMap.get(PaymentConstants.ENCRYPTED_PAYMENT_ID));
				payGroup.setMihpayid(returnedValueMap.get(PaymentConstants.MIHPAYID));
				payGroup.setMode(returnedValueMap.get(PaymentConstants.MODE));
				payGroup.setSubmittedDate(new Date());
				payGroup.setState(PaymentConstants.DEBITED);
				payGroup.setPaymentMethod(PaymentConstants.PAYU);
				payGroup.setPaymentGroupType(PaymentConstants.PAYU);
				
				//Set the Payment Status to Payment Group.
				PaymentStatus debitStatus = new PaymentStatus();
				String status = returnedValueMap.get(PaymentConstants.STATUS2);
				if (!StringUtils.isEmpty(status) && PaymentConstants.SUCCESS.equalsIgnoreCase(status)) {

					debitStatus.setErrorCode(returnedValueMap.get(PaymentConstants.ERROR));
					debitStatus.setTransactionTimestamp(new Date());
					debitStatus.setTransactionId(returnedValueMap.get(PaymentConstants.TXNID));
					debitStatus.setTransactionSuccess(Boolean.TRUE);
					debitStatus.setUnmappedstatus(returnedValueMap.get(PaymentConstants.UNMAPPEDSTATUS));
					debitStatus.setErrorMessage(returnedValueMap.get(PaymentConstants.ERROR_MESSAGE));

				} else {
					debitStatus.setErrorCode(returnedValueMap.get(PaymentConstants.ERROR));
					debitStatus.setTransactionTimestamp(new Date());
					debitStatus.setTransactionId(returnedValueMap.get(PaymentConstants.TXNID));
					debitStatus.setTransactionSuccess(Boolean.FALSE);
					debitStatus.setUnmappedstatus(returnedValueMap.get(PaymentConstants.UNMAPPEDSTATUS));
					debitStatus.setErrorMessage(returnedValueMap.get(PaymentConstants.ERROR_MESSAGE));
				}
				payGroup.addDebitStatus(debitStatus);
			}
		}
	}

	/**
	 * 
	 * @param orderItem
	 * @param returnedValueMap 
	 */
	private void persistOrderDetails(Order orderItem, Map<String, String> returnedValueMap) {

		log.debug("Inside PaymentService.persistPaymentDetails() and Order Id is: ", orderItem.getId());
		orderItem.setSubmittedDate(new Date());
		orderItem.setState(PaymentConstants.SUBMITTED);

	}

	/**
	 * Method responsible for populating Payment group and status from failure
	 * response.
	 * 
	 * @param parameterNames
	 * @param model
	 */
	public void processPaymentErrorResponse(Map<String, String> parameterNames, Model model) {

		log.debug("Inside PaymentService.processPaymentErrorResponse()");

		if (null == parameterNames || parameterNames.isEmpty())
			return;
		
		// Populate and Return the Model Object.
		String status = parameterNames.get(PaymentConstants.STATUS2);
		if (StringUtils.isEmpty(status) || !PaymentConstants.FAILURE.equalsIgnoreCase(status))
			return;

		String submittedOrderId = parameterNames.get(PaymentConstants.UDF1);
		log.debug("Order Id Passed Is", submittedOrderId);

		if (!StringUtils.isEmpty(submittedOrderId)) {
			model.addAttribute(CommerceConstants.ORDER_ID, parameterNames.get(PaymentConstants.UDF1));

			Query query = new Query();
			query.addCriteria(Criteria.where(CommerceConstants._ID).is(submittedOrderId));
			Order orderItem = mongoOperation.findOne(query, Order.class);
			if (null != orderItem) {
				List<PaymentGroup> paymentGroups = orderItem.getPaymentGroups();
				if (null != paymentGroups && !paymentGroups.isEmpty()) {
					for (PaymentGroup payGroup : paymentGroups) {

						// Set the Payment Status to Payment Group.
						PaymentStatus debitStatus = new PaymentStatus();

						debitStatus.setErrorCode(parameterNames.get(PaymentConstants.ERROR));
						debitStatus.setTransactionTimestamp(new Date());
						debitStatus.setTransactionId(parameterNames.get(PaymentConstants.TXNID));
						debitStatus.setTransactionSuccess(Boolean.FALSE);
						debitStatus.setUnmappedstatus(parameterNames.get(PaymentConstants.UNMAPPEDSTATUS));
						debitStatus.setErrorMessage(parameterNames.get(PaymentConstants.ERROR_MESSAGE));
						
						payGroup.addDebitStatus(debitStatus);
					}
				}
				//Save the Order Document.
				orderRepository.save(orderItem);
			}
		}
	}

}
