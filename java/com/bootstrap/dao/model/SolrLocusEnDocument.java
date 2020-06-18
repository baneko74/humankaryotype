package com.bootstrap.dao.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.bootstrap.dao.repositories.MatchEnRepository;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SolrDocument(collection = "chromosomes")
public class SolrLocusEnDocument {

	@Autowired
	private MatchEnRepository matchRepo;

	public SolrLocusEnDocument(Locus locus) {
		this.id = (int) matchRepo.count() + 1;
		this.locusName = locus.getName();
		this.fullName = locus.getFullName();
		this.locusRole = locus.getBioRole();
		this.diseaseName = locus.getDisease().getName();
		this.diseaseRole = locus.getDisease().getDescription();
		this.chromId = locus.getChromosome().getId();
		this.locusId = locus.getId();
	}

	@Id
	@Field
	private Integer id;

	@Field
	private String locusName;

	@Field
	private String locusRole;

	@Field
	private String diseaseName;

	@Field
	private String diseaseRole;

	@Field
	private String fullName;

	@Field
	private Integer chromId;

	@Field
	private Integer locusId;

}
