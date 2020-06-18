package com.bootstrap.dao.services;

import java.util.List;
import java.util.Optional;

import com.bootstrap.dao.model.Subscriber;

public interface SubscriberService {

	List<Subscriber> findAll();

	Optional<Subscriber> findByEmail(String email);

	Subscriber findById(Long id);

	Subscriber save(Subscriber subscriber);

	void delete(Subscriber subscriber);

	boolean existByEmail(String email);
}
