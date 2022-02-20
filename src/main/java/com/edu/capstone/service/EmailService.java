package com.edu.capstone.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

/**
 * @author NhatHH
 * Date: Feb 1, 2022
 */
@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	/**
	 * Gửi email
	 * 
	 * @version 1.0 - Initiation (Feb 1, 2022 by <b>NhatHH</b>)
	 * @throws MessagingException 
	 */
	public void sendMail(String to, String subject, String content) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content, true);
		
		javaMailSender.send(message);
	}
	
}
