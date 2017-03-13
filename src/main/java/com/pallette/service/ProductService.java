/**
 * 
 */
package com.pallette.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pallette.domain.ProductDocument;
import com.pallette.repository.ProductRepository;

/**
 * <p>
 * Service class for product repository operations. This class includes methods
 * that perform database operations related to product.
 * </p>
 * 
 * @author amall3
 *
 */

@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;

	/**
	 * Method responsible for invoking the product repository to fetch all the
	 * products.
	 * 
	 * @return List<ProductDocument>
	 */
	public List<ProductDocument> getAllProduct() {

		logger.debug("ProductService.getAllProduct()");
		List<ProductDocument> products = new ArrayList<ProductDocument>();

		products = productRepository.findAll();
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductService.getAllProduct - retrieved product list is: %s" , products.toString()));
		} else {
			logger.warn("ProductService.getAllProduct(): could not find any Products from Repository.");
		}
		return products;
	}
	
	
	/**
	 * Method responsible for invoking the product repository to fetch selected
	 * product.
	 * 
	 * @return ProductDocument
	 */
	public ProductDocument getProductById(String productId) {

		logger.debug("ProductService.getProductById() , parameter passed is: " + productId);
		ProductDocument product = null;

		product = productRepository.findOne(productId);
		if (null != product) {
			logger.info(String.format("ProductService.getProductById - retrieved product is: " , product.getId()));
		} else {
			logger.warn("ProductService.getProductById(): could not find any Product from Repository.");
		}
		return product;
	}
	
	
	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the product title.
	 * 
	 * @return List<ProductDocument>
	 */
	public List<ProductDocument> getProductByTitle(String productTitle) {

		logger.debug("ProductService.getProductByTitle() , parameter passed is: " + productTitle);
		List<ProductDocument> products = new ArrayList<ProductDocument>();

		products = productRepository.findByProductTitle(productTitle);
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductService.getProductByTitle - retrieved product list is: %s" , products.toString()));
		} else {
			logger.warn("ProductService.getProductByTitle(): could not find any Products from Repository.");
		}
		return products;
	}
	
}
