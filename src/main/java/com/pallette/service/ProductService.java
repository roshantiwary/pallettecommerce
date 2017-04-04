/**
 * 
 */
package com.pallette.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.pallette.beans.CategoryResponse;
import com.pallette.beans.ImageResponse;
import com.pallette.beans.ProductResponse;
import com.pallette.beans.ProductResponseBean;
import com.pallette.beans.SkuResponse;
import com.pallette.commerce.contants.CommerceConstants;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ImagesDocument;
import com.pallette.domain.ProductDocument;
import com.pallette.domain.SkuDocument;
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
	
	@Autowired
	private MongoOperations mongoOperation;

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

	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the Brand Id.
	 * 
	 * @return List<ProductDocument>
	 */
	public List<ProductDocument> getProductByBrand(String brandId) {

		logger.debug("ProductService.getProductByBrand() , parameter passed is: " + brandId);
		List<ProductDocument> products = new ArrayList<ProductDocument>();

		products = productRepository.findByProductBrandId(brandId);
		if (null != products && !products.isEmpty()) {
			logger.info(String.format("ProductService.getProductByBrand - retrieved product list is: %s" , products.toString()));
		} else {
			logger.warn("ProductService.getProductByBrand(): could not find any Products from Repository.");
		}
		return products;
	}
	
	   
	/**
	 * Method To construct product response
	 * 
	 * @param products
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ProductResponseBean constructProductResponse(List<ProductDocument> products) throws IllegalAccessException, InvocationTargetException {
		List<ProductResponse> responseList = new ArrayList<ProductResponse>();
		ProductResponseBean responseBean = new ProductResponseBean();
		if(null != products && !products.isEmpty()){
			for(ProductDocument product : products){
				if(product.getProductStatus().equalsIgnoreCase(CommerceConstants.FALSE))
					continue;
				
				ProductResponse response = new ProductResponse();
				response.setId(product.getId());
				response.setProductTitle(product.getProductTitle());
				response.setProductDescription(product.getProductDescription());
				populateSkuDetails(response,product.getSkuDocument());
				populateImageDetails(response,product.getImagesDocument());
				populateCategoryDetails(response,product.getCategoryDocument());
				responseList.add(response);
			}
		}
		responseBean.setProductResponse(responseList);
		return responseBean;
	}



	/**
	 * This method populate category details for 
	 * product response.
	 * 
	 * @param response
	 * @param categoryDocument
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void populateCategoryDetails(ProductResponse response,CategoryDocument categoryDocument) 
			throws IllegalAccessException, InvocationTargetException {
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setId(categoryDocument.getId());
		categoryResponse.setCategoryTitle(categoryDocument.getCategoryTitle());
		categoryResponse.setCategoryDescription(categoryDocument.getCategoryDescription());
		ImageResponse imageResponse = new ImageResponse();
		BeanUtils.copyProperties(imageResponse, categoryDocument.getImagesDocument());
		categoryResponse.setImageResponse(imageResponse);
	}



	/**
	 * This Method populates image details
	 * for product response.
	 * 
	 * @param response
	 * @param imagesDocument
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void populateImageDetails(ProductResponse response,ImagesDocument imagesDocument) 
			throws IllegalAccessException, InvocationTargetException {
		ImageResponse imageResponse = new ImageResponse();
		BeanUtils.copyProperties(imageResponse, imagesDocument);
		response.setImageResponse(imageResponse);
	}



	/**
	 * This method populate sku Details 
	 * for product response
	 * 
	 * @param response
	 * @param skuDocument
	 */
	private void populateSkuDetails(ProductResponse response,List<SkuDocument> skuDocument) {
		List<SkuResponse> skuResponseList = new ArrayList<SkuResponse>();
		if(null != skuDocument && !skuDocument.isEmpty()){
			for(SkuDocument sku : skuDocument){
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(sku.getId()));
				SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
				if(null == skuItem)
					continue;
				
				SkuResponse skuResponse = new SkuResponse();
				skuResponse.setId(skuItem.getId());
				skuResponse.setName(skuItem.getName());
				skuResponse.setUnitOfMeasure(skuItem.getUnitOfMeasure());
				skuResponse.setAvailableStockLevel(skuItem.getInventoryDocument().getAvailableStockLevel());
				skuResponse.setListPrice(skuItem.getPriceDocument().getListPrice());
				skuResponse.setSalePrice(skuItem.getPriceDocument().getSalePrice());
				skuResponseList.add(skuResponse);
			}
		}
		response.setSkuResponse(skuResponseList);
	}
}
