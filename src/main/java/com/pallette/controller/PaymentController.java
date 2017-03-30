package com.pallette.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pallette.payment.payu.JavaIntegrationKit;


@Controller
public class PaymentController {

	@Autowired
	JavaIntegrationKit paymentIntegrator;
	
	@RequestMapping(value = "/paynow", method = RequestMethod.POST)
	public String makePayment(HttpServletRequest request,HttpServletResponse response, Map model) throws ServletException, IOException{
	    //do sume stuffs
		request.setAttribute("key", "rjQUPktU");
        request.setAttribute("amount", 1);
        request.setAttribute("firstname", "Roshan");
        request.setAttribute("email", "roshantiwary@gmail.com");
        request.setAttribute("phone", "1234567890");
        request.setAttribute("productinfo", "test");
        request.setAttribute("surl", "http://localhost:8080/pallette-commerce-1.0.0/success");
        request.setAttribute("furl", "http://localhost:8080/pallette-commerce-1.0.0/failure");
        request.setAttribute("service_provider", "payu_paisa");
	    Map<String, String> values;
	    values = paymentIntegrator.hashCalMethod(request, response);
		//Mandatory Parameters
	    request.setAttribute("key", values.get("key"));
	    request.setAttribute("hash", values.get("hash"));
	    request.setAttribute("txnid", values.get("txnid"));
	    request.setAttribute("amount", values.get("amount"));
	    request.setAttribute("firstname", values.get("firstname"));
	    request.setAttribute("email", values.get("email"));
	    request.setAttribute("phone", values.get("phone"));
	    request.setAttribute("productinfo", values.get("productinfo"));
	    request.setAttribute("service_provider", values.get("service_provider"));
	    request.setAttribute("furl", values.get("furl"));
	    //optional Parameters
	    request.setAttribute("lastname", values.get("lastname"));
	    request.setAttribute("address1", values.get("address1"));
	    request.setAttribute("address2", values.get("address2"));
	    request.setAttribute("city", values.get("city"));
	    request.setAttribute("state", values.get("state"));
	    request.setAttribute("country", values.get("country"));
	    request.setAttribute("zipcode", values.get("zipcode"));
	    request.setAttribute("udf1", values.get("udf1"));
	    request.setAttribute("udf2", values.get("udf2"));
	    request.setAttribute("udf3", values.get("udf3"));
	    request.setAttribute("hashString", values.get("hashString"));
	    request.setAttribute("udf4", values.get("udf4"));
	    request.setAttribute("udf5", values.get("udf5"));
	    request.setAttribute("udf5", values.get("udf5"));
	    request.setAttribute("pg", values.get("pg"));
	    
	    return "forward:/submitorder"; //gets redirected to the url '/anotherUrl'
	}
	
	@RequestMapping(value="/failure",method=RequestMethod.POST)
	public String paymentFailure(HttpServletRequest request,HttpServletResponse response){
	    //do sume stuffs
		System.out.println("Payment Failure");
	    return "failed"; 
	}
	
	@RequestMapping(value="/success",method=RequestMethod.POST)
	public String paymentSuccess(HttpServletRequest request,HttpServletResponse response){
	    //do sume stuffs
		request.getParameter("hash");
		System.out.println("Payment Successfull");
	    return "confirmation"; 
	}
}
