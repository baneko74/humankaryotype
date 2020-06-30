package com.bootstrap.dao.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.model.SolrLocusEnDocument;
import com.bootstrap.dao.model.SolrLocusRsDocument;
import com.bootstrap.dao.repositories.MatchEnRepository;
import com.bootstrap.dao.repositories.MatchRsRepository;
import com.bootstrap.dao.repositories.SolrLocusEnRepository;
import com.bootstrap.dao.repositories.SolrLocusRsRepository;

import lombok.Data;

@Service("solrService")
@Transactional
@Data
public class SolrServiceImpl implements SolrService {

	private MatchEnRepository matchEnRepo;
	private MatchRsRepository matchRsRepo;
	private SolrLocusEnRepository solrEnRepository;
	private SolrLocusRsRepository solrRsRepository;

	public SolrServiceImpl(SolrLocusEnRepository solrEnRepository, SolrLocusRsRepository solrRsRepository, 
					MatchEnRepository matchEnRepo,MatchRsRepository matchRsRepo) {
		this.solrEnRepository = solrEnRepository;
		this.solrRsRepository = solrRsRepository;
		this.matchEnRepo = matchEnRepo;
		this.matchRsRepo = matchRsRepo;
	}

	@Transactional
	public void saveSolrLocusDocument(String lang, Locus locus) {
		switch (lang) {
		case "en":
			SolrLocusEnDocument solrEnDoc = new SolrLocusEnDocument(locus);
			getSolrEnRepository().save(solrEnDoc);
			break;
		case "rs":
			SolrLocusRsDocument solrRsDoc = new SolrLocusRsDocument(locus);
			getSolrRsRepository().save(solrRsDoc);
			break;
		}
	}
}
