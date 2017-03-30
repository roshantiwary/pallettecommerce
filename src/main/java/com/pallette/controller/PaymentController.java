package com.pallette.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	    
	    return "redirect:/submitorder"; //gets redirected to the url '/anotherUrl'
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
		request.getAttribute("hash");
		System.out.println("Payment Successfull");
	    return "confirmation"; 
	}
	
	private ResponseEntity sendPostRequest(String uri, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		URL url = new URL(uri);
		HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	    urlConnection.setRequestMethod("POST"); // PUT is another valid option
	    urlConnection.setDoOutput(true);
	    Map<String,String> arguments = new HashMap<>();
	    
	    request.setAttribute("key", "rjQUPktU");
        request.setAttribute("amount", 1);
        request.setAttribute("firstname", "Roshan");
        request.setAttribute("email", "roshantiwary@gmail.com");
        request.setAttribute("phone", "1234567890");
        request.setAttribute("productinfo", "test");
        request.setAttribute("surl", "/success");
        request.setAttribute("furl", "/failure");
        request.setAttribute("service_provider", "payu_paisa");
	    Map<String, String> values;
	    values = paymentIntegrator.hashCalMethod(request, response);
		//Mandatory Parameters
	    arguments.put("key", values.get("key"));
	    arguments.put("hash", values.get("hash"));
	    arguments.put("txnid", values.get("txnid"));
	    arguments.put("amount", values.get("amount"));
	    arguments.put("firstname", values.get("firstname"));
	    arguments.put("email", values.get("email"));
	    arguments.put("phone", values.get("phone"));
	    arguments.put("productinfo", values.get("productinfo"));
	    arguments.put("service_provider", values.get("service_provider"));
	    arguments.put("furl", values.get("furl"));
	    
	    StringBuilder sj = new StringBuilder();
	    for(Map.Entry<String,String> entry : arguments.entrySet()) {
	        sj.append(URLEncoder.encode(entry.getKey(), "UTF-8") + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&");
	    }
	    byte[] out = sj.toString().getBytes();

	    urlConnection.setFixedLengthStreamingMode(out.length);
	    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    InputStream inputStream = urlConnection.getInputStream();
	    
	  //2, create HttpHeaders for ResponseEntity
        HttpHeaders responseHeaders = new HttpHeaders();
        for (int i = 0;; i++) {
          String headerName = urlConnection.getHeaderFieldKey(i);
          String headerValue = urlConnection.getHeaderField(i);
          if(headerName != null && headerValue != null){
            responseHeaders.set(headerName, headerValue);
          }
          if (headerName == null && headerValue == null) {
            break;
          }
        }
        
	    urlConnection.connect();
	    try
	    {
	        OutputStream os = urlConnection.getOutputStream();
	        os.write(out);
	    }
	    catch (Exception e)
	    {

	    }
	    
	    return new ResponseEntity<>(inputStream, responseHeaders, HttpStatus.OK);
	}
}
