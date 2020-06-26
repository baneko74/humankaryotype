package com.bootstrap.dao.services;

import org.springframework.stereotype.Service;

import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.model.SolrLocusEnDocument;
import com.bootstrap.dao.model.SolrLocusRsDocument;
import com.bootstrap.dao.repositories.MatchEnRepository;
import com.bootstrap.dao.repositories.MatchRsRepository;
import com.bootstrap.dao.repositories.SolrLocusEnRepository;
import com.bootstrap.dao.repositories.SolrLocusRsRepository;

import lombok.Data;

@Service("solrService")
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

	public void saveSolrLocusDocument(String lang, Locus locus) {
		switch (lang) {
		case "en":
			SolrLocusEnDocument solrEnDoc = new SolrLocusEnDocument(locus);
			solrEnDoc.setId((int) (matchEnRepo.count() + 1));
			getSolrEnRepository().save(solrEnDoc);
			break;
		case "rs":
			SolrLocusRsDocument solrRsDoc = new SolrLocusRsDocument(locus);
			solrRsDoc.setId((int) (matchRsRepo.count() + 1));
			getSolrRsRepository().save(solrRsDoc);
			break;
		}
	}
	
	public void saveExistingSolrLocusDocument(String lang, Locus locus) {
		switch (lang) {
		case "en":
			SolrLocusEnDocument solrEnDoc = new SolrLocusEnDocument(locus);
			solrEnDoc.setId(locus.getId());
			getSolrEnRepository().save(solrEnDoc);
			break;
		case "rs":
			SolrLocusRsDocument solrRsDoc = new SolrLocusRsDocument(locus);
			solrRsDoc.setId(locus.getId());
			getSolrRsRepository().save(solrRsDoc);
			break;
		}
	}
}
