package com.bootstrap.dao.services;

import java.util.Map;

import com.bootstrap.dao.model.EmailResponse;
import com.bootstrap.dao.model.Subscriber;

public interface SendMail {

	void sendEmail(Subscriber subscriber, String subject, String body);

	EmailResponse sendRichMail(Subscriber subscriber, String subject, Map<String, Object> model, String lang);
}
