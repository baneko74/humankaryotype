package com.bootstrap.dao.services;

import java.util.Map;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.bootstrap.dao.model.EmailResponse;
import com.bootstrap.dao.model.Subscriber;

import freemarker.template.Configuration;

@Service("sendMail")
public class SendMailImpl implements SendMail {

	private JavaMailSender mailSender;
	private Configuration config;

	public SendMailImpl(JavaMailSender mailSender, Configuration config) {
		this.config = config;
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(Subscriber subscriber, String subject, String text) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(subscriber.getEmail());
		msg.setSubject(subject);
		msg.setText(text);
		mailSender.send(msg);
	}

	@Override
	public EmailResponse sendRichMail(Subscriber subscriber, Map<String, Object> model) {
		// TODO Auto-generated method stub
		return null;
	}

}
