package com.pallette.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.domain.CartItem;
import com.pallette.domain.Order;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.GenericResponse;
import com.pallette.service.OrderService;

@RestController
@RequestMapping("/rest/api/v1")
public class CartController {

	@Autowired
	private OrderService orderService;
	
	private static final Logger logger = LoggerFactory.getLogger(CartController.class);

	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> addItemToCart(@RequestBody CartItem item) throws NoRecordsFoundException {
		
		String cartId = item.getCartId();
		GenericResponse genericResponse = new GenericResponse();
		if (cartId.isEmpty()) {

			genericResponse.setStatusCode(HttpStatus.OK.value());
			Order order = orderService.createOrder(item.getProductId(), item.getQuantity(), item.getProfileId());
			List<Order> orders = new ArrayList<Order>();
			orders.add(order);
			genericResponse.setItems(orders);
			genericResponse.setItemCount(orders.size());
			genericResponse.setMessage("Product Items were Returned Successfully.");
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);

		} else {
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/cart/update", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> updateItem(@RequestBody CartItem item) throws NoRecordsFoundException , IllegalArgumentException {
		
		logger.debug("Inside CartController.updateItem()");
		GenericResponse genericResponse = new GenericResponse();
		
		if(null == item)
			throw new IllegalArgumentException("No Input parameters were Passed");
			
		if (StringUtils.isEmpty(item.getCartId()) || StringUtils.isEmpty(item.getProductId()) || StringUtils.isEmpty(item.getQuantity()))
			throw new IllegalArgumentException("Input parameters missing.");
		
		logger.debug("The Passed In Order id : " + item.getCartId() + " Product Id :" + item.getProductId() + "Quantity :" + item.getQuantity());
		Order order = orderService.updateItemQuantity(item.getCartId() , item.getProductId() , item.getQuantity());
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		genericResponse.setItems(orders);
		genericResponse.setItemCount(orders.size());
		genericResponse.setMessage("Order was successfully updated.");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}

}
