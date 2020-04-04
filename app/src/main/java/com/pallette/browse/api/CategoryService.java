/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;

import com.pallette.browse.response.CategoryResponseBean;

/**
 * @author amall3
 *
 */
public interface CategoryService {

	/**
	 * Method responsible for invoking the Category repository to fetch all the
	 * Categories.
	 * 
	 * @return List<CategoryDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getAllCategories() throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Category repository to fetch selected
	 * Category.
	 * 
	 * @return CategoryDocument
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getCategoryById(Long categoryId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Category repository to fetch
	 * Categories matching the Category title.
	 * 
	 * @return List<CategoryDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public CategoryResponseBean getCategoryByTitle(String categoryTitle) throws IllegalAccessException, InvocationTargetException;

}
