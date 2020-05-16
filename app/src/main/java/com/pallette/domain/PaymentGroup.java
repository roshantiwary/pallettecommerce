/**
 * 
 */
package com.pallette.domain;

import java.io.Serializable;
import java.util.ArrayList;
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
	private String id;

	@Field(value = "payment_group_type")
	private String paymentGroupType;

	@Field(value = "payment_method")
	private String paymentMethod;

	private String description;

	private String state;

	private double amount;

	private String mode;

	private double discount;

	@Field(value = "net_amount_debit")
	private double netAmountDebit;

	@Field(value = "encrypted_payment_id")
	private String encryptedPaymentId;

	@Field(value = "mih_pay_id")
	private String mihpayid;

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
	private List<PaymentStatus> debitStatus;

	public void addDebitStatus(PaymentStatus paymentStatus) {
		if (null == this.debitStatus) {
			debitStatus = new ArrayList<PaymentStatus>();
			debitStatus.add(paymentStatus);
		} else {
			debitStatus.add(paymentStatus);
		}
	}

	@DBRef
	@CascadeSave
	private List<PaymentStatus> creditStatus;

	public PaymentGroup() {
		super();
	}

	public PaymentGroup(String id) {
		this.id = id;
	}

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
	 * @return the debitStatus
	 */
	public List<PaymentStatus> getDebitStatus() {
		return debitStatus;
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

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode
	 *            the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the encryptedPaymentId
	 */
	public String getEncryptedPaymentId() {
		return encryptedPaymentId;
	}

	/**
	 * @param encryptedPaymentId
	 *            the encryptedPaymentId to set
	 */
	public void setEncryptedPaymentId(String encryptedPaymentId) {
		this.encryptedPaymentId = encryptedPaymentId;
	}

	/**
	 * @return the mihpayid
	 */
	public String getMihpayid() {
		return mihpayid;
	}

	/**
	 * @param mihpayid
	 *            the mihpayid to set
	 */
	public void setMihpayid(String mihpayid) {
		this.mihpayid = mihpayid;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	/**
	 * @param discount
	 *            the discount to set
	 */
	public void setDiscount(double discount) {
		this.discount = discount;
	}

	/**
	 * @return the netAmountDebit
	 */
	public double getNetAmountDebit() {
		return netAmountDebit;
	}

	/**
	 * @param netAmountDebit
	 *            the netAmountDebit to set
	 */
	public void setNetAmountDebit(double netAmountDebit) {
		this.netAmountDebit = netAmountDebit;
	}

}
