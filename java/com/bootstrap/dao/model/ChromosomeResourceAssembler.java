package com.bootstrap.dao.model;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.bootstrap.controllers.ChromosomeController;

public class ChromosomeResourceAssembler extends ResourceAssemblerSupport<Chromosome, ChromosomeResource> {

	public ChromosomeResourceAssembler() {
		super(ChromosomeController.class, ChromosomeResource.class);
	}

	@Override
	protected ChromosomeResource instantiateResource(Chromosome chromosome) {
		return new ChromosomeResource(chromosome);
	}

	@Override
	public ChromosomeResource toResource(Chromosome chromosome) {
		return createResourceWithId(chromosome.getId(), chromosome);
	}

}
