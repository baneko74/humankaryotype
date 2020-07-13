package com.bootstrap.dao.services;

import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.Disease;
import com.bootstrap.dao.model.Locus;

public interface SolrService {
	void saveSolrLocusDocument(String lang, Locus locus, Disease disease, Chromosome chrom);
}
