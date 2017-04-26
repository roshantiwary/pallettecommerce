package com.pallette.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.commerce.contants.PaymentConstants;
import com.pallette.commerce.order.purchase.pipelines.processors.SubmitOrderValidateChain;
import com.pallette.constants.RestURLConstants;
import com.pallette.domain.Order;
import com.pallette.payment.payu.PaymentIntegrator;
import com.pallette.repository.OrderRepository;
import com.pallette.response.OrderConfirmationDetailsResponse;
import com.pallette.service.PaymentService;

@Controller
public class PaymentController {

	@Autowired
	PaymentIntegrator paymentIntegrator;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	SubmitOrderValidateChain validateChain;

	
	/**
	 * The Order repository.
	 */
	@Autowired
	private OrderRepository orderRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value = "{orderId}/paynow", method = RequestMethod.POST)
	public String makePayment(HttpServletRequest request, HttpServletResponse response, Model model , @PathVariable(CommerceConstants.ORDER_ID) String orderId) throws ServletException, IOException{
		
		log.debug("Inside PaymentController.makePayment()");
		log.debug("The Passed In Order id : " + orderId);

		Order order = orderRepository.findOne(orderId);
		if (null == order)
			return null;
		
		boolean isValidationSuccess = Boolean.TRUE;
		
		isValidationSuccess = validateChain.validateForSubmitOrder(order);
		if(!isValidationSuccess)
			return "redirect:" + PaymentConstants.FAILED;
		
		Map<String, String> values = new HashMap<String, String>();
		values = paymentIntegrator.hashCalMethod(order);
		//Mandatory Parameters
	    model.addAttribute(PaymentConstants.KEY, values.get(PaymentConstants.KEY));
	    model.addAttribute(PaymentConstants.HASH, values.get(PaymentConstants.HASH));
	    model.addAttribute(PaymentConstants.TXNID, values.get(PaymentConstants.TXNID));
	    model.addAttribute(PaymentConstants.AMOUNT, values.get(PaymentConstants.AMOUNT));
	    model.addAttribute(PaymentConstants.FIRSTNAME, values.get(PaymentConstants.FIRSTNAME));
	    model.addAttribute(PaymentConstants.EMAIL, values.get(PaymentConstants.EMAIL));
	    model.addAttribute(PaymentConstants.PHONE, values.get(PaymentConstants.PHONE));
	    model.addAttribute(PaymentConstants.PRODUCTINFO, values.get(PaymentConstants.PRODUCTINFO));
	    model.addAttribute(PaymentConstants.SERVICE_PROVIDER, values.get(PaymentConstants.SERVICE_PROVIDER));
	    model.addAttribute(PaymentConstants.FURL, values.get(PaymentConstants.FURL));
	    model.addAttribute(PaymentConstants.SURL, values.get(PaymentConstants.SURL));
	    model.addAttribute(PaymentConstants.CURL, values.get(PaymentConstants.CURL));
	    model.addAttribute(PaymentConstants.ACTION, values.get(PaymentConstants.ACTION));
	    //optional Parameters
	    model.addAttribute(PaymentConstants.LASTNAME, values.get(PaymentConstants.LASTNAME));
	    model.addAttribute(PaymentConstants.ADDRESS1, values.get(PaymentConstants.ADDRESS1));
	    model.addAttribute(PaymentConstants.ADDRESS2, values.get(PaymentConstants.ADDRESS2));
	    model.addAttribute(PaymentConstants.CITY, values.get(PaymentConstants.CITY));
	    model.addAttribute(PaymentConstants.STATE, values.get(PaymentConstants.STATE));
	    model.addAttribute(PaymentConstants.COUNTRY, values.get(PaymentConstants.COUNTRY));
	    model.addAttribute(PaymentConstants.ZIPCODE, values.get(PaymentConstants.ZIPCODE));
	    model.addAttribute(PaymentConstants.UDF1, values.get(PaymentConstants.UDF1));
	    model.addAttribute(PaymentConstants.UDF2, values.get(PaymentConstants.UDF2));
	    model.addAttribute(PaymentConstants.UDF3, values.get(PaymentConstants.UDF3));
	    model.addAttribute(PaymentConstants.HASH_STRING, values.get(PaymentConstants.HASH_STRING));
	    model.addAttribute(PaymentConstants.UDF4, values.get(PaymentConstants.UDF4));
	    model.addAttribute(PaymentConstants.UDF5, values.get(PaymentConstants.UDF5));
	    model.addAttribute(PaymentConstants.UDF5, values.get(PaymentConstants.UDF5));
	    model.addAttribute(PaymentConstants.PG, values.get(PaymentConstants.PG));
	    
	    return "processPayment"; //gets redirected to the url '/anotherUrl'
	}
	
	@RequestMapping(value = "/failure", method = RequestMethod.POST)
	public String paymentFailure(HttpServletRequest request, HttpServletResponse response , Model model) {
		 
		log.debug("Inside PaymentController.paymentFailure()");
		Map<String, String> parameterNames = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();
		
		while(enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
	        parameterNames.put(parameterName, request.getParameter(parameterName));
		}
		//
		paymentService.processPaymentErrorResponse(parameterNames, model);
		
		log.debug("Payment Failure");
		return "redirect:" + PaymentConstants.FAILED;
	}

	@RequestMapping(value = "/success", method = RequestMethod.POST)
	public String paymentSuccess(HttpServletRequest request, HttpServletResponse response , Model model) {
		 
		log.debug("Inside PaymentController.paymentSuccess()");
		request.getParameter(PaymentConstants.HASH);
		Map<String, String> parameterNames = new HashMap<String, String>();
		Enumeration<String> enumeration = request.getParameterNames();
		
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			parameterNames.put(parameterName, request.getParameter(parameterName));
		}
		//Process the payment response and then submit the Order.
		paymentService.processPaymentResponse(parameterNames);
		
		log.debug("Payment Successfull");
		return "redirect:" + "http://localhost:4200/checkout/" + parameterNames.get(PaymentConstants.UDF1)  +"/confirmation";
	}
	
	
	@RequestMapping(value = RestURLConstants.GET_ORDER_CONFIRMATION_DETAILS_URL, method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<OrderConfirmationDetailsResponse> handleGetOrderConfirmationDetails(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalAccessException, InvocationTargetException {

		log.debug("Inside PaymentController.handleGetOrderConfirmationDetails()");

		log.debug("Order Id from GET Request ", orderId);
		//Process the payment response and then submit the Order.
		OrderConfirmationDetailsResponse orderConfirmationDetailsResponse = paymentService.getOrderConfirmationDetails(orderId);
				
		
		if (null != orderConfirmationDetailsResponse) {
			
			log.debug("Address Bean to be returned is : ", orderConfirmationDetailsResponse);
			orderConfirmationDetailsResponse.setMessage("Order Confirmation Details Successfully Retrieved.");
			orderConfirmationDetailsResponse.setStatus(Boolean.TRUE);
			orderConfirmationDetailsResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(orderConfirmationDetailsResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			orderConfirmationDetailsResponse.setMessage("There was a problem while getting Order Conf Details.");
			orderConfirmationDetailsResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			orderConfirmationDetailsResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(orderConfirmationDetailsResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
