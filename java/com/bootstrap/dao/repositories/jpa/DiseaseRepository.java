package com.bootstrap.dao.repositories.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bootstrap.dao.model.Disease;

public interface DiseaseRepository extends CrudRepository<Disease, Integer> {

	@Query("select distinct d from Locus l JOIN l.disease d where l.name = :name and l.lang = :lang")
	Disease findDiseaseByLocusName(@Param("name") String name, @Param("lang") String lang);

}
