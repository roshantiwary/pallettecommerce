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

import com.pallette.domain.CategoryDocument;
import com.pallette.repository.CategoryRepository;

/**
 * <p>
 * Service class for Category repository operations. This class includes methods
 * that perform database operations related to Category.
 * </p>
 * 
 * @author amall3
 *
 */

@Service
public class CategoryService {

	private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	/**
	 * The Category repository.
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Method responsible for invoking the Category repository to fetch all the
	 * Categories.
	 * 
	 * @return List<CategoryDocument>
	 */
	public List<CategoryDocument> getAllCategories() {

		logger.debug("CategoryService.getAllCategories()");
		List<CategoryDocument> categories = new ArrayList<CategoryDocument>();

		categories = categoryRepository.findAll();
		if (null != categories && !categories.isEmpty()) {
			logger.info(String.format("CategoryService.getAllCategories - retrieved Category list is: %s" , categories.toString()));
		} else {
			logger.warn("CategoryService.getAllCategories could not find any Categories from Repository.");
		}
		return categories;
	}
	
	
	/**
	 * Method responsible for invoking the Category repository to fetch selected
	 * Category.
	 * 
	 * @return CategoryDocument
	 */
	public CategoryDocument getCategoryById(String categoryId) {

		logger.debug("CategoryService.getCategoryById() , parameter passed is: " + categoryId);
		CategoryDocument category = null;

		category = categoryRepository.findOne(categoryId);
		if (null != category) {
			logger.info(String.format("CategoryService.getCategoryById - retrieved Category is: " , category.getId()));
		} else {
			logger.warn("CategoryService.getCategoryById(): could not find any Category from Repository.");
		}
		return category;
	}
	
	
	/**
	 * Method responsible for invoking the Category repository to fetch Categories
	 * matching the Category title.
	 * 
	 * @return List<CategoryDocument>
	 */
	public List<CategoryDocument> getCategoryByTitle(String categoryTitle) {

		logger.debug("CategoryService.getCategoryByTitle() , parameter passed is: " + categoryTitle);
		List<CategoryDocument> categories = new ArrayList<CategoryDocument>();

		categories = categoryRepository.findByCategoryTitle(categoryTitle);
		if (null != categories && !categories.isEmpty()) {
			logger.info(String.format("CategoryService.getCategoryByTitle - retrieved Category list is: %s" , categories.toString()));
		} else {
			logger.warn("CategoryService.getCategoryByTitle(): could not find any Categories from Repository.");
		}
		return categories;
	}
	
}
