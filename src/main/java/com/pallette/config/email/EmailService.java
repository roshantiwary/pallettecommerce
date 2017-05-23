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
	 * <p>
	 * Send Email using SimpleMailMessage.
	 * </p>
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @return EmailStatus
	 */
	EmailStatus sendSimpleMessage(String to, String subject, String text);

	/**
	 * <p>
	 * Send Email using Template.
	 * </p>
	 * 
	 * @param to
	 * @param subject
	 * @param template
	 * @param templateArgs
	 * @return EmailStatus
	 */
	EmailStatus sendSimpleMessageUsingTemplate(String to, String subject, SimpleMailMessage template, String... templateArgs);

	/**
	 * <p>
	 * Send Email With Attachment.
	 * </p>
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @param pathToAttachment
	 * @return EmailStatus
	 */
	EmailStatus sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment, boolean isHTML);

	/**
	 * <p>
	 * Send Email With HTML Message.
	 * </p>
	 * 
	 * @param to
	 * @param subject
	 * @param text
	 * @param isHtml
	 * @return EmailStatus
	 */
	EmailStatus sendHtmlMessage(String to, String subject, String text);

}
