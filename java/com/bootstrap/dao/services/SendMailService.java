package com.bootstrap.dao.services;

import com.bootstrap.dao.model.Subscriber;

public interface SendMailService {

	void sendEmail(String subject, String body);

	void sendRichMail(Subscriber subscriber, String subject, String body);
}
