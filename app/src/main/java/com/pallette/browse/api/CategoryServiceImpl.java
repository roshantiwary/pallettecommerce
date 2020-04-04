/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pallette.browse.documents.CategoryDocument;
import com.pallette.browse.repository.CategoryRepository;
import com.pallette.browse.response.CategoryResponseBean;

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
public class CategoryServiceImpl implements CategoryService{

	private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

	/**
	 * The Category repository.
	 */
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private BrowseServices browseServices;

	/**
	 * Method responsible for invoking the Category repository to fetch all the
	 * Categories.
	 * 
	 * @return CategoryResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getAllCategories() throws IllegalAccessException, InvocationTargetException {
		
		logger.debug("CategoryServiceImpl.getAllCategories()");
		CategoryResponseBean response = null;
		
		List<CategoryDocument> categories = categoryRepository.findAll();
		if (null != categories && !categories.isEmpty()) {
			logger.info(String.format("CategoryServiceImpl.getAllCategories - retrieved Category list is: %s" , categories.toString()));
			response = browseServices.constructCategoryResponse(categories);
		} else {
			logger.warn("CategoryServiceImpl.getAllCategories could not find any Categories from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the Category repository to fetch selected
	 * Category.
	 * 
	 * @return CategoryResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getCategoryById(Long categoryId) throws IllegalAccessException, InvocationTargetException {

		logger.debug("CategoryServiceImpl.getCategoryById() , parameter passed is: " + categoryId);
		CategoryResponseBean response = null;

		 Optional<CategoryDocument> category = categoryRepository.findById(categoryId);
		if (null != category) {
			logger.info(String.format("CategoryServiceImpl.getCategoryById - retrieved Category is: " , category.get().getId()));
			response = browseServices.constructCategoryResponse(new ArrayList<CategoryDocument>(Arrays.asList(category.get())));
		} else {
			logger.warn("CategoryServiceImpl.getCategoryById(): could not find any Category from Repository.");
		}
		return response;
	}
	
	
	/**
	 * Method responsible for invoking the Category repository to fetch Categories
	 * matching the Category title.
	 * 
	 * @return CategoryResponseBean
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getCategoryByTitle(String categoryTitle) throws IllegalAccessException, InvocationTargetException {

		logger.debug("CategoryServiceImpl.getCategoryByTitle() , parameter passed is: " + categoryTitle);
		CategoryResponseBean response = null;

		List<CategoryDocument> categories = categoryRepository.findByCategoryTitle(categoryTitle);
		if (null != categories && !categories.isEmpty()) {
			logger.info(String.format("CategoryServiceImpl.getCategoryByTitle - retrieved Category list is: %s" , categories.toString()));
			response = browseServices.constructCategoryResponse(categories);
		} else {
			logger.warn("CategoryServiceImpl.getCategoryByTitle(): could not find any Categories from Repository.");
		}
		return response;
	}
	
}
