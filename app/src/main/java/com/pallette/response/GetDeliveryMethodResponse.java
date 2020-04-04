/**
 * 
 */
package com.pallette.response;

import java.util.List;

/**
 * @author amall3
 *
 */
public class GetDeliveryMethodResponse extends Response {

	private List<DeliveryMethodResponse> deliveryMethods;

	/**
	 * @return the deliveryMethods
	 */
	public List<DeliveryMethodResponse> getDeliveryMethods() {
		return deliveryMethods;
	}

	/**
	 * @param deliveryMethods
	 *            the deliveryMethods to set
	 */
	public void setDeliveryMethods(List<DeliveryMethodResponse> deliveryMethods) {
		this.deliveryMethods = deliveryMethods;
	}

}
