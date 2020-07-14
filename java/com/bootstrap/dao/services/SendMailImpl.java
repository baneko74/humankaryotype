package com.bootstrap.dao.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bootstrap.dao.model.Subscriber;

@Service("sendMailService")
public class SendMailImpl implements SendMailService {

	private JavaMailSender mailSender;
	private TemplateEngine engine;

	public SendMailImpl(JavaMailSender mailSender, TemplateEngine engine) {
		this.mailSender = mailSender;
		this.engine = engine;
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
	public void sendRichMail(Subscriber subscriber, String subject, String body) {
		MimeMessagePreparator preparator = mimeMsg -> {
			MimeMessageHelper helper = new MimeMessageHelper(mimeMsg);
			helper.setFrom("hkaryotype@gmail.com");
			helper.setTo(subscriber.getEmail());
			helper.setSubject(subject);
			String content = build(body, subscriber.getSha1());
			helper.setText(content, true);
		};
		try {
			mailSender.send(preparator);
		} catch (MailException e) {

		}
	}

	private String build(String message, String code) {
		Context ctx = new Context();
		LocalDateTime date = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String fmtDate = date.format(formatter);
		String unSubLink = "http://localhost:8080/unSubscribe?code=" + code;
		ctx.setVariable("unsubscribe", unSubLink);
		ctx.setVariable("date", fmtDate);
		ctx.setVariable("message", message);
		return engine.process("mailTemplate", ctx);
	}
}
