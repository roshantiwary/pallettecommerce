/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pallette.browse.documents.BrandDocument;
import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.repository.BrandRepository;
import com.pallette.browse.response.BrandResponseBean;

/**
 * <p>
 * Service class for Brand repository operations. This class includes methods
 * that perform database operations related to Brands.
 * </p>
 * 
 * @author amall3
 *
 */

@Service
public class BrandServiceImpl implements BrandService{

	private static final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

	/**
	 * The Brand repository.
	 */
	@Autowired
	private BrandRepository brandRepository;
	
	@Autowired
	private BrowseServices browseServices;

	/**
	 * Method responsible for invoking the Brand repository to fetch all the
	 * Brands.
	 * 
	 * @return BrandResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getAllBrands() throws IllegalAccessException, InvocationTargetException {

		logger.debug("BrandServiceImpl.getAllBrands()");
		BrandResponseBean response = null;

		List<BrandDocument> brands = brandRepository.findAll();
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandServiceImpl.getAllBrands - retrieved Category list is: %s" , brands.toString()));
			response = browseServices.constructBrandResponse(brands);
		} else {
			logger.warn("BrandServiceImpl.getAllBrands could not find any Brands from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch selected
	 * Brand.
	 * 
	 * @return BrandResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandById(String brandId) throws IllegalAccessException, InvocationTargetException {

		logger.debug("BrandServiceImpl.getBrandById() , parameter passed is: " + brandId);
		BrandResponseBean response = null;

		BrandDocument brand = brandRepository.findOne(brandId);
		if (null != brand) {
			logger.info(String.format("BrandServiceImpl.getBrandById - retrieved Brand is: " , brand.getId()));
			response = browseServices.constructBrandResponse(new ArrayList<BrandDocument>(Arrays.asList(brand)));
		} else {
			logger.warn("BrandServiceImpl.getBrandById(): could not find any Brand from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand City.
	 * 
	 * @return BrandResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByCity(String brandCity) throws IllegalAccessException, InvocationTargetException {

		logger.debug("BrandServiceImpl.getBrandByCity() , parameter passed is: " + brandCity);
		BrandResponseBean response = null;

		List<BrandDocument> brands = brandRepository.findByCity(brandCity);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandServiceImpl.getBrandByCity - retrieved Brand list is: %s", brands.toString()));
			response = browseServices.constructBrandResponse(brands);
		} else {
			logger.warn("BrandServiceImpl.getBrandByCity(): could not find any Brands from Repository.");
		}
		return response;
	}
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand State.
	 * 
	 * @return BrandResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByState(String brandState) throws IllegalAccessException, InvocationTargetException {

		logger.debug("BrandServiceImpl.getBrandByState() , parameter passed is: " + brandState);
		BrandResponseBean response = null;

		List<BrandDocument> brands = brandRepository.findByState(brandState);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandServiceImpl.getBrandByState - retrieved Brand list is: %s", brands.toString()));
			response = browseServices.constructBrandResponse(brands);
		} else {
			logger.warn("BrandServiceImpl.getBrandByState(): could not find any Brands from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand Postal Code.
	 * 
	 * @return List<BrandDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByPostalCode(String brandPostalCode) throws IllegalAccessException, InvocationTargetException {

		logger.debug("BrandServiceImpl.getBrandByPostalCode() , parameter passed is: " + brandPostalCode);
		BrandResponseBean response = null;

		List<BrandDocument> brands = brandRepository.findByPostalCode(brandPostalCode);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandServiceImpl.getBrandByPostalCode - retrieved Brand list is: %s", brands.toString()));
			response = browseServices.constructBrandResponse(brands);
		} else {
			logger.warn("BrandServiceImpl.getBrandByPostalCode(): could not find any Brands from Repository.");
		}
		return response;
	}
	
}
