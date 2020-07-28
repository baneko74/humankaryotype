package com.bootstrap.controllers;

import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootstrap.dao.config.ChromosomesProps;
import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.ChromosomeResource;
import com.bootstrap.dao.model.ChromosomeResourceAssembler;
import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.model.Subscriber;
import com.bootstrap.dao.services.ChromosomeRepositoryService;
import com.bootstrap.dao.services.ChromosomeService;

@Controller
@RequestMapping("/chromosomes")
public class ChromosomeController {

	private ChromosomeService chromosomeService;
	private ChromosomesProps props;

	public ChromosomeController(ChromosomeRepositoryService chromosomeService, ChromosomesProps props) {
		this.chromosomeService = chromosomeService;
		this.props = props;
	}

	@GetMapping
	public String getChromosomes(Model model, @RequestParam(defaultValue = "0") int page) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Page<Chromosome> chroms = null;
		if (lang.equals("rs") || lang.equals("en")) {
			chroms = chromosomeService.findAll(PageRequest.of(page, props.getPageSize()), lang);
		} else {
			chroms = chromosomeService.findAll(PageRequest.of(page, props.getPageSize()), "en");
		}
		model.addAttribute("chromosomes", chroms);
		model.addAttribute("currentPage", page);
		model.addAttribute("subscriber", new Subscriber());
		return "chromosomes";
	}

	@GetMapping("/{id}")
	public String getChromosomeById(@PathVariable Integer id, Model model) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Chromosome chrom = null;
		if (lang.equals("rs") || lang.equals("en")) {
			chrom = chromosomeService.findById(id, lang);
			if (chrom == null) {
				return "redirect:/chromosomes?lang=" + lang;
			}
		} else {
			chrom = chromosomeService.findById(id, "en");
			if (chrom == null) {
				return "redirect:/chromosomes?lang=" + lang;
			}
		}
		model.addAttribute("subscriber", new Subscriber());
		model.addAttribute("chromosome", chrom);
		return "chromosome";
	}

	@GetMapping("/{chrom_id}/locus/{name}")
	public String getLoci(@PathVariable("chrom_id") Integer chromId, @PathVariable String name, Model model) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Locus locus = null;
		Chromosome chro = null;
		if (lang.equals("rs") || lang.equals("en")) {
			locus = chromosomeService.findLocusByName(name, lang);
			if (locus == null) {
				return "redirect:/chromosomes/" + chromId + "/locus?lang=" + lang;
			}
			chro = locus.getChromosome();
		} else {
			locus = chromosomeService.findLocusByName(name, "en");
			if (locus == null) {
				return "redirect:/chromosomes/" + chromId + "/locus?lang=en";
			}
			chro = locus.getChromosome();
		}

		model.addAttribute("chromosome", chro);
		model.addAttribute("subscriber", new Subscriber());
		model.addAttribute("locus", locus);
		return "locus";

	}

	@GetMapping(path = "/rest/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Resources<ChromosomeResource>> getChromInfo() {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		List<Chromosome> chroms = chromosomeService.findAll(lang);
		List<ChromosomeResource> chromosomeResources = new ChromosomeResourceAssembler().toResources(chroms);
		Resources<ChromosomeResource> resources = new Resources<ChromosomeResource>(chromosomeResources);
		resources.add(ControllerLinkBuilder.linkTo(Chromosome.class).slash("chromosomes").slash("rest").slash(
				"all")
				.withRel("allChromosomes"));
		return new ResponseEntity<>(resources, HttpStatus.OK);
	}

	@GetMapping(path = "/rest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<ChromosomeResource> getChromosome(@PathVariable("id") Integer id) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		if (id > 24) {
			ChromosomeResource res = new ChromosomeResource();
			return new ResponseEntity<ChromosomeResource>(res, HttpStatus.BAD_REQUEST);
		}
		if (lang.equals("rs")) {
			id = id + 24;
		}
		Chromosome chrom = chromosomeService.findById(id, lang);
		ChromosomeResource chr = new ChromosomeResource(chrom);
		return new ResponseEntity<ChromosomeResource>(chr, HttpStatus.OK);
	}
}
