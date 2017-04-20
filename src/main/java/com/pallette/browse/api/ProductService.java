/**
 * 
 */
package com.pallette.browse.api;

import java.lang.reflect.InvocationTargetException;

import com.pallette.browse.response.ProductResponseBean;

/**
 * @author amall3
 *
 */
public interface ProductService {

	/**
	 * Method responsible for invoking the product repository to fetch all the
	 * products.
	 * 
	 * @return List<ProductDocument>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ProductResponseBean getAllProduct() throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the product repository to fetch selected
	 * product.
	 * 
	 * @return ProductDocument
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ProductResponseBean getProductById(String productId) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the product title.
	 * 
	 * @return List<ProductDocument>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ProductResponseBean getProductByTitle(String productTitle) throws IllegalAccessException, InvocationTargetException;

	/**
	 * Method responsible for invoking the product repository to fetch products
	 * matching the Brand Id.
	 * 
	 * @return List<ProductDocument>
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	public ProductResponseBean getProductByBrand(String brandId) throws IllegalAccessException, InvocationTargetException;

}
