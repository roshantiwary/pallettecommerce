/**
 * 
 */
package com.pallette.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */

@Document(collection = "shippingPriceInfo")
public class ShippingPriceInfo extends AmountInfo {

	@Id
	private ObjectId id;

	@Field(value = "raw_shipping")
	private double rawShipping;

	public String toString() {
		StringBuffer buf = new StringBuffer(super.toString());
		buf.append("; ").append("RawShipping:").append(getRawShipping());
		return buf.toString();
	}

	public ShippingPriceInfo() {
		super();
	}

	public ShippingPriceInfo(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public ObjectId getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(ObjectId id) {
		this.id = id;
	}

	/**
	 * @return the rawShipping
	 */
	public double getRawShipping() {
		return rawShipping;
	}

	/**
	 * @param rawShipping
	 *            the rawShipping to set
	 */
	public void setRawShipping(double rawShipping) {
		this.rawShipping = rawShipping;
	}

}
