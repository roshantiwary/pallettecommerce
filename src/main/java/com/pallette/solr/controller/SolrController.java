package com.pallette.solr.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.solr.domain.SolrProduct;
import com.pallette.solr.repository.SolrProductRepository;
import com.pallette.solr.response.ProductsResponse;

@RestController
@RequestMapping("/rest/api/v1")
public class SolrController {

	private static final Logger logger = LoggerFactory.getLogger(SolrController.class);

	@Autowired
	SolrProductRepository solrProductRepository;
	
	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;

	/**
	 * 
	 * @return
	 */
	@PostMapping("/index/solr/products")
	public ResponseEntity<?> indexProducts() {

		logger.debug("Inside SolrController.indexProducts()");
		List<ProductDocument> products = productRepository.findAll();
		if (null != products && !products.isEmpty()) {
			for (ProductDocument product : products) {

				SolrProduct solrProduct = new SolrProduct();
				solrProduct.setId(product.getId());
				solrProduct.setProductDescription(product.getProductDescription());
				solrProduct.setProductSlug(product.getProductSlug());
				solrProduct.setProductStatus(product.getProductStatus());
				solrProduct.setProductTitle(product.getProductTitle());
				solrProductRepository.save(solrProduct);
			}
		}
		return new ResponseEntity("Successfully uploaded", new HttpHeaders(), HttpStatus.OK);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/get/solr/products", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProductsResponse getProductsFromSolr() {
		
		logger.debug("Inside SolrController.getProducts()");
		SolrResultPage<SolrProduct> products = (SolrResultPage<SolrProduct>) solrProductRepository.findAll();
		ProductsResponse response = new ProductsResponse();
		response.setProducts(products);
		return response;
	}

}
