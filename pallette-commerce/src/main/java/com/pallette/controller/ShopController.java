package com.pallette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/rest/api/v1")
public class ShopController {
	
	@Autowired
    private OAuth2RestOperations restTemplate;

    @Value("https://api.molt.in/v1/products/")
    private String productsURI;

    @RequestMapping("/products")
    public JsonNode home() {
    	JsonNode node = restTemplate.getForObject(productsURI, JsonNode.class);
    	System.out.println(node);
        return node;
    }
}
