/**
 * 
 */
package com.pallette.constants;

/**
 * @author amall3
 *
 */
public class RestURLConstants {
	
	//Browse Services :: Product Services URL Starts.
	public static final String ALL_PRODUCTS_URL = "/products";
	
	public static final String SELECTED_PRODUCT_URL = "/products/{productId}";
	
	public static final String GET_PRODUCTS_BY_TITLE_URL = "/products/title/{productTitle}";
	
	public static final String GET_PRODUCTS_BY_BRAND_URL = "/products/brand/{brandId}";
	//Browse Services :: Product Services URL Ends.
	
	
	//Browse Services :: Category Services URL Starts.
	public static final String ALL_CATEGORIES_URL = "/categories";
	
	public static final String SELECTED_CATEGORY_URL = "/categories/{categoryId}";
	
	public static final String GET_CATEGORIES_BY_TITLE_URL = "/categories/title/{categoryTitle}";
	//Browse Services :: Category Services URL Ends.
	

	//Browse Services :: Brand Services URL Starts.
	public static final String ALL_BRANDS_URL = "/brands";
	
	public static final String SELECTED_BRAND_URL = "/brands/{brandId}";
	
	public static final String GET_BRANDS_BY_POSTAL_CODE_URL = "/brands/postalCode/{postalCode}";

	public static final String GET_BRANDS_BY_STATE_URL = "/brands/state/{state}";

	public static final String GET_BRANDS_BY_CITY_URL = "/brands/city/{city}";
	//Browse Services :: Brand Services URL Ends.
	
	
	//Cart Services URL Starts.
	public static final String CART_ADD = "/cart/add";
	
	public static final String CART_REMOVE = "/cart/remove";

	public static final String CART_UPDATE = "/cart/update";
	
	public static final String CART_DETAILS_URL = "/cart/{orderId}/details";
	
	public static final String MOVE_TO_CHECKOUT_URL = "/cart/moveToCheckout/{orderId}";
	//Cart Services URL Ends.
	
	
	//Shipping Services URL Starts.
	public static final String ADD_ADDRESS_URL = "/shipping/address/add";
	
	public static final String EDIT_ADDRESS_URL = "/shipping/address/edit";
	
	public static final String REMOVE_ADDRESS_URL = "/shipping/address/remove/{orderId}";
	
	public static final String GET_SAVED_ADDRESSES_URL = "/shipping/address/savedAddress/{orderId}";

	public static final String GET_SHIPMENT_ADDRESS_URL = "/shipping/address/shipmentAddress/{orderId}";

}
