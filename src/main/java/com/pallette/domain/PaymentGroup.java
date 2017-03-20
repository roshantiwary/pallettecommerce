/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.pallette.config.CascadeSave;

/**
 * @author amall3
 *
 */

@Document(collection = "paymentGroup")
public class PaymentGroup implements Serializable {

	@Id
	private ObjectId id;

	@Field(value = "payment_group_type")
	private String paymentGroupType;

	@Field(value = "payment_method")
	private String paymentMethod;

	private String description;

	private String state;

	private double amount;

	@Field(value = "amount_authorized")
	private double amountAuthorized;

	@Field(value = "amount_debited")
	private double amountDebited;

	@Field(value = "amount_credited")
	private double amountCredited;

	@Field(value = "currency_code")
	private String currencyCode;

	@Field(value = "submitted_date")
	private Date submittedDate;

	@Field(value = "special_instructions")
	private String specialInstructions;

	@DBRef
	@CascadeSave
	private List<PaymentStatus> authorizationStatus;

	@DBRef
	@CascadeSave
	private List<PaymentStatus> debitStatus;

	@DBRef
	@CascadeSave
	private List<PaymentStatus> creditStatus;

	public PaymentGroup() {
		super();
	}

	public PaymentGroup(ObjectId id) {
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
	 * @return the paymentGroupType
	 */
	public String getPaymentGroupType() {
		return paymentGroupType;
	}

	/**
	 * @param paymentGroupType
	 *            the paymentGroupType to set
	 */
	public void setPaymentGroupType(String paymentGroupType) {
		this.paymentGroupType = paymentGroupType;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod
	 *            the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
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
	 * @return the amountAuthorized
	 */
	public double getAmountAuthorized() {
		return amountAuthorized;
	}

	/**
	 * @param amountAuthorized
	 *            the amountAuthorized to set
	 */
	public void setAmountAuthorized(double amountAuthorized) {
		this.amountAuthorized = amountAuthorized;
	}

	/**
	 * @return the amountDebited
	 */
	public double getAmountDebited() {
		return amountDebited;
	}

	/**
	 * @param amountDebited
	 *            the amountDebited to set
	 */
	public void setAmountDebited(double amountDebited) {
		this.amountDebited = amountDebited;
	}

	/**
	 * @return the amountCredited
	 */
	public double getAmountCredited() {
		return amountCredited;
	}

	/**
	 * @param amountCredited
	 *            the amountCredited to set
	 */
	public void setAmountCredited(double amountCredited) {
		this.amountCredited = amountCredited;
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
	 * @return the submittedDate
	 */
	public Date getSubmittedDate() {
		return submittedDate;
	}

	/**
	 * @param submittedDate
	 *            the submittedDate to set
	 */
	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	/**
	 * @return the specialInstructions
	 */
	public String getSpecialInstructions() {
		return specialInstructions;
	}

	/**
	 * @param specialInstructions
	 *            the specialInstructions to set
	 */
	public void setSpecialInstructions(String specialInstructions) {
		this.specialInstructions = specialInstructions;
	}

	/**
	 * @return the authorizationStatus
	 */
	public List<PaymentStatus> getAuthorizationStatus() {
		return authorizationStatus;
	}

	/**
	 * @param authorizationStatus
	 *            the authorizationStatus to set
	 */
	public void setAuthorizationStatus(List<PaymentStatus> authorizationStatus) {
		this.authorizationStatus = authorizationStatus;
	}

	/**
	 * @return the debitStatus
	 */
	public List<PaymentStatus> getDebitStatus() {
		return debitStatus;
	}

	/**
	 * @param debitStatus
	 *            the debitStatus to set
	 */
	public void setDebitStatus(List<PaymentStatus> debitStatus) {
		this.debitStatus = debitStatus;
	}

	/**
	 * @return the creditStatus
	 */
	public List<PaymentStatus> getCreditStatus() {
		return creditStatus;
	}

	/**
	 * @param creditStatus
	 *            the creditStatus to set
	 */
	public void setCreditStatus(List<PaymentStatus> creditStatus) {
		this.creditStatus = creditStatus;
	}

}
