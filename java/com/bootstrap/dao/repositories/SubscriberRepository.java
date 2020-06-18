package com.bootstrap.dao.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bootstrap.dao.model.Subscriber;

public interface SubscriberRepository extends CrudRepository<Subscriber, Long> {

	@Query("select case when count(s)>0 then true else false end from Subscriber s where s.email = :email")
	boolean existByEmail(@Param("email") String email);
}
