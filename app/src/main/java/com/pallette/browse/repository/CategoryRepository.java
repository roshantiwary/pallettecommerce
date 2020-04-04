/**
 * 
 */
package com.pallette.browse.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.pallette.browse.documents.CategoryDocument;

/**
 * @author amall3
 *
 */
public interface CategoryRepository extends PagingAndSortingRepository<CategoryDocument, Long> {

	/**
	 * Method that queries the Mongo DB for a particular categoryId.
	 */
//	public CategoryDocument findOne(Long id);

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
