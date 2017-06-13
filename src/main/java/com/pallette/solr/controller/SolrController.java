package com.pallette.solr.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.solr.domain.SolrProduct;
import com.pallette.solr.repository.SolrProductRepository;
import com.pallette.solr.response.ProductsResponse;

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
       product.setProductDescription("zindagi");
       product.setProductSlug("adf");
       product.setProductStatus("productStatus");
       product.setProductTitle("productTitle");
       productRepository.save(product);
       
       
       SolrProduct product1 = new SolrProduct();
       product.setId("456");
       product.setProductDescription("roger");
       product.setProductSlug("copy");
       product.setProductStatus("true");
       product.setProductTitle("tiger");
       productRepository.save(product1);
       
       SolrProduct product2 = new SolrProduct();
       product.setId("789");
       product.setProductDescription("shah");
       product.setProductSlug("salman");
       product.setProductStatus("rukh");
       product.setProductTitle("khan");
       productRepository.save(product2);
       
       return new ResponseEntity("Successfully uploaded" , new HttpHeaders(), HttpStatus.OK);
    }
	
	@GetMapping("/index/products")
    public ProductsResponse getProducts() {
        logger.debug("Product file upload");
      
        SolrResultPage<SolrProduct> products = (SolrResultPage<SolrProduct>) productRepository.findAll();
       
       ProductsResponse response = new ProductsResponse();
       response.setProducts(products);
       
       return response;
    }

}
