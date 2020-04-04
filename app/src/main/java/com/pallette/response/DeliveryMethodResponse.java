/**
 * 
 */
package com.pallette.response;

/**
 * @author amall3
 *
 */
public class DeliveryMethodResponse {

	private String id;

	private String deliveryMethodName;

	private String deliveryMethodDescription;

	private String deliveryMethodType;

	private double convenienceFee;

	private boolean selected;

	private int minDaysToShip;

	private int maxDaysToShip;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

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
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
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
