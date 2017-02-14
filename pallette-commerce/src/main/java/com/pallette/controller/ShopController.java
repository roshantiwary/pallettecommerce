package com.pallette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/rest/api/v1")
public class ShopController {
	
	@Autowired
    private OAuth2RestOperations restTemplate;

    @Value("https://api.molt.in/v1/products/")
    private String productsURI;

    @Value("https://api.molt.in/v1/categories")
    private String categoriesURI;

    @RequestMapping("/products")
    public JsonNode getAllProducts() {
    	JsonNode node = restTemplate.getForObject(productsURI, JsonNode.class);
    	System.out.println(node);
        return node;
    }
    
    @RequestMapping("/categories")
    public JsonNode getAllCategories() {
    	JsonNode node = restTemplate.getForObject(categoriesURI, JsonNode.class);
    	System.out.println(node);
        return node;
    }
    
    @RequestMapping(value="/categories/{categoryId}", method=RequestMethod.GET, produces="application/json")
    public JsonNode getCategory(@PathVariable("categoryId") String categoryId)
    {
    	String categoryURI = categoriesURI + categoryId;
    	JsonNode node = restTemplate.getForObject(categoryURI, JsonNode.class);
    	return node;
    }
    
    
}
