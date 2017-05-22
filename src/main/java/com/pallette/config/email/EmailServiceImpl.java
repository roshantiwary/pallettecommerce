package com.pallette.config.email;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
	
	private static final String ATTACHMENT = "Attachment";

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender emailSender;

	public void sendMessageWithAttachment(String emailTo, String emailSubject, String emailBody , String pathToAttachment) {

		logger.info("Inside EmailServiceImpl.sendMessageWithAttachment()");
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = null;
		try {
			helper = new MimeMessageHelper(message, true); // true indicates multi-part message
			helper.setSubject(emailSubject);
			helper.setTo(emailTo);
			helper.setText(emailBody, true); // true indicates html continue using helper object for more
												// functionalities like adding attachments, etc.
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment(ATTACHMENT, file);

			emailSender.send(message);
			
		} catch (MailException | MessagingException msgExp) {
			logger.error("Exception while sending emails", msgExp);
		}
	}

	@Override
	public void sendSimpleMessage(String emailTo, String emailSubject, String emailBody) {

		logger.info("Inside EmailServiceImpl.sendSimpleMessage()");
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(emailTo);
			message.setSubject(emailSubject);
			message.setText(emailBody);

			emailSender.send(message);
		} catch (MailException msgExp) {
			logger.error("Exception while sending emails", msgExp);
		}
		logger.info("Email Sent Successfully!!");
	}

    @Override
    public void sendSimpleMessageUsingTemplate(String emailTo,  String emailSubject,  SimpleMailMessage template,  String ...templateArgs) {
        String emailBody = String.format(template.getText(), templateArgs);  
        sendSimpleMessage(emailTo, emailSubject, emailBody);
    }

}
