/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */
public class AmountInfo {

	@Field(value = "currency_code")
	String currencyCode;

	private double amount;

	private boolean discounted;

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("CurrencyCode:").append(getCurrencyCode()).append("; ");
		buf.append("Amount:").append(getAmount()).append("; ");
		buf.append("Discounted:").append(isDiscounted()).append("; ");
		return buf.toString();
	}

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return the discounted
	 */
	public boolean isDiscounted() {
		return discounted;
	}

	/**
	 * @param discounted
	 *            the discounted to set
	 */
	public void setDiscounted(boolean discounted) {
		this.discounted = discounted;
	}

}
