package com.bootstrap.dao.model;

import java.util.Set;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.Relation;

import lombok.Getter;

@Relation(value = "chromosome", collectionRelation = "chromosomes")
public class ChromosomeResource extends ResourceSupport {

	@Getter
	private final String name;
	@Getter
	private final String size;
	@Getter
	private final Integer genes;
	@Getter
	private final String description;
	@Getter
	private final Set<Locus> loci;

	public ChromosomeResource(Chromosome chromosome) {
		this.name = chromosome.getName();
		this.size = chromosome.getSize();
		this.genes = chromosome.getGenes();
		this.description = chromosome.getDescription();
		this.loci = chromosome.getLoci();
	}
}
