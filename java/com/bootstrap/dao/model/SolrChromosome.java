package com.bootstrap.dao.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@SolrDocument(collection = "humangenes")
public class SolrChromosome {

	@Id
	@Field
	private Integer id;

	@Field
	private String name;

	@Field
	private String description;

	@Field
	private Integer genes;

}
