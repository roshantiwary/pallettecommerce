package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
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
import com.pallette.constants.RestURLConstants;
import com.pallette.response.AddEditAddressResponse;
import com.pallette.response.AddressResponse;
import com.pallette.response.GetAddressResponse;
import com.pallette.service.CheckoutServices;

@RestController
@RequestMapping("/rest/api/v1")
public class CheckoutController {

	@Autowired
	private CheckoutServices checkoutServices;
	
	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

	@RequestMapping(value = RestURLConstants.ADD_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<AddEditAddressResponse> handleAddAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.addAddress()");
		AddEditAddressResponse addEditAddressResponse = new AddEditAddressResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		
		String orderId = address.getOrderId();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed"); 
			
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.saveNewAddress(address , orderId)){
			addEditAddressResponse.setMessage("Address was successfully added.");
			addEditAddressResponse.setStatus(Boolean.TRUE);
			addEditAddressResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			addEditAddressResponse.setMessage("There was a problem while adding address.");
			addEditAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			addEditAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.EDIT_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<AddEditAddressResponse> handleEditAddress(@RequestBody AddressBean address) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.editAddress()");
		AddEditAddressResponse addEditAddressResponse = new AddEditAddressResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		String orderId = address.getOrderId();
		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		log.debug("Order Id from Request Body ", orderId);

		if(checkoutServices.editAddress(address , orderId)){
			addEditAddressResponse.setMessage("Address was successfully Edited.");
			addEditAddressResponse.setStatusCode(HttpStatus.OK.value());
			addEditAddressResponse.setStatus(Boolean.TRUE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			addEditAddressResponse.setMessage("There was a problem while editing the address.");
			addEditAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			addEditAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.REMOVE_ADDRESS_URL, method = RequestMethod.GET , produces = "application/json")
	public ResponseEntity<AddEditAddressResponse> handleRemoveAddress(@PathVariable(CommerceContants.ORDER_ID) String orderId) throws IllegalArgumentException {

		log.debug("Inside CheckoutController.removeAddress()");
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
	public ResponseEntity<GetAddressResponse> handleGetShipmentAddress(@PathVariable(CommerceContants.ORDER_ID) String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.handleGetShipmentAddress()");
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
			addressMap.put(CommerceContants.SHIPMENT_ADDRESS, addresses);
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
	public ResponseEntity<GetAddressResponse> handleGetSavedAddress(@PathVariable(CommerceContants.ORDER_ID) String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.debug("Inside CheckoutController.handleGetSavedAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		List<AddressResponse> addressList = checkoutServices.getSavedAddressFromOrder(orderId);
		
		if (!addressList.isEmpty()) {
			
			Map<String, List<AddressResponse>> addressMap = new HashMap<>();
			addressMap.put(CommerceContants.SAVED_ADDRESS, addressList);
			getAddressResponse.setDataMap(addressMap);
			addressMap.put(CommerceContants.SAVED_ADDRESS, addressList);
			
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


}
