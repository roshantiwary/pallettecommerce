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
	
	public static final String SET_SHIPMENT_ADDRESS_URL = "/shipping/address/set/{addressId}/to/{orderId}";
	
	public static final String GET_DELIVERY_METHODS_URL = "/shipping/deliveryMethods/{orderId}";
	
	public static final String SET_DELIVERY_METHOD_URL = "/shipping/set/deliveryMethod/{deliveryMethod}/to/{orderId}";
	//Shipping Services URL Starts.
	
	//Order Confirmation.
	public static final String GET_ORDER_CONFIRMATION_DETAILS_URL = "/orderConfirmation/order/{orderId}";
	
	public static final String PROFILE_ADD_ADDRESS_URL = "/account/address/add";
	
	public static final String PROFILE_EDIT_ADDRESS_URL = "/account/address/edit";
	
	public static final String PROFILE_GET_ADDRESS_URL = "/account/address/{id}";
	
	public static final String POFILE_REMOVE_ADDRESS_URL = "/account/address/{id}/remove";
	
	public static final String EDIT_PROFILE_URL = "/account/edit";
	
	public static final String CHANGE_PASSWORD_URL = "/account/changePassword";
	
	public static final String ORDER_DETAIL_URL = "/account/{orderId}/orderDetail";
	
	public static final String ORDER_HISTORY_URL = "/account/orders";
	
	public static final String PROFILE_ADDRESSES_URL = "/account/addresses";
	
	public static final String CREATE_PROFILE_URL = "/account/create";
	
	public static final String LOGIN_PROFILE_URL = "/login";
	
	public static final String LOGOUT_URL = "/logout";

	public static final String FORGOT_PASSWORD_URL = "/user/forgotPassword/";
	
	public static final String DISPLAY_FORGOT_PASSWORD = "/user/display/forgotPassword/";
	
	public static final String SET_NEW_PASSWORD = "/user/forgotPassword/setNewPassword/";

}
