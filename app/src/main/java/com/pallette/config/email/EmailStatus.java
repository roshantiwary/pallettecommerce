/**
 * 
 */
package com.pallette.config.email;

/**
 * @author amall3
 *
 */
public class EmailStatus {

	private final String to;
	private final String subject;
	private final String body;

	private String status;
	private String errorMessage;

	public EmailStatus(String to, String subject, String body) {
		this.to = to;
		this.subject = subject;
		this.body = body;
	}

	public EmailStatus success() {
		this.status = EmailConstants.SUCCESS;
		return this;
	}

	public EmailStatus error(String errorMessage) {
		this.status = EmailConstants.ERROR;
		this.errorMessage = errorMessage;
		return this;
	}

	public boolean isSuccess() {
		return EmailConstants.SUCCESS.equals(this.status);
	}

	public boolean isError() {
		return EmailConstants.ERROR.equals(this.status);
	}

	public String getTo() {
		return to;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}

	public String getStatus() {
		return status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
