package com.pallette.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.domain.CartItem;
import com.pallette.domain.CommerceItem;
import com.pallette.domain.Order;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.GenericResponse;
import com.pallette.service.OrderService;

@RestController
@RequestMapping("/rest/api/v1")
public class CartController {

	private static final String ORDER = "order";

	@Autowired
	private OrderService orderService;
	
	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	@RequestMapping(value = "/cart/add", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> addItemToCart(@RequestBody CartItem item) throws NoRecordsFoundException {
		
		log.debug("Inside CartController.addItemToCart()");
		String cartId = item.getOrderId();
		GenericResponse genericResponse = new GenericResponse();
		if (cartId.isEmpty()) {

			Order order = orderService.createOrder(item.getProductId(), item.getQuantity(), item.getProfileId());
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put(ORDER, order);
			genericResponse.setMapData(orderMap);
			genericResponse.setMessage("Item successfully added to cart.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);

		} else {
			Order order = orderService.addItemToOrder(item.getProductId(), item.getQuantity(), item.getProfileId() , cartId);
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put(ORDER, order);
			genericResponse.setMapData(orderMap);
			genericResponse.setMessage("Item successfully added to cart.");
			genericResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(value = "/cart/update", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> updateItem(@RequestBody CartItem item) throws NoRecordsFoundException , IllegalArgumentException {
		
		log.debug("Inside CartController.updateItem()");
		GenericResponse genericResponse = new GenericResponse();
		
		if(null == item)
			throw new IllegalArgumentException("No Input parameters were Passed");
			
		if (StringUtils.isEmpty(item.getOrderId()) || StringUtils.isEmpty(item.getProductId()) || StringUtils.isEmpty(item.getQuantity()))
			throw new IllegalArgumentException("Input parameters missing.");
		
		log.debug("The Passed In Order id : " + item.getOrderId() + " Product Id :" + item.getProductId() + "Quantity :" + item.getQuantity());
		Order order = orderService.updateItemQuantity(item.getOrderId() , item.getProductId() , item.getQuantity());
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(order);
		genericResponse.setItems(orders);
		genericResponse.setItemCount(orders.size());
		genericResponse.setMessage("Order was successfully updated.");
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	/**
	 * <p>
	 * Method to remove an item from Order.
	 * </p>
	 * 
	 * @param item
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = "/cart/remove", method = RequestMethod.POST)
	public ResponseEntity<GenericResponse> handleRemoveItem(@RequestBody CartItem item) throws NoRecordsFoundException , IllegalArgumentException {
		
		log.debug("Inside CartController.removeItem()");
		GenericResponse genericResponse = new GenericResponse();
		
		if(null == item)
			throw new IllegalArgumentException("No Input parameters were Passed");
			
		if (StringUtils.isEmpty(item.getOrderId()) || StringUtils.isEmpty(item.getProductId()))
			throw new IllegalArgumentException("Input parameters missing.");
		
		log.debug("The Passed In Order id : " + item.getOrderId() + " Product Id :" + item.getProductId());
		CommerceItem removedItem = orderService.removeItemFromOrder(item.getOrderId() , item.getProductId());
		
		genericResponse.setMessage("Item was successfully removed.");
		genericResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
	}
}
