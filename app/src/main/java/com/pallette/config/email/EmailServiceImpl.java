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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JavaMailSender emailSender;

	public EmailStatus sendMessageWithAttachment(String emailTo, String emailSubject, String emailBody , String pathToAttachment , boolean isHtml) {

		logger.info("Inside EmailServiceImpl.sendMessageWithAttachment()");
		try {
			MimeMessage message = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true); // true indicates multi-part message
			helper.setSubject(emailSubject);
			helper.setTo(emailTo);
			helper.setText(emailBody, isHtml); // true indicates html continue using helper object for more
												// functionalities like adding attachments, etc.
			FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
			helper.addAttachment(EmailConstants.ATTACHMENT, file);

			emailSender.send(message);
			logger.info("Send email '{}' to: {}", emailSubject, emailTo);
			
			return new EmailStatus(emailTo, emailSubject, emailBody).success();
		} catch (MailException | MessagingException msgExp) {
			logger.error("Problem with sending email with attachment to: {}, error message: {}", emailTo, msgExp.getMessage());
			return new EmailStatus(emailTo, emailSubject, emailBody).error(msgExp.getMessage());
		}
	}

	@Override
	public EmailStatus sendSimpleMessage(String emailTo, String emailSubject, String emailBody) {

		logger.info("Inside EmailServiceImpl.sendSimpleMessage()");
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(emailTo);
			message.setSubject(emailSubject);
			message.setText(emailBody);

			emailSender.send(message);
			logger.info("Send email '{}' to: {}", emailSubject, emailTo);
			
			return new EmailStatus(emailTo, emailSubject, emailBody).success();
		} catch (MailException msgExp) {
			logger.error("Problem with sending Simple Email Message to: {}, error message: {}", emailTo, msgExp.getMessage());
			return new EmailStatus(emailTo, emailSubject, emailBody).error(msgExp.getMessage());
		}
	}
	
	public EmailStatus sendHtmlMessage(String emailTo, String emailSubject, String emailBody) {
		
		logger.info("Inside EmailServiceImpl.sendHtmlMessage()");
		try {
			MimeMessage mail = emailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mail, true);
			helper.setTo(emailTo);
			helper.setSubject(emailSubject);
			helper.setText(emailBody, Boolean.TRUE);
			
			emailSender.send(mail);
			logger.info("Send email '{}' to: {}", emailSubject, emailTo);
			
			return new EmailStatus(emailTo, emailSubject, emailBody).success();
		} catch (Exception exp) {
			logger.error("Problem with sending HTML email to: {}, error message: {}", emailTo, exp.getMessage());
			return new EmailStatus(emailTo, emailSubject, emailBody).error(exp.getMessage());
		}
	}

    @Override
    public EmailStatus sendSimpleMessageUsingTemplate(String emailTo,  String emailSubject,  SimpleMailMessage template,  String ...templateArgs) {
        String emailBody = String.format(template.getText(), templateArgs);  
        return sendSimpleMessage(emailTo, emailSubject, emailBody);
    }

}
