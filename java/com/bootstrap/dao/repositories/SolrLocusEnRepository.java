package com.bootstrap.dao.repositories;

import org.springframework.data.solr.repository.SolrCrudRepository;

import com.bootstrap.dao.model.SolrLocusEnDocument;

public interface SolrLocusEnRepository extends SolrCrudRepository<SolrLocusEnDocument, Integer> {

}
