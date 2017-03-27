package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AddressBean;
import com.pallette.response.GenericResponse;
import com.pallette.service.CheckoutServices;

@RestController
@RequestMapping("/rest/api/v1")
public class CheckoutController {

	@Autowired
	private CheckoutServices checkoutServices;
	
	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

	@RequestMapping(value = "/shipping/address/add", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> addAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.addAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		
		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		String profileId = address.getProfileId();
		log.debug("Profile Id from Request Body ", profileId);

		if(checkoutServices.saveNewAddress(address , orderId , profileId)){
			genericResponse.setMessage("Address was successfully added.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while adding address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/shipping/address/edit", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> editAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.editAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		String profileId = address.getProfileId();
		log.debug("Profile Id from Request Body ", profileId);

		if(checkoutServices.editAddress(address , orderId , profileId)){
			genericResponse.setMessage("Address was successfully Edited.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while editing the address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/shipping/address/remove", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> removeAddress(@RequestBody AddressBean address) throws IllegalArgumentException {

		log.debug("Inside CheckoutController.removeAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		String addressId = address.getAddressId();
		log.debug("Address Id from Request Body ", addressId);
		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		String profileId = address.getProfileId();
		log.debug("Profile Id from Request Body ", profileId);

		if(checkoutServices.removeAddress(address , orderId , profileId)){
			genericResponse.setMessage("Address was successfully removed.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while removing the address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


}
