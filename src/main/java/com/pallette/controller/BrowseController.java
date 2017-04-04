package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.beans.BrandBean;
import com.pallette.beans.CategoryResponse;
import com.pallette.beans.ProductResponseBean;
import com.pallette.constants.RestURLConstants;
import com.pallette.domain.BrandDocument;
import com.pallette.domain.CategoryDocument;
import com.pallette.domain.ProductDocument;
import com.pallette.exception.NoRecordsFoundException;
import com.pallette.response.GenericResponse;
import com.pallette.service.BrandService;
import com.pallette.service.CategoryService;
import com.pallette.service.ProductService;


/**
 * <p>
 * Controller that exposes all the product related rest calls.
 * </p>
 * 
 * @author amall3
 *
 */
@RestController
@RequestMapping("/rest/api/v1")
public class BrowseController {

	private static final Logger logger = LoggerFactory.getLogger(BrowseController.class);

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	
	
	@RequestMapping(value = RestURLConstants.ALL_PRODUCTS_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getAllProducts() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseController.getAllProduct()");
		List<ProductDocument> products = productService.getAllProduct();

		ProductResponseBean response = productService.constructProductResponse(products);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
		
		
	}

	

	@RequestMapping(value = RestURLConstants.SELECTED_PRODUCT_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProduct(@PathVariable("productId") String productId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		logger.debug("BrowseController.getProduct()");
		if (StringUtils.isEmpty(productId)) {
			throw new IllegalArgumentException("No Product Id was Passed");
		}
		
		logger.debug("Product Id passed is : " + productId);
		ProductDocument product = productService.getProductById(productId);
		List<ProductDocument> productList = new ArrayList<ProductDocument>();
		productList.add(product);
		ProductResponseBean response = productService.constructProductResponse(productList);
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Item was Returned Successfully.");
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	@RequestMapping(value = RestURLConstants.GET_PRODUCTS_BY_TITLE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProductsByTitle(@PathVariable("productTitle") String productTitle) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseController.getProductByTitle()");
		if (StringUtils.isEmpty(productTitle)) { 
			throw new IllegalArgumentException("No Product Title was Passed");
		}
		
		logger.debug("Product Title passed is : ", productTitle);
		List<ProductDocument> products = productService.getProductByTitle(productTitle);
		
		ProductResponseBean response = productService.constructProductResponse(products);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	@RequestMapping(value = RestURLConstants.GET_PRODUCTS_BY_BRAND_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProductsByBrand(@PathVariable("brandId") String brandId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseController.getProductsByBrand()");
		if (StringUtils.isEmpty(brandId)) { 
			throw new IllegalArgumentException("Brand Id not Passed");
		}
		
		logger.debug("Brand Id passed is : ", brandId);
		List<ProductDocument> products = productService.getProductByBrand(brandId);
		
		ProductResponseBean response = productService.constructProductResponse(products);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}
	
	@RequestMapping(value= RestURLConstants.ALL_CATEGORIES_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getAllCategories() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {
		
		logger.debug("BrowseController.getAllCategories()");
		GenericResponse genericResponse = new GenericResponse();
		List<CategoryDocument> categories = categoryService.getAllCategories();
		List<CategoryResponse> categoryBeanList= new ArrayList<CategoryResponse>(); 

		for (CategoryDocument category: categories ) {
	    	CategoryResponse categoryBean= new CategoryResponse();
	        BeanUtils.copyProperties(categoryBean , category);
	        categoryBeanList.add(categoryBean);
		}
		
		if (null != categoryBeanList && !categoryBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categoryBeanList);
			genericResponse.setItemCount(categoryBeanList.size());
			genericResponse.setMessage("Category Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
    	
	@RequestMapping(value = RestURLConstants.SELECTED_CATEGORY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getCategory(@PathVariable("categoryId") String categoryId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException{

		logger.debug("BrowseController.getCategory()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(categoryId)) {
			throw new IllegalArgumentException("No Category Id was Passed");
		}

		logger.debug("Category Id passed is : " + categoryId);
		CategoryDocument category = categoryService.getCategoryById(categoryId);
		CategoryResponse categoryBean = new CategoryResponse();
		BeanUtils.copyProperties(categoryBean, category);
		if (null != categoryBean) {
			List<CategoryResponse> categories = new ArrayList<CategoryResponse>();
			categories.add(categoryBean);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categories);
			genericResponse.setItemCount(categories.size());
			genericResponse.setMessage("Category Item was Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	@RequestMapping(value = RestURLConstants.GET_CATEGORIES_BY_TITLE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getCategoriesByTitle(@PathVariable("categoryTitle") String categoryTitle) 
			throws IllegalArgumentException , NoRecordsFoundException, IllegalAccessException, InvocationTargetException{
		
		logger.debug("BrowseController.getCategoriesByTitle()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(categoryTitle)) {
			throw new IllegalArgumentException("No Category Title was Passed.");
		}
		
		logger.debug("Category Title passed is : ", categoryTitle);
		List<CategoryDocument> categories = categoryService.getCategoryByTitle(categoryTitle);
		List<CategoryResponse> categoryBeanList= new ArrayList<CategoryResponse>(); 

		for (CategoryDocument category: categories ) {
	    	CategoryResponse categoryBean= new CategoryResponse();
	        BeanUtils.copyProperties(categoryBean , category);
	        categoryBeanList.add(categoryBean);
		}
		
		if (null != categoryBeanList && !categoryBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categoryBeanList);
			genericResponse.setItemCount(categoryBeanList.size());
			genericResponse.setMessage("Category Items were Returned Successfully.");
			
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	
	@RequestMapping(value= RestURLConstants.ALL_BRANDS_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getAllBrands() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {

		logger.debug("BrowseController.getAllBrands()");
		GenericResponse genericResponse = new GenericResponse();
		List<BrandDocument> brands = brandService.getAllBrands();
		List<BrandBean> brandBeanList= new ArrayList<BrandBean>(); 

		for (BrandDocument brand: brands ) {
	    	BrandBean brandBean= new BrandBean();
	        BeanUtils.copyProperties(brandBean , brand);
	        brandBeanList.add(brandBean);
		}
		
		if (null != brandBeanList && !brandBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brandBeanList);
			genericResponse.setItemCount(brandBeanList.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
    	
	@RequestMapping(value = RestURLConstants.SELECTED_BRAND_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getBrand(@PathVariable("brandId") String brandId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.debug("BrowseController.getBrand()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(brandId)) { 
			throw new IllegalArgumentException("No Brand Id was Passed.");
		}

		logger.debug("Brand Id passed is : " + brandId);
		BrandDocument brand = brandService.getBrandById(brandId);
		BrandBean brandBean = new BrandBean();
		BeanUtils.copyProperties(brandBean, brand);
		if (null != brandBean) {
			List<BrandBean> brands = new ArrayList<BrandBean>();
			brands.add(brandBean);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Category Item was Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_CITY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getBrandByCity(@PathVariable("city") String city) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.debug("BrowseController.getBrandByCity()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(city)) { 
			throw new IllegalArgumentException("No Brand City was Passed.");
		}

		logger.debug("Brand City passed is : " + city);
		List<BrandDocument> brands = brandService.getBrandByCity(city);
		List<BrandBean> brandBeanList= new ArrayList<BrandBean>(); 

		for (BrandDocument brand: brands ) {
	    	BrandBean brandBean= new BrandBean();
	        BeanUtils.copyProperties(brandBean , brand);
	        brandBeanList.add(brandBean);
		}
		
		if (null != brandBeanList && !brandBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brandBeanList);
			genericResponse.setItemCount(brandBeanList.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_STATE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getBrandByState(@PathVariable("state") String state) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.debug("BrowseController.getBrandByState()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(state)) { 
			throw new IllegalArgumentException("No Brand State was Passed.");
		}

		logger.debug("Brand state passed is : " + state);
		List<BrandDocument> brands = brandService.getBrandByState(state);
		List<BrandBean> brandBeanList= new ArrayList<BrandBean>(); 

		for (BrandDocument brand: brands ) {
	    	BrandBean brandBean= new BrandBean();
	        BeanUtils.copyProperties(brandBean , brand);
	        brandBeanList.add(brandBean);
		}
		
		if (null != brandBeanList && !brandBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brandBeanList);
			genericResponse.setItemCount(brandBeanList.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_POSTAL_CODE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GenericResponse> getBrandByPostalCode(@PathVariable("postalCode") String postalCode) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.debug("BrowseController.getBrandByPostalCode()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(postalCode)) { 
			throw new IllegalArgumentException("No Brand Postal Code was Passed.");
		}

		logger.debug("Brand postalCode passed is : " + postalCode);
		List<BrandDocument> brands = brandService.getBrandByPostalCode(postalCode);
		List<BrandBean> brandBeanList= new ArrayList<BrandBean>(); 

		for (BrandDocument brand: brands ) {
	    	BrandBean brandBean= new BrandBean();
	        BeanUtils.copyProperties(brandBean , brand);
	        brandBeanList.add(brandBean);
		}
		
		if (null != brandBeanList && !brandBeanList.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brandBeanList);
			genericResponse.setItemCount(brandBeanList.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}

}
