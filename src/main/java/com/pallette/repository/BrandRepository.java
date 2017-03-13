/**
 * 
 */
package com.pallette.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pallette.domain.BrandDocument;

/**
 * @author amall3
 *
 */
public interface BrandRepository extends MongoRepository<BrandDocument, String> {

	/**
	 * Method that queries the Mongo DB for a particular brandId.
	 */
	public BrandDocument findOne(String brandId);

	/**
	 * Method that queries the Mongo DB to fetch all the brand documents.
	 */
	public List<BrandDocument> findAll();

	/**
	 * Method that queries the Mongo DB to get all the brands that matches
	 * passed in city.
	 */
	public List<BrandDocument> findByCity(String city);
	
	/**
	 * Method that queries the Mongo DB to get all the brands that matches
	 * passed in state.
	 */
	public List<BrandDocument> findByState(String state);
	
	/**
	 * Method that queries the Mongo DB to get all the brands that matches
	 * passed in postal code.
	 */
	public List<BrandDocument> findByPostalCode(String postalCode);

}
