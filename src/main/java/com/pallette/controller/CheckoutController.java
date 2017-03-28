package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AddressBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.response.GenericResponse;
import com.pallette.service.CheckoutServices;

@RestController
@RequestMapping("/rest/api/v1")
public class CheckoutController {

	@Autowired
	private CheckoutServices checkoutServices;
	
	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

	@RequestMapping(value = "/shipping/address/add", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> handleAddAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.addAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		
		String orderId = address.getOrderId();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed"); 
			
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.saveNewAddress(address , orderId)){
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
	public ResponseEntity<GenericResponse> handleEditAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.editAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		String orderId = address.getOrderId();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.editAddress(address , orderId)){
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
	public ResponseEntity<GenericResponse> handleRemoveAddress(@RequestBody AddressBean address) throws IllegalArgumentException {

		log.debug("Inside CheckoutController.removeAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		String addressId = address.getAddressId();
		if (StringUtils.isEmpty(addressId))
			throw new IllegalArgumentException("No Address Id was Passed");
		
		log.debug("Address Id from Request Body ", addressId);
		
		String orderId = address.getOrderId();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.removeAddress(address , orderId)){
			genericResponse.setMessage("Address was successfully removed.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while removing the address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/shipping/address/shipmentAddress/{orderId}", method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<GenericResponse> handleGetShipmentAddress(@PathVariable("orderId") String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.handleGetShipmentAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		AddressBean addressBean = checkoutServices.getShipmentAddressFromOrder(orderId);
		
		if (null != addressBean) {
			log.debug("Address Bean to be returned is : ", addressBean);
			Map<String, Object> addressMap = new HashMap<>();
			addressMap.put(CommerceContants.SHIPMENT_ADDRESS, addressBean);
			genericResponse.setMapData(addressMap);
			genericResponse.setMessage("Shipment Address was successfully retreived.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while getting the address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = "/shipping/address/savedAddress/{orderId}", method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<GenericResponse> handleGetSavedAddress(@PathVariable("orderId") String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.handleGetSavedAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		List<AddressBean> addressBeanList = checkoutServices.getSavedAddressFromOrder(orderId);
		
		if (!addressBeanList.isEmpty()) {
			Map<Object, Collection> addressMap = new HashMap<>();
			addressMap.put(CommerceContants.SAVED_ADDRESS, addressBeanList);
			genericResponse.setItemMapData(addressMap);
			genericResponse.setMessage("Saved Address was successfully retreived.");
			genericResponse.setItemCount(addressBeanList.size());
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			genericResponse.setMessage("There was a problem while getting the saved address.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


}
