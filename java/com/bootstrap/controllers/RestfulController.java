package com.bootstrap.controllers;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.ChromosomeResource;
import com.bootstrap.dao.model.ChromosomeResourceAssembler;
import com.bootstrap.dao.services.ChromosomeService;

@RestController
@RequestMapping("/chromosomes")
public class RestfulController {
	
	private final ChromosomeService chromosomeService;

	public RestfulController(ChromosomeService chromosomeService) {
		this.chromosomeService = chromosomeService;
	}

	@GetMapping(path = "/list-all", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resources<ChromosomeResource>> getChromInfo() {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		List<Chromosome> chroms = chromosomeService.findAll(lang);
		List<ChromosomeResource> chromosomeResources = new ChromosomeResourceAssembler().toResources(chroms);
		Resources<ChromosomeResource> resources = new Resources<ChromosomeResource>(chromosomeResources);
		resources.add(ControllerLinkBuilder.linkTo(Chromosome.class).slash("chromosomes").slash("rest").slash("all")
				.withRel("allChromosomes"));
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@GetMapping(path = "/get-chromosome/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ChromosomeResource> getChromosome(@PathVariable("id") Integer id) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		if (id > 24) {
			ChromosomeResource res = new ChromosomeResource();
			return new ResponseEntity<ChromosomeResource>(res, HttpStatus.NOT_FOUND);
		}
		if (lang.equals("rs")) {
			id = id + 24;
		}
		Chromosome chrom = chromosomeService.findById(id, lang);
		ChromosomeResource chr = new ChromosomeResource(chrom);
		return new ResponseEntity<ChromosomeResource>(chr, HttpStatus.OK);
	}
}
