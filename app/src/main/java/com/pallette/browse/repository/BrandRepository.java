/**
 * 
 */
package com.pallette.browse.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.pallette.browse.documents.BrandDocument;
import com.pallette.domain.Address;

/**
 * @author amall3
 *
 */
public interface BrandRepository extends PagingAndSortingRepository<BrandDocument, String> {

	/**
	 * Method that queries the Mongo DB for a particular brandId.
	 */	
//	public BrandDocument findOne(Long id);

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
