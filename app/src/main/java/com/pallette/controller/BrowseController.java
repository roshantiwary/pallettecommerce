package com.pallette.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pallette.browse.api.BrandService;
import com.pallette.browse.api.CategoryService;
import com.pallette.browse.api.ProductService;
import com.pallette.browse.constants.BrowseConstants;
import com.pallette.browse.response.BrandResponseBean;
import com.pallette.browse.response.CategoryResponse;
import com.pallette.browse.response.CategoryResponseBean;
import com.pallette.browse.response.ProductResponseBean;
import com.pallette.constants.RestURLConstants;
import com.pallette.exception.NoRecordsFoundException;


/**
 * <p>
 * Controller that is invoked in browse pages and is responsible for providing
 * api's related to products , categories and brands.
 * </p>
 * 
 * @author amall3
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/rest/api/v1")
public class BrowseController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private BrandService brandService;
	
	/**
	 * Method that fetches all the products available in the Mongo
	 * Repository.
	 * 
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.ALL_PRODUCTS_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getAllProducts() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {
		
		logger.info("Inside BrowseController.getAllProduct()");
		ProductResponseBean response = productService.getAllProduct();

		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	/**
	 * Method that fetches selected product available in the Mongo Repository.
	 * 
	 * @param productId
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.SELECTED_PRODUCT_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProduct(@PathVariable(BrowseConstants.PRODUCT_ID) String productId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		
		logger.info("BrowseController.getProduct()");
		logger.debug("Product Id passed is : " + productId);
		
		ProductResponseBean response = productService.getProductById(productId);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Item was Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	/**
	 * Method that fetches products based on product title.
	 * 
	 * @param productTitle
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.GET_PRODUCTS_BY_TITLE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProductsByTitle(@PathVariable(BrowseConstants.PRODUCT_TITLE) String productTitle) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		logger.info("BrowseController.getProductByTitle()");
		logger.debug("Product Title passed is : ", productTitle);
		
		ProductResponseBean response = productService.getProductByTitle(productTitle);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	/**
	 * Method that fetches products based on brand.
	 * 
	 * @param brandId
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.GET_PRODUCTS_BY_BRAND_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ProductResponseBean> getProductsByBrand(@PathVariable(BrowseConstants.BRAND_ID) String brandId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		
		logger.info("BrowseController.getProductsByBrand()");
		logger.debug("Brand Id passed is : ", brandId);
		ProductResponseBean response = productService.getProductByBrand(brandId);
		
		if (null != response && !response.getProductResponse().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}
	
	/**
	 * Method that fetches all the categories available in the Mongo Repository.
	 * 
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value= RestURLConstants.ALL_CATEGORIES_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryResponseBean> getAllCategories() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {
		
		logger.info("BrowseController.getAllCategories()");
		CategoryResponseBean response = categoryService.getAllCategories();

		if (null != response && !response.getCategoryResponseList().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Category Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
    	
	/**
	 * Method that fetches selected category available in the Mongo Repository.
	 * 
	 * @param categoryId
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.SELECTED_CATEGORY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryResponseBean> getCategory(@PathVariable(BrowseConstants.CATEGORY_ID) String categoryId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException{

		logger.info("BrowseController.getCategory()");
		logger.debug("Category Id passed is : " + categoryId);
		CategoryResponseBean response = categoryService.getCategoryById(categoryId);
		
		if (null != response && !response.getCategoryResponseList().isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatus(Boolean.TRUE);
			response.setMessage("Category Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	/**
	 * Method that returns categories based on title.
	 * 
	 * @param categoryTitle
	 * @return
	 * @throws IllegalArgumentException
	 * @throws NoRecordsFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.GET_CATEGORIES_BY_TITLE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryResponseBean> getCategoriesByTitle(@PathVariable(BrowseConstants.CATEGORY_TITLE) String categoryTitle) 
			throws IllegalArgumentException , NoRecordsFoundException, IllegalAccessException, InvocationTargetException{
		
		logger.info("BrowseController.getCategoriesByTitle()");
		logger.debug("Category Title passed is : ", categoryTitle);
		CategoryResponseBean response  = categoryService.getCategoryByTitle(categoryTitle);
		
		List<CategoryResponse> categoryBeanList= new ArrayList<CategoryResponse>(); 

		if (null != response && !response.getCategoryResponseList().isEmpty()) {
			response.setStatusCode(HttpStatus.OK.value());
			response.setStatus(Boolean.TRUE);
			response.setMessage("Category Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	/**
	 * Method that fetches all the brands available in the Mongo Repository.
	 * 
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value= RestURLConstants.ALL_BRANDS_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrandResponseBean> getAllBrands() throws NoRecordsFoundException, IllegalAccessException, InvocationTargetException {

		logger.info("BrowseController.getAllBrands()");
		BrandResponseBean response = brandService.getAllBrands();
		
		if (null != response && !response.getBrands().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
    
	/**
	 * Method that fetches selected brand available in the Mongo Repository
	 * based on id.
	 * 
	 * @param brandId
	 * @return
	 * @throws NoRecordsFoundException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = RestURLConstants.SELECTED_BRAND_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrandResponseBean> getBrand(@PathVariable(BrowseConstants.BRAND_ID) String brandId) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.info("BrowseController.getBrand()");
		logger.debug("Brand Id passed is : " + brandId);
		BrandResponseBean response = brandService.getBrandById(brandId);
		
		if (null != response && !response.getBrands().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_CITY_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrandResponseBean> getBrandByCity(@PathVariable(BrowseConstants.CITY2) String city) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.info("BrowseController.getBrandByCity()");
		logger.debug("Brand City passed is : " + city);
		
		BrandResponseBean response = brandService.getBrandByCity(city);
		if (null != response && !response.getBrands().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_STATE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrandResponseBean> getBrandByState(@PathVariable(BrowseConstants.STATE2) String state) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.info("BrowseController.getBrandByState()");
		logger.debug("Brand state passed is : " + state);
		
		BrandResponseBean response = brandService.getBrandByState(state);

		if (null != response && !response.getBrands().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = RestURLConstants.GET_BRANDS_BY_POSTAL_CODE_URL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrandResponseBean> getBrandByPostalCode(@PathVariable(BrowseConstants.POSTAL_CODE) String postalCode) 
			throws NoRecordsFoundException , IllegalArgumentException, IllegalAccessException, InvocationTargetException {

		logger.info("BrowseController.getBrandByPostalCode()");
		logger.debug("Brand postalCode passed is : " + postalCode);
		
		BrandResponseBean response = brandService.getBrandByPostalCode(postalCode);

		if (null != response && !response.getBrands().isEmpty()) {
			response.setStatus(Boolean.TRUE);
			response.setStatusCode(HttpStatus.OK.value());
			response.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}

}
