/**
 * 
 */
package com.pallette.config.email;

import org.springframework.mail.SimpleMailMessage;

/**
 * @author amall3
 *
 */
public interface EmailService {

	/**
	 * <p>Send Email using </p>
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 */
	void sendSimpleMessage(String to, String subject, String text);

	/**
	 * 
	 * @param to
	 * @param subject
	 * @param template
	 * @param templateArgs
	 */
	void sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs);

	/**
	 * <p>Send Email With Attachment. </p>
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @param pathToAttachment
	 */
	void sendMessageWithAttachment(String to, String subject, String text, 	String pathToAttachment);

}
