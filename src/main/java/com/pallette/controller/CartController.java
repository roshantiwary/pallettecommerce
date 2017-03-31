package com.pallette.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.AddToCartBean;
import com.pallette.beans.RemoveItemBean;
import com.pallette.beans.UpdateCartBean;
import com.pallette.commerce.contants.CommerceContants;
import com.pallette.constants.RestURLConstants;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.CartResponse;
import com.pallette.service.OrderService;

@RestController
@RequestMapping("/rest/api/v1")
public class CartController {

	@Autowired
	private OrderService orderService;
	
	private static final Logger log = LoggerFactory.getLogger(CartController.class);

	/**
	 * Method responsible for adding item to cart. The method while adding item
	 * creates order or uses the passed in order Id.
	 * 
	 * @param addToCartRequest
	 * @param errors
	 * @return
	 * @throws NoRecordsFoundException
	 */
	@RequestMapping(value = RestURLConstants.CART_ADD, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponse> handleAddItemToCart(@Valid @RequestBody AddToCartBean addToCartRequest , Errors errors) throws NoRecordsFoundException {
		
		log.debug("Inside CartController.addItemToCart()");
		CartResponse cartResponse = new CartResponse();
		
		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			cartResponse.setMessage(getValidationErrors(errors).toString());
			cartResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(cartResponse);
		}
            
		String orderId = addToCartRequest.getOrderId();
		if (StringUtils.isEmpty(orderId)) {
			
			String profileId = addToCartRequest.getProfileId();
			if(StringUtils.isEmpty(profileId))
				throw new IllegalArgumentException("No Profile Id was Passed");
				
			log.debug("The Passed In Product Id :" + addToCartRequest.getProductId() + "Quantity :" + addToCartRequest.getQuantity());
			//Creating a new Order and adding the item to the Order.
			cartResponse = orderService.createAndAddItemToOrder(addToCartRequest.getSkuId(), addToCartRequest.getProductId(), addToCartRequest.getQuantity(), profileId);
			cartResponse.setStatus(Boolean.TRUE);
			cartResponse.setMessage("New Order created and Item successfully added to cart.");
			cartResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);

		} else {
			log.debug("Order Id Passed is :" + orderId);
			log.debug("The Passed In Product Id :" + addToCartRequest.getProductId()+ " Sku Id : "+ addToCartRequest.getSkuId() + "Quantity :" + addToCartRequest.getQuantity());
			//Invoking method to add item to passed in order id.
			cartResponse = orderService.addItemToOrder(addToCartRequest.getSkuId(), addToCartRequest.getProductId(), addToCartRequest.getQuantity(), orderId);
			cartResponse.setStatus(Boolean.TRUE);
			cartResponse.setMessage("Item successfully added to cart.");
			cartResponse.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);
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
			errorMessages = errorMessages.append(objErr.getDefaultMessage()).append(CommerceContants.COMMA);
		}
		return errorMessages;
	}
	
	/**
	 * Method that is responsible for updating Item's quantity in cart.
	 * 
	 * @param updateItem
	 * @param errors
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = RestURLConstants.CART_UPDATE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponse> handleUpdateItem(@Valid @RequestBody UpdateCartBean updateItem , Errors errors) throws NoRecordsFoundException , IllegalArgumentException {
		
		log.debug("Inside CartController.updateItem()");
		CartResponse cartResponse = new CartResponse();
		
		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			cartResponse.setMessage(getValidationErrors(errors).toString());
			cartResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(cartResponse);
		}
		
		log.debug("The Passed In Order id : " + updateItem.getOrderId() + " Product Id :" + updateItem.getProductId() + " Sku Id :" + updateItem.getSkuId() + "Quantity :" + updateItem.getQuantity());
		cartResponse = orderService.updateItemQuantity(updateItem.getOrderId() , updateItem.getProductId() , updateItem.getSkuId(), updateItem.getQuantity());
		
		cartResponse.setStatus(Boolean.TRUE);
		cartResponse.setMessage("Item successfully updated.");
		cartResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	/**
	 * <p>
	 * Method to remove an item from Order.
	 * </p>
	 * 
	 * @param removeItemRequest
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 */
	@RequestMapping(value = RestURLConstants.CART_REMOVE, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponse> handleRemoveItem(@RequestBody RemoveItemBean removeItemRequest , Errors errors) throws NoRecordsFoundException , IllegalArgumentException {
		
		log.debug("Inside CartController.removeItem()");
		CartResponse cartResponse = new CartResponse();
		
		//If error, just return a 400 bad request, along with the error message.
		if (errors.hasErrors()) {
			cartResponse.setMessage(getValidationErrors(errors).toString());
			cartResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
			return ResponseEntity.badRequest().body(cartResponse);
		}
		
		log.debug("The Passed In Order id : " + removeItemRequest.getOrderId() + " Product Id :" + removeItemRequest.getProductId() + " Sku Id :" + removeItemRequest.getSkuId());
		cartResponse = orderService.removeItemFromOrder(removeItemRequest.getOrderId() , removeItemRequest.getProductId(), removeItemRequest.getSkuId());
		
		cartResponse.setStatus(Boolean.TRUE);
		cartResponse.setMessage("Item successfully Removed.");
		cartResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = RestURLConstants.CART_DETAILS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CartResponse> handleGetCartDetails(@PathVariable(CommerceContants.ORDER_ID) String orderId) throws IllegalArgumentException, NoRecordsFoundException {

		log.debug("Inside CheckoutController.removeAddress()");
		CartResponse cartResponse = new CartResponse();

		if (StringUtils.isEmpty(orderId))
			throw new IllegalArgumentException("No Order Id was Passed");
		
		log.debug("Order Id from Request Body ", orderId);
		cartResponse = orderService.getCartDetails(orderId);
		cartResponse.setStatus(Boolean.TRUE);
		cartResponse.setMessage("Item successfully updated.");
		cartResponse.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(cartResponse, new HttpHeaders(), HttpStatus.OK);
		}

}
