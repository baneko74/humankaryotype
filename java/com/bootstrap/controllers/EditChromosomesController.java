package com.bootstrap.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootstrap.dao.config.ChromosomesProps;
import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.Disease;
import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.model.NewsLetter;
import com.bootstrap.dao.model.Subscriber;
import com.bootstrap.dao.services.ChromosomeService;
import com.bootstrap.dao.services.SendMail;
import com.bootstrap.dao.services.SolrService;
import com.bootstrap.dao.services.SubscriberService;

@Controller
@RequestMapping("/edit/chromosomes")
@SessionAttributes("locus")
public class EditChromosomesController {

	private ChromosomeService chromosomeService;
	private ChromosomesProps props;
	private SolrService solrService;
	private SubscriberService subService;
	private SendMail sendMail;

	Logger logger = LoggerFactory.getLogger(EditChromosomesController.class);

	public EditChromosomesController(ChromosomeService chromosomeService, ChromosomesProps props,
			SolrService solrService, SubscriberService subService, SendMail sendMail) {
		this.chromosomeService = chromosomeService;
		this.solrService = solrService;
		this.props = props;
		this.subService = subService;
		this.sendMail = sendMail;
	}

	@ModelAttribute("locus")
	public Locus locus() {
		return new Locus();
	}

	@GetMapping
	public String getAllChromosomes(Model model, @RequestParam(defaultValue = "0") Integer page) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Page<Chromosome> chroms = null;
		if (lang.equals("en") || lang.equals("rs")) {
			chroms = chromosomeService.findAll(PageRequest.of(page, props.getPageSize()), lang);
		} else {
			chroms = chromosomeService.findAll(PageRequest.of(page, props.getPageSize()), "en");
		}
		model.addAttribute("title", "Edit page for chromosomes");
		model.addAttribute("chromosomes", chroms);
		model.addAttribute("currentPage", page);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/chromosomes_list";
	}

	@GetMapping("/detail")
	public String getChromomosome(Model model, @RequestParam(required = true, name = "chromId") Integer id) {
		Chromosome chromosome = chromosomeService.findById(id).get();
		model.addAttribute("chromosome", chromosome);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/chromosome_edit";
	}

	@PostMapping("/save-chromosome")
	public String saveChromosome(Model model, @ModelAttribute Chromosome chromosome) {
		chromosomeService.saveChromosome(chromosome);
		return "redirect:/edit/chromosomes/";
	}

	@GetMapping("/locus-detail")
	public String getLocus(@RequestParam(name = "chromId", required = true) Integer chromId,
			@RequestParam(name = "locusName", required = true) String name, Model model) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Locus locus = null;
		Chromosome chrom = null;
		if (lang.equals("en") || lang.equals("rs")) {
			locus = chromosomeService.findLocusByName(name, lang);
			if (locus == null) {
				return "redirect:/edit/chromosomes?lang=" + lang;
			}
			chrom = locus.getChromosome();
		} else {
			locus = chromosomeService.findLocusByName(name, "en");
			if (locus == null) {
				return "redirect:/edit/chromosomes?lang=en";
			}
			chrom = locus.getChromosome();
		}
		model.addAttribute("locus", locus);
		model.addAttribute("chromosome", chrom);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/locus_edit";

	}

	@PostMapping("save-locus")
	public String saveLocus(Model model, @ModelAttribute Locus locus) {
		chromosomeService.saveLocus(locus);
		return "redirect:/edit/chromosomes/";
	}

	@GetMapping("/locus-new")
	public String addNewLocus(@RequestParam(name = "chromId", required = true) Integer chromId, Model model) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Locus locus = new Locus();
		Chromosome chrom = chromosomeService.findById(chromId, lang);
		chrom.addLocus(locus);
		model.addAttribute("locus", locus);
		model.addAttribute("chromosome", chrom);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/locus_new";
	}

	@PostMapping("save-new-locus")
	public String saveNewLocus(@Valid @ModelAttribute("locus") Locus locus, Errors errors,
			RedirectAttributes redirectAttrs, @ModelAttribute("subscriber") Subscriber subscriber) {
		if (errors.hasErrors()) {
			return "edits/locus_new";
		}
		chromosomeService.saveLocus(locus);
		redirectAttrs.addAttribute("locus", locus);
		return "redirect:/edit/chromosomes/new-disease";
	}

	@GetMapping("/new-disease")
	public String addNewDisease(@ModelAttribute("locus") Locus locus, Model model) {
		Disease disease = new Disease();
		disease.setLocus(locus);
		locus.setDisease(disease);
		model.addAttribute("disease", disease);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/disease_new";
	}

	@GetMapping("/disorder-detail")
	public String getDisease(Model model, @RequestParam(name = "chromId", required = true) Integer id,
			@RequestParam(name = "locusName", required = true) String locusName,
			@RequestParam(name = "disorderName", required = true) String diseaseName) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		Disease disease = null;
		Locus locus = null;
		Chromosome chrom = null;
		if (lang.equals("rs") || lang.equals("en")) {
			locus = chromosomeService.findLocusByName(locusName, lang);
			if (locus == null) {
				return "redirect:/edit/chromosomes?lang=" + lang;
			}
			chrom = locus.getChromosome();
			disease = locus.getDisease();
		} else {
			locus = chromosomeService.findLocusByName(locusName, "en");
			if (locus == null) {
				return "redirect:/edit/chromosomes?lang=en";
			}
			chrom = locus.getChromosome();
			disease = locus.getDisease();
		}
		model.addAttribute("disease", disease);
		model.addAttribute("locus", locus);
		model.addAttribute("chromosome", chrom);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/disease_edit";
	}

	@PostMapping("/save-disease")
	public String saveDisease(@ModelAttribute Disease disease, Model model, RedirectAttributes redirAttr) {
		chromosomeService.saveDisease(disease);
		Locus locus = disease.getLocus();
		redirAttr.addAttribute("locus", locus);
		redirAttr.addAttribute("disease", disease);
		return "redirect:/edit/chromosomes/index-new-data";
	}

	@GetMapping("/notify-subscribers")
	public String notifyAllSubscribers(Model model) {
		NewsLetter letter = new NewsLetter();
		model.addAttribute("letter", letter);
		model.addAttribute("subscriber", new Subscriber());
		return "notify-subscribers";
	}

	@PostMapping("/sendEmail")
	public String sendEmails(@Valid @ModelAttribute("letter") NewsLetter letter,
			Errors errors,
			@ModelAttribute("subscriber") Subscriber subscriber) {
		if (errors.hasErrors()) {
			return "notify-subscribers";
		}
		// dodati unsubscribe link
		String lang = LocaleContextHolder.getLocale().getLanguage();
		subService.findAllByLang(lang).stream().forEach(sub -> {
			sendMail.sendEmail(sub, letter.getSubject(), letter.getBody());
		});
		return "redirect:/edit/chromosomes";
	}

	@GetMapping("/index-new-data")
	private String indexSolrLocusDoc(@ModelAttribute("locus") Locus locus, Model model,
			@ModelAttribute("disease") Disease disease) {
		model.addAttribute("locus", locus);
		model.addAttribute("subscriber", new Subscriber());
		return "edits/index_new_data";
	}

	@PostMapping("/index-solr")
	private String indexSolr(@ModelAttribute("locus") Locus locus) {
		String lang = LocaleContextHolder.getLocale().getLanguage();
		solrService.saveSolrLocusDocument(lang, locus);
		return "redirect:/edit/chromosomes";
	}
}
