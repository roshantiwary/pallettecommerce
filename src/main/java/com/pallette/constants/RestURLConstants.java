/**
 * 
 */
package com.pallette.constants;

/**
 * @author amall3
 *
 */
public class RestURLConstants {

	public static final String GET_SAVED_ADDRESSES_URL = "/shipping/address/savedAddress/{orderId}";

	public static final String GET_SHIPMENT_ADDRESS_URL = "/shipping/address/shipmentAddress/{orderId}";

	public static final String REMOVE_ADDRESS_URL = "/shipping/address/remove/{orderId}";

	public static final String EDIT_ADDRESS_URL = "/shipping/address/edit";

	public static final String ADD_ADDRESS_URL = "/shipping/address/add";
	
	public static final String CART_REMOVE = "/cart/remove";

	public static final String CART_UPDATE = "/cart/update";

	public static final String CART_ADD = "/cart/add";
	
	public static final String CART_DETAILS_URL = "/cart/{orderId}/details";
	
	public static final String MOVE_TO_CHECKOUT_URL = "/cart/moveToCheckout/{orderId}";

}
