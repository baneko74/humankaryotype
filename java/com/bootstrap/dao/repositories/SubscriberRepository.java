package com.bootstrap.dao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bootstrap.dao.model.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

	@Query("select case when count(s)>0 then true else false end from Subscriber s where s.email = :email")
	boolean existByEmail(@Param("email") String email);

	@Query("select distinct s from Subscriber s where s.lang = :lang")
	List<Subscriber> findAllByLang(@Param("lang") String lang);

	@Query("select distinct s from Subscriber s where s.sha1 = :code")
	Subscriber findBySha1(@Param("code") String code);
}
