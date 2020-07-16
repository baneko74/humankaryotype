package com.bootstrap.dao.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootstrap.dao.model.Subscriber;
import com.bootstrap.dao.repositories.jpa.SubscriberRepository;

@Service("subscriberService")
@Transactional
public class SubscriberServiceImpl implements SubscriberService {

	@Autowired
	private SubscriberRepository subscriberRepo;

	@Transactional(readOnly = true)
	@Override
	public List<Subscriber> findAll() {
		return (List<Subscriber>) subscriberRepo.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Optional<Subscriber> findByEmail(String email) {
		return ((List<Subscriber>) subscriberRepo.findAll()).stream().filter(sub -> sub.getEmail().equals(email))
				.findFirst();
	}

	@Transactional
	@Override
	public List<Subscriber> findAllByLang(String lang) {
		return subscriberRepo.findAllByLang(lang);
	}

	@Override
	public Subscriber findById(Long id) {
		return subscriberRepo.findById(id).get();
	}

	@Override
	public Subscriber findBySha1(String code) {
		return subscriberRepo.findBySha1(code);
	}
	@Override
	public Subscriber save(Subscriber subscriber) {
		return subscriberRepo.save(subscriber);
	}

	@Override
	public void delete(Subscriber subscriber) {
		subscriberRepo.delete(subscriber);

	}

	@Override
	public boolean existByEmail(String email) {
		return subscriberRepo.existByEmail(email);
	}


}
