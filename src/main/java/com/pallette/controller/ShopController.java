package com.pallette.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/rest/api/v1")
public class ShopController {
	
	@Autowired
    private OAuth2RestOperations restTemplate;

    @Value("${url.moltin.api.products}")
    private String productsURI;

    @Value("${url.moltin.api.categories}")
    private String categoriesURI;
    
    @Value("${url.moltin.api.products-search}")
    private String searchProductbyField;

    @RequestMapping("/products")
    public JsonNode getAllProducts() {
    	JsonNode node = restTemplate.getForObject(productsURI, JsonNode.class);
        return node;
    }
    
    @RequestMapping("/categories")
    public JsonNode getAllCategories() {
    	JsonNode node = restTemplate.getForObject(categoriesURI, JsonNode.class);
        return node;
    }
    
    @RequestMapping(value="/categories/{categoryId}", method=RequestMethod.GET, produces="application/json")
    public JsonNode getCategory(@PathVariable("categoryId") String categoryId)
    {
    	String categoryURI = categoriesURI + "/" + categoryId;
    	JsonNode node = restTemplate.getForObject(categoryURI, JsonNode.class);
    	return node;
    }
    
    @RequestMapping(value="/products/{productId}", method=RequestMethod.GET, produces="application/json")
    public JsonNode getProduct(@PathVariable("productId") String productId)
    {
    	String productURI = productsURI + "/" + productId;
    	JsonNode node = restTemplate.getForObject(productURI, JsonNode.class);
    	return node;
    }
    
    @RequestMapping(value="/products/search/?category={categoryId}", method=RequestMethod.GET, produces="application/json")
    public JsonNode getProductByCategory(@PathVariable("categoryId") String categoryId)
    {
    	String productByCategory = searchProductbyField + categoryId;
    	JsonNode node = restTemplate.getForObject(productByCategory, JsonNode.class);
    	return node;
    } 
}
