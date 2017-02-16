package com.pallette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.pallette.domain.CartItem;

@RestController
@RequestMapping("/rest/api/v1")
public class CartController {

	@Autowired
    private OAuth2RestOperations restTemplate;
	
	@Value("https://api.molt.in/v1/carts/")
	private String cartURI;
	
	@RequestMapping(value="/cart/add", method=RequestMethod.POST)
    public JsonNode getProduct(@RequestBody CartItem item)
    {
		String cartId = item.getCartId();
		if(cartId.isEmpty()) {
			cartId= "234556";
		}
		
    	String addItemURI = cartURI + "/" + cartId;
    	JsonNode node = restTemplate.postForObject(addItemURI, item, JsonNode.class);
    	return node;
    }
}
