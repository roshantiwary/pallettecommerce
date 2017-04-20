/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;

import com.pallette.browse.response.BrandResponseBean;

/**
 * @author amall3
 *
 */
public interface BrandService {

	/**
	 * Method responsible for invoking the Brand repository to fetch all the
	 * Brands.
	 * 
	 * @return List<BrandDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getAllBrands() throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Brand repository to fetch selected
	 * Brand.
	 * 
	 * @return BrandDocument
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandById(String brandId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand City.
	 * 
	 * @return List<BrandDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByCity(String brandCity) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand State.
	 * 
	 * @return List<BrandDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByState(String brandState) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the Brand repository to fetch brands
	 * matching the Brand Postal Code.
	 * 
	 * @return List<BrandDocument>
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public BrandResponseBean getBrandByPostalCode(String brandPostalCode) throws IllegalAccessException, InvocationTargetException;

}
