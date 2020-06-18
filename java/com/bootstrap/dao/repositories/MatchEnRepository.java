package com.bootstrap.dao.repositories;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import com.bootstrap.dao.model.Match;
import com.bootstrap.dao.model.MatchEn;

public interface MatchEnRepository extends SolrCrudRepository<MatchEn, Integer> {

	@Query(value = "{!edismax v='?0' qf='chromosomeName description^5 locusName fullName locusRole diseaseRole content' pf='description locusRole diseaseRole content' pf2='description locusRole diseaseRole content' q.op='OR'}", fields = {
			"id", "chromosomeName", "locusName", "chromId", "link" })
	@Highlight(prefix = "<span class='highlight'>", postfix = "</span>", fields = { "description", "locusRole",
			"diseaseRole", "fullName", "content" }, fragsize = 199)
	HighlightPage<Match> findByNameIn(Collection<String> names, Pageable pageable);
}
