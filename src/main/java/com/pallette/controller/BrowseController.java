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
	public ResponseEntity<GenericResponse> getAllProducts() {
		
		logger.debug("BrowseController.getAllProduct()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<ProductDocument> products = productService.getAllProduct();
			
			if (null != products && !products.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(products);
				genericResponse.setItemCount(products.size());
				genericResponse.setMessage("Product Items were Returned Successfully.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
				
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Product Items were Returned.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getProduct(@PathVariable("productId") String productId) {
		
		logger.debug("BrowseController.getProduct()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(productId)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Product Id was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
			
			logger.debug("Product Id passed is : " + productId);
			ProductDocument product = productService.getProductById(productId);
			
			if (null != product) {
				List<ProductDocument> products = new ArrayList<ProductDocument>();
				products.add(product);
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(products);
				genericResponse.setItemCount(products.size());
				genericResponse.setMessage("Product Item was Returned Successfully.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
				
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Product Item was Returned.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	
	}

	@RequestMapping(value = "/products/title/{productTitle}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getProductsByTitle(@PathVariable("productTitle") String productTitle) {
		
		logger.debug("BrowseController.getProductByTitle()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(productTitle)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Product Title was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
			
			logger.debug("Product Title passed is : ", productTitle);
			List<ProductDocument> products = productService.getProductByTitle(productTitle);
			
			if (null != products && !products.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(products);
				genericResponse.setItemCount(products.size());
				genericResponse.setMessage("Product Items were Returned Successfully.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
				
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Product Items were Returned.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping("/categories")
	public ResponseEntity<GenericResponse> getAllCategories() {
		
		logger.debug("BrowseController.getAllCategories()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<CategoryDocument> categories = categoryService.getAllCategories();

			if (null != categories && !categories.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(categories);
				genericResponse.setItemCount(categories.size());
				genericResponse.setMessage("Category Items were Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Category Items were Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
    	
	@RequestMapping(value = "/categories/{categoryId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getCategory(@PathVariable("categoryId") String categoryId) {

		logger.debug("BrowseController.getCategory()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(categoryId)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Category Id was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

			logger.debug("Category Id passed is : " + categoryId);
			CategoryDocument category = categoryService.getCategoryById(categoryId);

			if (null != category) {
				List<CategoryDocument> categories = new ArrayList<CategoryDocument>();
				categories.add(category);
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(categories);
				genericResponse.setItemCount(categories.size());
				genericResponse.setMessage("Category Item was Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);

			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Category Item was Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/categories/title/{categoryTitle}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getCategoriesByTitle(@PathVariable("categoryTitle") String categoryTitle) {
		
		logger.debug("BrowseController.getCategoriesByTitle()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(categoryTitle)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Category Title was Passed.");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}
			
			logger.debug("Category Title passed is : ", categoryTitle);
			List<CategoryDocument> categories = categoryService.getCategoryByTitle(categoryTitle);
			
			if (null != categories && !categories.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(categories);
				genericResponse.setItemCount(categories.size());
				genericResponse.setMessage("Category Items were Returned Successfully.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
				
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Category Items were Returned.");
				
				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping("/brands")
	public ResponseEntity<GenericResponse> getAllBrands() {
		
		logger.debug("BrowseController.getAllBrands()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			List<BrandDocument> brands = brandService.getAllBrands();

			if (null != brands && !brands.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(brands);
				genericResponse.setItemCount(brands.size());
				genericResponse.setMessage("Brand Items were Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);
			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Brand Items were Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
    	
	@RequestMapping(value = "/brands/{brandId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrand(@PathVariable("brandId") String brandId) {

		logger.debug("BrowseController.getBrand()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(brandId)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Brand Id was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

			logger.debug("Brand Id passed is : " + brandId);
			BrandDocument brand = brandService.getBrandById(brandId);

			if (null != brand) {
				List<BrandDocument> brands = new ArrayList<BrandDocument>();
				brands.add(brand);
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(brands);
				genericResponse.setItemCount(brands.size());
				genericResponse.setMessage("Category Item was Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);

			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Category Item was Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/brands/city/{city}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByCity(@PathVariable("city") String city) {

		logger.debug("BrowseController.getBrandByCity()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(city)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Brand City was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

			logger.debug("Brand City passed is : " + city);
			List<BrandDocument> brands = brandService.getBrandByCity(city);

			if (null != brands && !brands.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(brands);
				genericResponse.setItemCount(brands.size());
				genericResponse.setMessage("Brand Items were Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);

			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Brand Items were Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/brands/state/{state}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByState(@PathVariable("state") String state) {

		logger.debug("BrowseController.getBrandByState()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(state)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Brand state was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

			logger.debug("Brand state passed is : " + state);
			List<BrandDocument> brands = brandService.getBrandByState(state);

			if (null != brands && !brands.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(brands);
				genericResponse.setItemCount(brands.size());
				genericResponse.setMessage("Brand Items were Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);

			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Brand Items were Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/brands/postalCode/{postalCode}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<GenericResponse> getBrandByPostalCode(@PathVariable("postalCode") String postalCode) {

		logger.debug("BrowseController.getBrandByPostalCode()");
		GenericResponse genericResponse = new GenericResponse();
		try {
			if (StringUtils.isEmpty(postalCode)) {
				genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
				genericResponse.setMessage("No Brand postalCode was Passed");
				return new ResponseEntity(genericResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
			}

			logger.debug("Brand postalCode passed is : " + postalCode);
			List<BrandDocument> brands = brandService.getBrandByPostalCode(postalCode);

			if (null != brands && !brands.isEmpty()) {
				genericResponse.setStatusCode(HttpStatus.FOUND);
				genericResponse.setItems(brands);
				genericResponse.setItemCount(brands.size());
				genericResponse.setMessage("Brand Items were Returned Successfully.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.FOUND);

			} else {
				genericResponse.setStatusCode(HttpStatus.NOT_FOUND);
				genericResponse.setItemCount(0);
				genericResponse.setMessage("No Brand Items were Returned.");

				return new ResponseEntity<>(genericResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (Exception exp) {
			genericResponse.setStatusCode(HttpStatus.BAD_REQUEST);
			genericResponse.setMessage(exp.getMessage());
			return new ResponseEntity(genericResponse , new HttpHeaders(), HttpStatus.BAD_REQUEST);
		}
	}
   
}
