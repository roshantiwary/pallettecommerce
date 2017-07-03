/**
 * 
 */
package com.pallette.browse.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.pallette.browse.documents.ProductDocument;

/**
 * @author amall3
 *
 */
public interface ProductRepository extends 	CrudRepository<ProductDocument, String> {

	/**
	 * Method that queries the Mongo DB for a particular productId.
	 */
	public ProductDocument findOne(String productId);

	/**
	 * Method that queries the Mongo DB to fetch all the product documents.
	 */
	public List<ProductDocument> findAll();

	/**
	 * Method that queries the Mongo DB to get all the products that matches
	 * passed in product Title.
	 */
	public List<ProductDocument> findByProductTitle(String productTitle);
	
	
	/**
	 * Method that queries the Mongo DB to get all the products that matches
	 * passed in brand id.
	 */
	public List<ProductDocument> findByProductBrandId(String id);
	
}
