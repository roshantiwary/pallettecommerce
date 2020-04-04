/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.persistence.BaseEntity;

/**
 * @author amall3
 *
 */

@Document(collection = "deliveryMethod")
public class DeliveryMethod extends BaseEntity {

	public DeliveryMethod() {
		super();
	}

	@Field(value = "delivery_method_name")
	private String deliveryMethodName;

	@Field(value = "delivery_method_description")
	private String deliveryMethodDescription;

	@Field(value = "delivery_method_type")
	private String deliveryMethodType;

	private boolean active;

	private double convenienceFee;

	private int minDaysToShip;

	private int maxDaysToShip;

	/**
	 * @return the deliveryMethodName
	 */
	public String getDeliveryMethodName() {
		return deliveryMethodName;
	}

	/**
	 * @param deliveryMethodName
	 *            the deliveryMethodName to set
	 */
	public void setDeliveryMethodName(String deliveryMethodName) {
		this.deliveryMethodName = deliveryMethodName;
	}

	/**
	 * @return the deliveryMethodDescription
	 */
	public String getDeliveryMethodDescription() {
		return deliveryMethodDescription;
	}

	/**
	 * @param deliveryMethodDescription
	 *            the deliveryMethodDescription to set
	 */
	public void setDeliveryMethodDescription(String deliveryMethodDescription) {
		this.deliveryMethodDescription = deliveryMethodDescription;
	}

	/**
	 * @return the deliveryMethodType
	 */
	public String getDeliveryMethodType() {
		return deliveryMethodType;
	}

	/**
	 * @param deliveryMethodType
	 *            the deliveryMethodType to set
	 */
	public void setDeliveryMethodType(String deliveryMethodType) {
		this.deliveryMethodType = deliveryMethodType;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * @return the convenienceFee
	 */
	public double getConvenienceFee() {
		return convenienceFee;
	}

	/**
	 * @param convenienceFee
	 *            the convenienceFee to set
	 */
	public void setConvenienceFee(double convenienceFee) {
		this.convenienceFee = convenienceFee;
	}

	/**
	 * @return the minDaysToShip
	 */
	public int getMinDaysToShip() {
		return minDaysToShip;
	}

	/**
	 * @param minDaysToShip
	 *            the minDaysToShip to set
	 */
	public void setMinDaysToShip(int minDaysToShip) {
		this.minDaysToShip = minDaysToShip;
	}

	/**
	 * @return the maxDaysToShip
	 */
	public int getMaxDaysToShip() {
		return maxDaysToShip;
	}

	/**
	 * @param maxDaysToShip
	 *            the maxDaysToShip to set
	 */
	public void setMaxDaysToShip(int maxDaysToShip) {
		this.maxDaysToShip = maxDaysToShip;
	}
}
