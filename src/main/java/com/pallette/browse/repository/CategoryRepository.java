/**
 * 
 */
package com.pallette.browse.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pallette.browse.documents.CategoryDocument;

/**
 * @author amall3
 *
 */
public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {

	/**
	 * Method that queries the Mongo DB for a particular categoryId.
	 */
	public CategoryDocument findOne(String categoryId);

	/**
	 * Method that queries the Mongo DB to fetch all the category documents.
	 */
	public List<CategoryDocument> findAll();

	/**
	 * Method that queries the Mongo DB to get all the category that matches
	 * passed in category Title.
	 */
	public List<CategoryDocument> findByCategoryTitle(String categoryTitle);

}
