/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.repository.ProductRepository;
import com.pallette.browse.response.ProductResponseBean;

/**
 * <p>
 * Implementation class for product repository operations. This class includes
 * methods that perform database operations related to product.
 * </p>
 * 
 * @author amall3
 *
 */
@Service
public class ProductServiceImpl implements ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	/**
	 * The Product repository.
	 */
	@Autowired
	private ProductRepository productRepository;
	
	/**
	 * Injecting Mongo Operations for Mongo related processing.
	 */
	@Autowired
	private MongoOperations mongoOperation;
	
	@Autowired
	private BrowseServices browseServices;

	/**
	 * Method responsible for invoking the product repository to fetch all the
	 * products.
	 * 
	 * @return ProductResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ProductResponseBean getAllProduct() throws IllegalAccessException, InvocationTargetException {

		logger.debug("ProductServiceImpl.getAllProduct()");
		ProductResponseBean response = null;

		 List<ProductDocument> products = productRepository.findAll();
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductServiceImpl.getAllProduct - retrieved product list is: %s" , products.toString()));
			response = browseServices.constructProductResponse(products);
		} else {
			logger.warn("ProductServiceImpl.getAllProduct(): could not find any Products from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the product repository to fetch selected
	 * product details.
	 * 
	 * @return ProductResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ProductResponseBean getProductById(Long productId) throws IllegalAccessException, InvocationTargetException {

		logger.debug("ProductServiceImpl.getProductById() , parameter passed is: " + productId);
		ProductResponseBean response = null;
		
		Optional<ProductDocument> product = productRepository.findById(productId);
		if (null != product) {
			logger.info(String.format("ProductServiceImpl.getProductById - retrieved product is: " , product.get().getId()));
			response = browseServices.constructProductResponse(new ArrayList<ProductDocument>(Arrays.asList(product.get())));
		} else {
			logger.warn("ProductServiceImpl.getProductById(): could not find any Product from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the product title.
	 * 
	 * @return ProductResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ProductResponseBean getProductByTitle(String productTitle) throws IllegalAccessException, InvocationTargetException {

		logger.debug("ProductServiceImpl.getProductByTitle() , parameter passed is: " + productTitle);
		ProductResponseBean response = null;

		List<ProductDocument> products = productRepository.findByProductTitle(productTitle);
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductServiceImpl.getProductByTitle - retrieved product list is: %s", products.toString()));
			response = browseServices.constructProductResponse(products);
		} else {
			logger.warn("ProductServiceImpl.getProductByTitle(): could not find any Products from Repository.");
		}
		return response;
	}

	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the Brand Id.
	 * 
	 * @return ProductResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public ProductResponseBean getProductByBrand(String brandId) throws IllegalAccessException, InvocationTargetException {

		logger.debug("ProductServiceImpl.getProductByBrand() , parameter passed is: " + brandId);
		ProductResponseBean response = null;

		List<ProductDocument> products = productRepository.findByProductBrandId(brandId);
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductServiceImpl.getProductByBrand - retrieved product list is: %s" , products.toString()));
			response = browseServices.constructProductResponse(products);
		} else {
			logger.warn("ProductServiceImpl.getProductByBrand(): could not find any Products from Repository.");
		}
		return response;
	}
	
	   
	
}
