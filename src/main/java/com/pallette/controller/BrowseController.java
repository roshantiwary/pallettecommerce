package com.pallette.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping("/products")
	public ResponseEntity<GenericResponse> getAllProducts() throws NoRecordsFoundException {
		
		logger.debug("BrowseController.getAllProduct()");
		GenericResponse genericResponse = new GenericResponse();
		List<ProductDocument> products = productService.getAllProduct();
		
		if (null != products && !products.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(products);
			genericResponse.setItemCount(products.size());
			genericResponse.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getProduct(@PathVariable("productId") String productId) throws NoRecordsFoundException , IllegalArgumentException{
		
		logger.debug("BrowseController.getProduct()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(productId)) {
			throw new IllegalArgumentException("No Product Id was Passed");
		}
		
		logger.debug("Product Id passed is : " + productId);
		ProductDocument product = productService.getProductById(productId);
		
		if (null != product) {
			List<ProductDocument> products = new ArrayList<ProductDocument>();
			products.add(product);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(products);
			genericResponse.setItemCount(products.size());
			genericResponse.setMessage("Product Item was Returned Successfully.");
			
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}

	@RequestMapping(value = "/products/title/{productTitle}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getProductsByTitle(@PathVariable("productTitle") String productTitle) throws NoRecordsFoundException , IllegalArgumentException {
		
		logger.debug("BrowseController.getProductByTitle()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(productTitle)) { 
			throw new IllegalArgumentException("No Product Title was Passed");
		}
		
		logger.debug("Product Title passed is : ", productTitle);
		List<ProductDocument> products = productService.getProductByTitle(productTitle);
		
		if (null != products && !products.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(products);
			genericResponse.setItemCount(products.size());
			genericResponse.setMessage("Product Items were Returned Successfully.");
			
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Products Found");
	}
	
	@RequestMapping("/categories")
	public ResponseEntity<GenericResponse> getAllCategories() throws NoRecordsFoundException {
		
		logger.debug("BrowseController.getAllCategories()");
		GenericResponse genericResponse = new GenericResponse();
		List<CategoryDocument> categories = categoryService.getAllCategories();

		if (null != categories && !categories.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categories);
			genericResponse.setItemCount(categories.size());
			genericResponse.setMessage("Category Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
    	
	@RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getCategory(@PathVariable("categoryId") String categoryId) throws NoRecordsFoundException , IllegalArgumentException{

		logger.debug("BrowseController.getCategory()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(categoryId)) {
			throw new IllegalArgumentException("No Category Id was Passed");
		}

		logger.debug("Category Id passed is : " + categoryId);
		CategoryDocument category = categoryService.getCategoryById(categoryId);

		if (null != category) {
			List<CategoryDocument> categories = new ArrayList<CategoryDocument>();
			categories.add(category);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categories);
			genericResponse.setItemCount(categories.size());
			genericResponse.setMessage("Category Item was Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else 
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	@RequestMapping(value = "/categories/title/{categoryTitle}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getCategoriesByTitle(@PathVariable("categoryTitle") String categoryTitle) throws IllegalArgumentException , NoRecordsFoundException{
		
		logger.debug("BrowseController.getCategoriesByTitle()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(categoryTitle)) {
			throw new IllegalArgumentException("No Category Title was Passed.");
		}
		
		logger.debug("Category Title passed is : ", categoryTitle);
		List<CategoryDocument> categories = categoryService.getCategoryByTitle(categoryTitle);
		
		if (null != categories && !categories.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(categories);
			genericResponse.setItemCount(categories.size());
			genericResponse.setMessage("Category Items were Returned Successfully.");
			
			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Categories Found");
	}
	
	
	@RequestMapping("/brands")
	public ResponseEntity<GenericResponse> getAllBrands() throws NoRecordsFoundException {

		logger.debug("BrowseController.getAllBrands()");
		GenericResponse genericResponse = new GenericResponse();
		List<BrandDocument> brands = brandService.getAllBrands();

		if (null != brands && !brands.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
    	
	@RequestMapping(value = "/brands/{brandId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrand(@PathVariable("brandId") String brandId) throws NoRecordsFoundException , IllegalArgumentException {

		logger.debug("BrowseController.getBrand()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(brandId)) { 
			throw new IllegalArgumentException("No Brand Id was Passed.");
		}

		logger.debug("Brand Id passed is : " + brandId);
		BrandDocument brand = brandService.getBrandById(brandId);

		if (null != brand) {
			List<BrandDocument> brands = new ArrayList<BrandDocument>();
			brands.add(brand);
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Category Item was Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	
	@RequestMapping(value = "/brands/city/{city}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByCity(@PathVariable("city") String city) throws NoRecordsFoundException , IllegalArgumentException {

		logger.debug("BrowseController.getBrandByCity()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(city)) { 
			throw new IllegalArgumentException("No Brand City was Passed.");
		}

		logger.debug("Brand City passed is : " + city);
		List<BrandDocument> brands = brandService.getBrandByCity(city);

		if (null != brands && !brands.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = "/brands/state/{state}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByState(@PathVariable("state") String state) throws NoRecordsFoundException , IllegalArgumentException {

		logger.debug("BrowseController.getBrandByState()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(state)) { 
			throw new IllegalArgumentException("No Brand State was Passed.");
		}

		logger.debug("Brand state passed is : " + state);
		List<BrandDocument> brands = brandService.getBrandByState(state);

		if (null != brands && !brands.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
	
	@RequestMapping(value = "/brands/postalCode/{postalCode}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByPostalCode(@PathVariable("postalCode") String postalCode) throws NoRecordsFoundException , IllegalArgumentException {

		logger.debug("BrowseController.getBrandByPostalCode()");
		GenericResponse genericResponse = new GenericResponse();
		if (StringUtils.isEmpty(postalCode)) { 
			throw new IllegalArgumentException("No Brand Postal Code was Passed.");
		}

		logger.debug("Brand postalCode passed is : " + postalCode);
		List<BrandDocument> brands = brandService.getBrandByPostalCode(postalCode);

		if (null != brands && !brands.isEmpty()) {
			genericResponse.setStatusCode(HttpStatus.OK.value());
			genericResponse.setItems(brands);
			genericResponse.setItemCount(brands.size());
			genericResponse.setMessage("Brand Items were Returned Successfully.");

			return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.OK);
		} else
			throw new NoRecordsFoundException("No Brands Found.");
	}
   
}
