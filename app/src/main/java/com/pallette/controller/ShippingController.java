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
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.pallette.response.CartResponse;
import com.pallette.response.DeliveryMethodResponse;
import com.pallette.response.GetAddressResponse;
import com.pallette.response.GetDeliveryMethodResponse;
import com.pallette.service.ShippingServices;
import com.pallette.user.api.ApiUser;

@CrossOrigin
@RestController
@RequestMapping("/rest/api/v1")
public class ShippingController {

	@Autowired
	private ShippingServices checkoutServices;
	
	private static final Logger log = LoggerFactory.getLogger(ShippingController.class);
	
	/**
	 * Method that queries mongo db to get all the delivery methods.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.GET_DELIVERY_METHODS_URL, method = RequestMethod.GET , produces = CommerceConstants.APPLICATION_JSON)
	public ResponseEntity<GetDeliveryMethodResponse> handleGetDeliveryMethods(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.handleGetDeliveryMethods()");
		GetDeliveryMethodResponse getDeliveryMethodResponse = new GetDeliveryMethodResponse();
		log.debug("The Order Id Passed is " + orderId);
		List<DeliveryMethodResponse> deliveryMethodResponse = checkoutServices.getDeliveryMethodDetails(orderId);
		
		if (!deliveryMethodResponse.isEmpty()) {
			
			log.debug("Delivery Method to be returned is : ", deliveryMethodResponse);
			getDeliveryMethodResponse.setDeliveryMethods(deliveryMethodResponse);
			
			getDeliveryMethodResponse.setMessage("Delivery Methods was successfully retreived.");
			getDeliveryMethodResponse.setStatus(Boolean.TRUE);
			getDeliveryMethodResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(getDeliveryMethodResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			getDeliveryMethodResponse.setMessage("No Delivery Methods found.");
			getDeliveryMethodResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			getDeliveryMethodResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(getDeliveryMethodResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * Method that takes Order Id and delivery Method as a parameter and sets
	 * the passed in delivery method in the Order's shipping Group as shipping
	 * method.
	 * 
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.SET_DELIVERY_METHOD_URL, method = RequestMethod.GET , produces = CommerceConstants.APPLICATION_JSON)
	public ResponseEntity<CartResponse> handleSetDeliveryMethod(@PathVariable(CommerceConstants.ORDER_ID) String orderId, @PathVariable(CommerceConstants.DELIVERY_METHOD) String deliveryMethod){

		log.info("Inside ShippingController.handleSetDeliveryMethod()");
		log.debug("The Order Id Passed is " + orderId + " And delivery Method is " + deliveryMethod);
		CartResponse cartResponse = checkoutServices.setDeliveryMethod(orderId, deliveryMethod);

		if (null != cartResponse) {

			cartResponse.setStatus(Boolean.TRUE);
			cartResponse.setMessage("Delivery Method Set Successfully.");
			cartResponse.setStatusCode(HttpStatus.OK.value());
			log.debug("Cart Response to be returned is : ", cartResponse);
			return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);

		} else {

			cartResponse = new CartResponse();
			cartResponse.setMessage("Delivery Method Failed to Set Successfully.");
			cartResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			cartResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = RestURLConstants.ADD_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<AddEditAddressResponse> handleAddAddress(@Valid @RequestBody AddEditAddressBean address , Errors errors) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.addAddress()");
		AddEditAddressResponse addEditAddressResponse = new AddEditAddressResponse();
		
		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			addEditAddressResponse.setMessage(getValidationErrors(errors).toString());
			addEditAddressResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(addEditAddressResponse);
		}

		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		AddressResponse addrResponse = checkoutServices.saveNewAddress(address , orderId);

		if (null != addrResponse) {
			addEditAddressResponse.setMessage("Address was successfully added.");
			addEditAddressResponse.setStatus(Boolean.TRUE);
			addEditAddressResponse.setStatusCode(HttpStatus.OK.value());
			
			Map<String, AddressResponse> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.ADDED_ADDRESS, addrResponse);
			addEditAddressResponse.setDataMap(addressMap);
			
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			addEditAddressResponse.setMessage("There was a problem while adding address.");
			addEditAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			addEditAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.EDIT_ADDRESS_URL, method = RequestMethod.POST)
	public ResponseEntity<AddEditAddressResponse> handleEditAddress(@Valid @RequestBody AddEditAddressBean address , Errors errors) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.editAddress()");
		AddEditAddressResponse addEditAddressResponse = new AddEditAddressResponse();

		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			addEditAddressResponse.setMessage(getValidationErrors(errors).toString());
			addEditAddressResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(addEditAddressResponse);
		}
		
		String orderId = address.getOrderId();
		log.debug("Order Id from Request Body ", orderId);
		AddressResponse addrResponse = checkoutServices.editAddress(address , orderId);
		
		if (null != addrResponse) {
			addEditAddressResponse.setMessage("Address was successfully Edited.");
			addEditAddressResponse.setStatusCode(HttpStatus.OK.value());
			addEditAddressResponse.setStatus(Boolean.TRUE);
			
			Map<String, AddressResponse> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.EDITED_ADDRESS, addrResponse);
			addEditAddressResponse.setDataMap(addressMap);
			
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			addEditAddressResponse.setMessage("There was a problem while editing the address.");
			addEditAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			addEditAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(addEditAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.SET_SHIPMENT_ADDRESS_URL, method = RequestMethod.GET , produces = CommerceConstants.APPLICATION_JSON)
	public ResponseEntity<AddEditAddressResponse> handleSetShipmentAddress(@PathVariable(CommerceConstants.ORDER_ID) String orderId , @PathVariable(CommerceConstants.ADDRESS_ID) String addressId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.handleGetShipmentAddress()");
		AddEditAddressResponse setShippingAddressResponse = new AddEditAddressResponse();

		log.debug("Order Id & Address Id from GET Request ", orderId , addressId);
		AddressResponse addressResponse = checkoutServices.setShipmentAddressToOrder(orderId , addressId);
		
		if (null != addressResponse) {
			
			log.debug("Address Bean to be returned is : ", addressResponse);
			Map<String, AddressResponse> addressMap = new HashMap<>();
			addressMap.put(CommerceConstants.SHIPMENT_ADDRESS, addressResponse);
			setShippingAddressResponse.setDataMap(addressMap);
			
			setShippingAddressResponse.setMessage("Shipment Address was successfully set.");
			setShippingAddressResponse.setStatus(Boolean.TRUE);
			setShippingAddressResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(setShippingAddressResponse, new HttpHeaders(), HttpStatus.OK);
		} else {
			setShippingAddressResponse.setMessage("There was a problem while setting the address.");
			setShippingAddressResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			setShippingAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(setShippingAddressResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = RestURLConstants.GET_SHIPMENT_ADDRESS_URL, method = RequestMethod.GET , produces = CommerceConstants.APPLICATION_JSON)
	public ResponseEntity<GetAddressResponse> handleGetShipmentAddress(@PathVariable(CommerceConstants.ORDER_ID) String orderId) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.handleGetShipmentAddress()");
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
			getAddressResponse.setMessage("No address found in profile.");
			getAddressResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
			getAddressResponse.setStatus(Boolean.FALSE);
			return new ResponseEntity<>(getAddressResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
	}

	
	@RequestMapping(value = RestURLConstants.GET_SAVED_ADDRESSES_URL, method = RequestMethod.GET , produces = CommerceConstants.APPLICATION_JSON)
	public ResponseEntity<GetAddressResponse> handleGetSavedAddress(@PathVariable(CommerceConstants.ORDER_ID) Long orderId, OAuth2Authentication authentication) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		log.info("Inside ShippingController.handleGetSavedAddress()");
		GetAddressResponse getAddressResponse = new GetAddressResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No order Id was Passed.");
		
		log.debug("Order Id from GET Request ", orderId);
		
		ApiUser profile = getProfileId(authentication);
		List<AddressResponse> addressList = checkoutServices.getSavedAddressFromOrder(orderId, profile);
		
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
			getAddressResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
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

		log.info("Inside CartController.getValidationErrors()");
		StringBuilder errorMessages = new StringBuilder();
		
		for (ObjectError objErr : errors.getAllErrors()) {
			if (!StringUtils.isEmpty(errorMessages))
				log.debug("Error Message is : ", objErr.getDefaultMessage());
			errorMessages = errorMessages.append(objErr.getDefaultMessage()).append(CommerceConstants.COMMA);
		}
		return errorMessages;
	}
	
	/**
	 * Method to return the profile Id from the authentication object.
	 * 
	 * @param authentication
	 * @return
	 */
	private ApiUser getProfileId(OAuth2Authentication authentication) {
		
		ApiUser user = null;
		if(null !=authentication.getUserAuthentication() && authentication.getUserAuthentication().getDetails() != null) {
			user = (ApiUser) authentication.getUserAuthentication().getDetails();
		}
		return user;
	}

}
