package com.bootstrap.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bootstrap.dao.model.AjaxResponse;
import com.bootstrap.dao.model.Subscriber;
import com.bootstrap.dao.services.SubscriberService;

@RestController
public class SubscriberAjax {

	private SubscriberService subService;

	private MessageSource messageSource;

	@Autowired
	public SubscriberAjax(SubscriberService subService, MessageSource messageSource) {
		this.messageSource = messageSource;
		this.subService = subService;
	}

	@PostMapping(value = "/subscribeEmail", consumes = "application/json")
	public ResponseEntity<AjaxResponse> getAjaxSubcsription(@Valid @RequestBody Subscriber subscriber, Errors errors) {
		AjaxResponse result = new AjaxResponse();
		if (errors.hasErrors()) {
			result.setStatus("error");
			result.setMessage(errors.getFieldError().getDefaultMessage());
			return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		String lang = LocaleContextHolder.getLocale().getLanguage();
		subscriber.setLang(lang);
		subService.save(subscriber);
		result.setStatus("success");
		result.setMessage(messageSource.getMessage("ajax.message", null, LocaleContextHolder.getLocale()));
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

}
