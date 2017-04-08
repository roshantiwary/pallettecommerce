package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AddEditAddressBean;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.constants.RestURLConstants;
import com.pallette.response.AddEditAddressResponse;
import com.pallette.response.AddressResponse;
import com.pallette.response.GetAddressResponse;
import com.pallette.service.ShippingServices;

@RestController
@RequestMapping("/rest/api/v1")
public class ShippingController {

	@Autowired
	private ShippingServices checkoutServices;
	
	private static final Logger log = LoggerFactory.getLogger(ShippingController.class);

	@RequestMapping(value = RestURLConstants.ADD_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<GetAddressResponse> handleAddAddress(@Valid @RequestBody AddEditAddressBean address , Errors errors) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingController.addAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();
		
		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			getAddressResponse.setMessage(getValidationErrors(errors).toString());
			getAddressResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(getAddressResponse);
		}

		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		AddressResponse addrResponse = checkoutServices.saveNewAddress(address , orderId);

		if (null != addrResponse) {
			getAddressResponse.setMessage("Address was successfully added.");
			getAddressResponse.setStatus(Boolean.TRUE);
			getAddressResponse.setStatusCode(HttpStatus.OK.value());
			
			List<AddressResponse> addresses = new ArrayList<AddressResponse>();
			addresses.add(addrResponse);
			Map<String, List<AddressResponse>> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.ADDED_ADDRESS, addresses);
			getAddressResponse.setDataMap(addressMap);
			
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			getAddressResponse.setMessage("There was a problem while adding address.");
			getAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			getAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.EDIT_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<GetAddressResponse> handleEditAddress(@Valid @RequestBody AddEditAddressBean address , Errors errors) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingController.editAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();

		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			getAddressResponse.setMessage(getValidationErrors(errors).toString());
			getAddressResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(getAddressResponse);
		}
		
		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		AddressResponse addrResponse = checkoutServices.editAddress(address , orderId);
		
		if (null != addrResponse) {
			getAddressResponse.setMessage("Address was successfully Edited.");
			getAddressResponse.setStatusCode(HttpStatus.OK.value());
			getAddressResponse.setStatus(Boolean.TRUE);
			
			List<AddressResponse> addresses = new ArrayList<AddressResponse>();
			addresses.add(addrResponse);
			Map<String, List<AddressResponse>> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.EDITED_ADDRESS, addresses);
			getAddressResponse.setDataMap(addressMap);
			
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			getAddressResponse.setMessage("There was a problem while editing the address.");
			getAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			getAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.REMOVE_ADDRESS_URL, method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<AddEditAddressResponse> handleRemoveAddress(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalArgumentException {

		log.debug("Inside ShippingController.removeAddress()");
		AddEditAddressResponse addEditAddressResponse = new AddEditAddressResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.removeAddress(orderId)){
			addEditAddressResponse.setMessage("Address was successfully removed.");
			addEditAddressResponse.setStatusCode(HttpStatus.OK.value());
			addEditAddressResponse.setStatus(Boolean.TRUE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			addEditAddressResponse.setMessage("There was a problem while removing the address.");
			addEditAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			addEditAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = RestURLConstants.GET_SHIPMENT_ADDRESS_URL, method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<GetAddressResponse> handleGetShipmentAddress(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingController.handleGetShipmentAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		AddressResponse addressResponse = checkoutServices.getShipmentAddressFromOrder(orderId);
		
		if (null != addressResponse) {
			
			log.debug("Address Bean to be returned is : ", addressResponse);
			List<AddressResponse> addresses = new ArrayList<AddressResponse>();
			addresses.add(addressResponse);
			Map<String, List<AddressResponse>> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.SHIPMENT_ADDRESS, addresses);
			getAddressResponse.setDataMap(addressMap);
			
			getAddressResponse.setMessage("Shipment Address was successfully retreived.");
			getAddressResponse.setStatus(Boolean.TRUE);
			getAddressResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			getAddressResponse.setMessage("There was a problem while getting the address.");
			getAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			getAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@RequestMapping(value = RestURLConstants.GET_SAVED_ADDRESSES_URL, method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<GetAddressResponse> handleGetSavedAddress(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside ShippingController.handleGetSavedAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		List<AddressResponse> addressList = checkoutServices.getSavedAddressFromOrder(orderId);
		
		if (!addressList.isEmpty()) {
			
			Map<String, List<AddressResponse>> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.SAVED_ADDRESS, addressList);
			getAddressResponse.setDataMap(addressMap);
			addressMap.put(CommerceConstants.SAVED_ADDRESS, addressList);
			
			getAddressResponse.setStatus(Boolean.TRUE);
			getAddressResponse.setMessage("Saved Address was successfully retreived.");
			getAddressResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			getAddressResponse.setMessage("There was a problem while getting the saved address.");
			getAddressResponse.setStatus(Boolean.FALSE);
			getAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	/**
	 * Method that iterates the validation errors and returns a comma separated
	 * error message.
	 * 
	 * @param errors
	 * @return
	 */
	private StringBuilder getValidationErrors(Errors errors) {

		log.debug("Inside CartController.getValidationErrors()");
		StringBuilder errorMessages = new StringBuilder();
		
		for (ObjectError objErr : errors.getAllErrors()) {
			if (!StringUtils.isEmpty(errorMessages))
				log.debug("Error Message is : ", objErr.getDefaultMessage());
			errorMessages = errorMessages.append(objErr.getDefaultMessage()).append(CommerceConstants.COMMA);
		}
		return errorMessages;
	}

}