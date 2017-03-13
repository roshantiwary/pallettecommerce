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

import com.pallette.domain.BrandDocument;
import com.pallette.repository.BrandRepository;

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
public class BrandService {

	private static final Logger logger = LoggerFactory.getLogger(BrandService.class);

	/**
	 * The Brand repository.
	 */
	@Autowired
	private BrandRepository brandRepository;

	/**
	 * Method responsible for invoking the Brand repository to fetch all the
	 * Brands.
	 * 
	 * @return List<BrandDocument>
	 */
	public List<BrandDocument> getAllBrands() {

		logger.debug("BrandService.getAllBrands()");
		List<BrandDocument> brands = new ArrayList<BrandDocument>();

		brands = brandRepository.findAll();
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandService.getAllBrands - retrieved Category list is: %s" , brands.toString()));
		} else {
			logger.warn("BrandService.getAllBrands could not find any Brands from Repository.");
		}
		return brands;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch selected
	 * Brand.
	 * 
	 * @return BrandDocument
	 */
	public BrandDocument getBrandById(String brandId) {

		logger.debug("BrandService.getBrandById() , parameter passed is: " + brandId);
		BrandDocument brand = null;

		brand = brandRepository.findOne(brandId);
		if (null != brand) {
			logger.info(String.format("BrandService.getBrandById - retrieved Brand is: " , brand.getId()));
		} else {
			logger.warn("BrandService.getBrandById(): could not find any Brand from Repository.");
		}
		return brand;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand City.
	 * 
	 * @return List<BrandDocument>
	 */
	public List<BrandDocument> getBrandByCity(String brandCity) {

		logger.debug("BrandService.getBrandByCity() , parameter passed is: " + brandCity);
		List<BrandDocument> brands = new ArrayList<BrandDocument>();

		brands = brandRepository.findByCity(brandCity);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandService.getBrandByCity - retrieved Brand list is: %s", brands.toString()));
		} else {
			logger.warn("BrandService.getBrandByCity(): could not find any Brands from Repository.");
		}
		return brands;
	}
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand State.
	 * 
	 * @return List<BrandDocument>
	 */
	public List<BrandDocument> getBrandByState(String brandState) {

		logger.debug("BrandService.getBrandByState() , parameter passed is: " + brandState);
		List<BrandDocument> brands = new ArrayList<BrandDocument>();

		brands = brandRepository.findByState(brandState);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandService.getBrandByState - retrieved Brand list is: %s", brands.toString()));
		} else {
			logger.warn("BrandService.getBrandByState(): could not find any Brands from Repository.");
		}
		return brands;
	}
	
	
	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand Postal Code.
	 * 
	 * @return List<BrandDocument>
	 */
	public List<BrandDocument> getBrandByPostalCode(String brandPostalCode) {

		logger.debug("BrandService.getBrandByPostalCode() , parameter passed is: " + brandPostalCode);
		List<BrandDocument> brands = new ArrayList<BrandDocument>();

		brands = brandRepository.findByPostalCode(brandPostalCode);
		if (null != brands && !brands.isEmpty()) {
			logger.info(String.format("BrandService.getBrandByPostalCode - retrieved Brand list is: %s", brands.toString()));
		} else {
			logger.warn("BrandService.getBrandByPostalCode(): could not find any Brands from Repository.");
		}
		return brands;
	}
	
}
