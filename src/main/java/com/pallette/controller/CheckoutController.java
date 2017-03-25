package com.pallette.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AddressBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.GenericResponse;
import com.pallette.service.CheckoutServices;

@RestController
@RequestMapping("/rest/api/v1")
public class CheckoutController {

	@Autowired
	private CheckoutServices checkoutServices;
	
	@Autowired
	private AuthorizationServerTokenServices tokenServices;

	private static final Logger log = LoggerFactory.getLogger(CheckoutController.class);

	@RequestMapping(value = "/shipping/address/add", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> addAddress(OAuth2Authentication oAuth2Authentication , @RequestBody AddressBean address) throws NoRecordsFoundException {

		log.debug("Inside CheckoutController.addAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		Map<String, Object> tokenInfo = tokenServices.getAccessToken(oAuth2Authentication).getAdditionalInformation();
		
		if (tokenInfo.isEmpty()) {
			genericResponse.setMessage("No Order and Profile Found.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String orderId = (String) tokenInfo.get(CommerceContants.ORDER_ID);
		log.debug("Order Id from Token ", orderId);
		String profileId = (String) tokenInfo.get(CommerceContants.PROFILE_ID);
		log.debug("Profile Id from Token ", profileId);

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
	public ResponseEntity<GenericResponse> editAddress(OAuth2Authentication oAuth2Authentication , @RequestBody AddressBean address) throws NoRecordsFoundException {

		log.debug("Inside CheckoutController.editAddress()");
		GenericResponse genericResponse = new GenericResponse();

		if (null == address)
			throw new IllegalArgumentException("No Input parameters were Passed");
		
		Map<String, Object> tokenInfo = tokenServices.getAccessToken(oAuth2Authentication).getAdditionalInformation();
		
		if (tokenInfo.isEmpty()) {
			genericResponse.setMessage("No Order and Profile Found.");
			genericResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		String orderId = (String) tokenInfo.get(CommerceContants.ORDER_ID);
		log.debug("Order Id from Token ", orderId);
		String profileId = (String) tokenInfo.get(CommerceContants.PROFILE_ID);
		log.debug("Profile Id from Token ", profileId);

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

}
