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

	@Field(value = "transaction_success")
	private boolean transactionSuccess;

	@Field(value = "error_message")
	private String errorMessage;

	@Field(value = "transaction_timestamp")
	private Date transactionTimestamp;

	@Field(value = "unmapped_status")
	private String unmappedstatus;

	@Field(value = "error_code")
	private String errorCode;

	public PaymentStatus() {
		super();
	}

	public PaymentStatus(ObjectId id) {
		this.id = id;
	}

	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("transactionId:").append(getTransactionId()).append("; ");
		buf.append("transactionSuccess:").append(isTransactionSuccess())
				.append("; ");
		buf.append("errorMessage:").append(getErrorMessage()).append("; ");
		buf.append("transactionTimestamp:").append(getTransactionTimestamp())
				.append("; ");
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

	/**
	 * @return the unmappedstatus
	 */
	public String getUnmappedstatus() {
		return unmappedstatus;
	}

	/**
	 * @param unmappedstatus
	 *            the unmappedstatus to set
	 */
	public void setUnmappedstatus(String unmappedstatus) {
		this.unmappedstatus = unmappedstatus;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
