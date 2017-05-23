/**
 * 
 */
package com.pallette.config.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author amall3
 *
 */
@Component
public class EmailHtmlSender {

	@Autowired
	private EmailService emailService;

	@Autowired
	private TemplateEngine templateEngine;

	public EmailStatus send(String emailTo, String emailSubject, String templateName, Context context) {
		
		String body = templateEngine.process(templateName, context);
		return emailService.sendHtmlMessage(emailTo, emailSubject, body);
	}
}
