package com.bootstrap.dao.repositories;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.bootstrap.dao.model.SolrChromosome;

public interface SolrChromosomeRepository extends SolrCrudRepository<SolrChromosome, Integer> {

	Optional<SolrChromosome> findByName(String name);

	@Query("description: *?0*")
	@Highlight(prefix = "<strong>", postfix = "</strong>", fields = { "description" })
	HighlightPage<SolrChromosome> findByTerm(String term, Pageable pageable);
}
