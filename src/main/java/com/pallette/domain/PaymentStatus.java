/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author amall3
 *
 */

@Document(collection = "paymentStatus")
public class PaymentStatus implements Serializable {

	@Id
	private ObjectId id;

	@Field(value = "transaction_id")
	private String transactionId;

	private String amount;

	@Field(value = "transaction_success")
	private boolean transactionSuccess;

	@Field(value = "error_message")
	private String errorMessage;

	@Field(value = "transaction_timestamp")
	private Date transactionTimestamp;

	public PaymentStatus() {
		super();
	}

	public PaymentStatus(ObjectId id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("transactionId:").append(getTransactionId()).append("; ");
		buf.append("amount:").append(getAmount()).append("; ");
		buf.append("transactionSuccess:").append(isTransactionSuccess()).append("; ");
		buf.append("errorMessage:").append(getErrorMessage()).append("; ");
		buf.append("transactionTimestamp:").append(getTransactionTimestamp()).append("; ");
		return buf.toString();
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
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId
	 *            the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * @return the transactionSuccess
	 */
	public boolean isTransactionSuccess() {
		return transactionSuccess;
	}

	/**
	 * @param transactionSuccess
	 *            the transactionSuccess to set
	 */
	public void setTransactionSuccess(boolean transactionSuccess) {
		this.transactionSuccess = transactionSuccess;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the transactionTimestamp
	 */
	public Date getTransactionTimestamp() {
		return transactionTimestamp;
	}

	/**
	 * @param transactionTimestamp
	 *            the transactionTimestamp to set
	 */
	public void setTransactionTimestamp(Date transactionTimestamp) {
		this.transactionTimestamp = transactionTimestamp;
	}

}
