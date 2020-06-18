package com.bootstrap.dao.services;

import org.springframework.stereotype.Component;

import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.model.SolrLocusEnDocument;
import com.bootstrap.dao.model.SolrLocusRsDocument;
import com.bootstrap.dao.repositories.SolrLocusEnRepository;
import com.bootstrap.dao.repositories.SolrLocusRsRepository;

import lombok.Data;

@Component
@Data
public class SolrService {

	private SolrLocusEnRepository solrEnRepository;
	private SolrLocusRsRepository solrRsRepository;

	public SolrService(SolrLocusEnRepository solrEnRepository, SolrLocusRsRepository solrRsRepository) {
		this.solrEnRepository = solrEnRepository;
		this.solrRsRepository = solrRsRepository;
	}

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
