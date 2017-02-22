package com.pallette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Value("${url.moltin.api.cart}")
	private String cartURI;

	@PreAuthorize("#oauth2.hasScope('write')")
	@RequestMapping(value="/cart/add", method=RequestMethod.POST)
    public JsonNode addItemToCart(@RequestBody CartItem item)
    {
		String cartId = item.getCartId();
		if(cartId.isEmpty()) {
			cartId= "234556";
		}
		
    	String addItemURI = cartURI + "/" + cartId;
    	
    	MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("id", item.getProductId());
		map.add("quantity", item.getQuantity());
		
    	JsonNode node = restTemplate.postForObject(addItemURI, map, JsonNode.class);
    	return node;
    }
	
	@RequestMapping(value="/cart/{cartId}", method=RequestMethod.GET, produces="application/json")
    public JsonNode getCart(@PathVariable("cartId") String cartId)
    {
		String getCartURI = cartURI + "/" + cartId;
    	JsonNode node = restTemplate.getForObject(getCartURI, JsonNode.class);
    	return node;
    }
}
