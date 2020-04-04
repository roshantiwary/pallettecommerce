/**
 * 
 */
package com.pallette.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * <p>
 * Sequence document created for maintaining sequential order id.
 * </p>
 * 
 * @author amall3
 *
 */
@Document(collection = "sequence")
public class SequenceId {

	@Id
	private String id;

	@Field(value = "order_seq")
	private long orderSeq;

	@Field(value = "profile_seq")
	private long profileSeq;
	
	@Field(value = "address_seq")
	private long addressSeq;

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
	 * @return the orderSeq
	 */
	public long getOrderSeq() {
		return orderSeq;
	}

	/**
	 * @param orderSeq
	 *            the orderSeq to set
	 */
	public void setOrderSeq(long orderSeq) {
		this.orderSeq = orderSeq;
	}

	/**
	 * @return the profileSeq
	 */
	public long getProfileSeq() {
		return profileSeq;
	}

	/**
	 * @param profileSeq
	 *            the profileSeq to set
	 */
	public void setProfileSeq(long profileSeq) {
		this.profileSeq = profileSeq;
	}

	public long getAddressSeq() {
		return addressSeq;
	}

	public void setAddressSeq(long addressSeq) {
		this.addressSeq = addressSeq;
	}

}