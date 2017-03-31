package com.pallette.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pallette.commerce.contants.CommerceContants;
import com.pallette.commerce.contants.PaymentConstants;
import com.pallette.domain.Order;
import com.pallette.payment.payu.JavaIntegrationKit;
import com.pallette.repository.OrderRepository;


@Controller
public class PaymentController {

	@Autowired
	JavaIntegrationKit paymentIntegrator;
	
	/**
	 * The Order repository.
	 */
	@Autowired
	private OrderRepository orderRepository;
	
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@RequestMapping(value = "{orderId}/paynow", method = RequestMethod.POST)
	public String makePayment(HttpServletRequest request, HttpServletResponse response, Map model , @PathVariable(CommerceContants.ORDER_ID) String orderId) throws ServletException, IOException{
		
		log.debug("Inside PaymentController.makePayment()");
		log.debug("The Passed In Order id : " + orderId);

		Order order = orderRepository.findOne(orderId);
		if (null == order)
			return null;
		
		Map<String, String> values = new HashMap<String, String>();
		values = paymentIntegrator.hashCalMethod(order);
		//Mandatory Parameters
	    request.setAttribute(PaymentConstants.KEY, values.get(PaymentConstants.KEY));
	    request.setAttribute(PaymentConstants.HASH, values.get(PaymentConstants.HASH));
	    request.setAttribute(PaymentConstants.TXNID, values.get(PaymentConstants.TXNID));
	    request.setAttribute(PaymentConstants.AMOUNT, values.get(PaymentConstants.AMOUNT));
	    request.setAttribute(PaymentConstants.FIRSTNAME, values.get(PaymentConstants.FIRSTNAME));
	    request.setAttribute(PaymentConstants.EMAIL, values.get(PaymentConstants.EMAIL));
	    request.setAttribute(PaymentConstants.PHONE, values.get(PaymentConstants.PHONE));
	    request.setAttribute(PaymentConstants.PRODUCTINFO, values.get(PaymentConstants.PRODUCTINFO));
	    request.setAttribute(PaymentConstants.SERVICE_PROVIDER, values.get(PaymentConstants.SERVICE_PROVIDER));
	    request.setAttribute(PaymentConstants.FURL, values.get(PaymentConstants.FURL));
	    request.setAttribute(PaymentConstants.SURL, values.get(PaymentConstants.SURL));
	    request.setAttribute(PaymentConstants.CURL, values.get(PaymentConstants.CURL));
	    request.setAttribute(PaymentConstants.ACTION, values.get(PaymentConstants.ACTION));
	    //optional Parameters
	    request.setAttribute(PaymentConstants.LASTNAME, values.get(PaymentConstants.LASTNAME));
	    request.setAttribute(PaymentConstants.ADDRESS1, values.get(PaymentConstants.ADDRESS1));
	    request.setAttribute(PaymentConstants.ADDRESS2, values.get(PaymentConstants.ADDRESS2));
	    request.setAttribute(PaymentConstants.CITY, values.get(PaymentConstants.CITY));
	    request.setAttribute(PaymentConstants.STATE, values.get(PaymentConstants.STATE));
	    request.setAttribute(PaymentConstants.COUNTRY, values.get(PaymentConstants.COUNTRY));
	    request.setAttribute(PaymentConstants.ZIPCODE, values.get(PaymentConstants.ZIPCODE));
	    request.setAttribute(PaymentConstants.UDF1, values.get(PaymentConstants.UDF1));
	    request.setAttribute(PaymentConstants.UDF2, values.get(PaymentConstants.UDF2));
	    request.setAttribute(PaymentConstants.UDF3, values.get(PaymentConstants.UDF3));
	    request.setAttribute(PaymentConstants.HASH_STRING, values.get(PaymentConstants.HASH_STRING));
	    request.setAttribute(PaymentConstants.UDF4, values.get(PaymentConstants.UDF4));
	    request.setAttribute(PaymentConstants.UDF5, values.get(PaymentConstants.UDF5));
	    request.setAttribute(PaymentConstants.UDF5, values.get(PaymentConstants.UDF5));
	    request.setAttribute(PaymentConstants.PG, values.get(PaymentConstants.PG));
	    
	    return "forward:/submitorder"; //gets redirected to the url '/anotherUrl'
	}
	
	@RequestMapping(value = "/failure", method = RequestMethod.POST)
	public String paymentFailure(HttpServletRequest request, HttpServletResponse response) {
		// do sume stuffs
		System.out.println("Payment Failure");
		return "failed";
	}

	@RequestMapping(value = "/success", method = RequestMethod.POST)
	public String paymentSuccess(HttpServletRequest request, HttpServletResponse response) {
		// do sume stuffs
		request.getParameter(PaymentConstants.HASH);
		System.out.println("Payment Successfull");
		return "confirmation";
	}
}
