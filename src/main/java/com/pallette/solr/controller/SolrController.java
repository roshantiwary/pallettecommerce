package com.pallette.solr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.solr.domain.SolrProduct;
import com.pallette.solr.repository.SolrProductRepository;

@RestController
@RequestMapping("/rest/api/v1")
public class SolrController {
	
	private static final Logger logger = LoggerFactory.getLogger(SolrController.class);
	
	@Autowired
	SolrProductRepository productRepository;

	@PostMapping("/index/products")
    public ResponseEntity<?> indexProducts() {
        logger.debug("Product file upload");
       SolrProduct product = new SolrProduct();
       product.setId("123");
       product.setProductDescription("productDescrition");
       product.setProductSlug("productSlug");
       product.setProductStatus("productStatus");
       product.setProductTitle("productTitle");
       productRepository.save(product);
       
       return new ResponseEntity("Successfully uploaded" , new HttpHeaders(), HttpStatus.OK);
    }

}
