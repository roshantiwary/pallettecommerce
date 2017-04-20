/**
 * 
 */
package com.pallette.browse.api;

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
import org.springframework.util.StringUtils;

import com.pallette.beans.SkuResponse;
import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.documents.ImagesDocument;
import com.pallette.browse.documents.ProductDocument;
import com.pallette.browse.documents.SkuDocument;
import com.pallette.browse.response.BrandBean;
import com.pallette.browse.response.BrandResponseBean;
import com.pallette.browse.response.CategoryResponse;
import com.pallette.browse.response.CategoryResponseBean;
import com.pallette.browse.response.ImageResponse;
import com.pallette.browse.response.ProductResponse;
import com.pallette.browse.response.ProductResponseBean;
import com.pallette.commerce.contants.CommerceConstants;

/**
 * @author amall3
 *
 */

@Service
public class BrowseServices {
	
	private static final Logger logger = LoggerFactory.getLogger(BrowseServices.class);

	/**
	 * Injecting Mongo Operations for Mongo related processing.
	 */
	@Autowired
	private MongoOperations mongoOperation;

	
	/**
	 * Method To construct product response.
	 * 
	 * @param products
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public ProductResponseBean constructProductResponse(List<ProductDocument> products) throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.constructProductResponse()");
		
		List<ProductResponse> responseList = new ArrayList<ProductResponse>();
		ProductResponseBean responseBean = new ProductResponseBean();
		
		if (null != products && !products.isEmpty()) {
			for (ProductDocument product : products) {
				
				String prodStatus = product.getProductStatus();
				if (StringUtils.isEmpty(prodStatus))
					continue;
				
				if (CommerceConstants.FALSE.equalsIgnoreCase(prodStatus))
					continue;
				
				ProductResponse response = new ProductResponse();
				BeanUtils.copyProperties(response , product);
				populateSkuDetails(response, product.getSkuDocument());
				populateImageDetails(response, product.getImagesDocument());
				populateCategoryDetails(response, product.getCategoryDocument());
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
	public void populateCategoryDetails(ProductResponse response, CategoryDocument categoryDocument)  throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.populateCategoryDetails()");
		
		if (null == categoryDocument)
			return;

		CategoryResponse categoryResponse = new CategoryResponse();
		BeanUtils.copyProperties(categoryResponse , categoryDocument);
		ImageResponse imageResponse = new ImageResponse();
		BeanUtils.copyProperties(imageResponse, categoryDocument.getImagesDocument());
		categoryResponse.setImage(imageResponse);
		response.setCategoryResponse(categoryResponse);
	}



	/**
	 * This Method populates image details for product response.
	 * 
	 * @param response
	 * @param imagesDocument
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void populateImageDetails(ProductResponse response, ImagesDocument imagesDocument) throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.populateImageDetails()");
		if (null == imagesDocument)
			return;
		
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
	public void populateSkuDetails(ProductResponse response, List<SkuDocument> skuDocument) {
		
		logger.debug("BrowseServices.populateImageDetails()");
		List<SkuResponse> skuResponseList = new ArrayList<SkuResponse>();
		
		if (null != skuDocument && !skuDocument.isEmpty()) {
			for (SkuDocument sku : skuDocument) {
				Query query = new Query();
				query.addCriteria(Criteria.where("_id").is(sku.getId()));
				SkuDocument skuItem = mongoOperation.findOne(query, SkuDocument.class);
				if (null == skuItem)
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
	
	/**
	 * Method To construct Category response.
	 * 
	 * @param categories
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public CategoryResponseBean constructCategoryResponse(List<CategoryDocument> categories) throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.constructProductResponse()");
		
		List<CategoryResponse> responseList = new ArrayList<CategoryResponse>();
		CategoryResponseBean responseBean = new CategoryResponseBean();
		
		if (null != categories && !categories.isEmpty()) {
			for (CategoryDocument category : categories) {
				
				String prodStatus = category.getCategoryStatus();
				if (StringUtils.isEmpty(prodStatus))
					continue;
				
				if (CommerceConstants.FALSE.equalsIgnoreCase(prodStatus))
					continue;

				CategoryResponse response = new CategoryResponse();
				BeanUtils.copyProperties(response , category);
				populateImageDetails(response, category.getImagesDocument());
				populateCategoryDetails(response, category.getParentCategoryDocument());
				responseList.add(response);
			}
		}
		responseBean.setCategoryResponseList(responseList);
		return responseBean;
	}
	
	/**
	 * This Method populates image details for Category response.
	 * 
	 * @param response
	 * @param imagesDocument
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void populateImageDetails(CategoryResponse response, ImagesDocument imagesDocument) throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.populateImageDetails()");
		if (null == imagesDocument)
			return;
		
		
		ImageResponse imageResponse = new ImageResponse();
		BeanUtils.copyProperties(imageResponse, imagesDocument);
		response.setImage(imageResponse);
	}
	
	/**
	 * This method populate category details for category response.
	 * 
	 * @param response
	 * @param categoryDocument
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public void populateCategoryDetails(CategoryResponse response, CategoryDocument categoryDocument)  throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.populateCategoryDetails()");
		if (null == categoryDocument)
			return;
		
		CategoryResponse categoryResponse = new CategoryResponse();
		BeanUtils.copyProperties(categoryResponse , categoryDocument);
		ImageResponse imageResponse = new ImageResponse();
		BeanUtils.copyProperties(imageResponse, categoryDocument.getImagesDocument());
		categoryResponse.setImage(imageResponse);
		response.setParentCategory(categoryResponse);
	}
	
	/**
	 * Method To construct brand response.
	 * 
	 * @param brands
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public BrandResponseBean constructBrandResponse(List<BrandDocument> brands) throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseServices.constructBrandResponse()");
		
		List<BrandBean> responseList = new ArrayList<BrandBean>();
		BrandResponseBean responseBean = new BrandResponseBean();
		
		if (null != brands && !brands.isEmpty()) {
			for (BrandDocument brand : brands) {
				
				BrandBean response = new BrandBean();
				BeanUtils.copyProperties(response , brand);
				responseList.add(response);
			}
		}
		responseBean.setBrands(responseList);
		return responseBean;
	}

}
