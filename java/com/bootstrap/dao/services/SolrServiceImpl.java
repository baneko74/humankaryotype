package com.bootstrap.dao.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.Disease;
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
	public void saveSolrLocusDocument(String lang, Locus locus, Disease disease, Chromosome chrom) {
		switch (lang) {
		case "en":
			SolrLocusEnDocument solrEnDoc = new SolrLocusEnDocument();
			solrEnDoc.setId(locus.getId());
			solrEnDoc.setLocusName(locus.getName());
			solrEnDoc.setLocusRole(locus.getBioRole());
			solrEnDoc.setDiseaseName(disease.getName());
			solrEnDoc.setDiseaseRole(disease.getDescription());
			solrEnDoc.setFullName(locus.getFullName());
			solrEnDoc.setChromId(chrom.getId());
			solrEnDoc.setLocusId(locus.getId());
			getSolrEnRepository().save(solrEnDoc);
			break;
		case "rs":
			SolrLocusRsDocument solrRsDoc = new SolrLocusRsDocument();
			solrRsDoc.setId(locus.getId());
			solrRsDoc.setLocusName(locus.getName());
			solrRsDoc.setLocusRole(locus.getBioRole());
			solrRsDoc.setDiseaseName(disease.getName());
			solrRsDoc.setDiseaseRole(disease.getDescription());
			solrRsDoc.setFullName(locus.getFullName());
			solrRsDoc.setChromId(chrom.getId());
			solrRsDoc.setLocusId(locus.getId());
			getSolrRsRepository().save(solrRsDoc);
			break;
		}
	}
}
