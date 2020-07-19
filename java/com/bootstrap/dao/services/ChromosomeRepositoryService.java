package com.bootstrap.dao.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.Disease;
import com.bootstrap.dao.model.Locus;
import com.bootstrap.dao.repositories.jpa.ChromosomeRepository;
import com.bootstrap.dao.repositories.jpa.DiseaseRepository;
import com.bootstrap.dao.repositories.jpa.LocusRepository;

@Service("chromosomeService")
@Repository
@Transactional
public class ChromosomeRepositoryService implements ChromosomeService {

	private LocusRepository locusRepository;

	private ChromosomeRepository chromosomeRepository;

	private DiseaseRepository diseaseRepository;

	public ChromosomeRepositoryService(ChromosomeRepository chromosomeRepository, LocusRepository locusRepository,
			DiseaseRepository diseaseRepository) {
		this.chromosomeRepository = chromosomeRepository;
		this.locusRepository = locusRepository;
		this.diseaseRepository = diseaseRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chromosome> findAll() {
		return chromosomeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chromosome> findAllWithLoci() {
		return chromosomeRepository.findAllWithLoci();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Chromosome> findById(Integer id) {
		return chromosomeRepository.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Chromosome findByName(String name) {
		return chromosomeRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Locus> findAllLoci() {
		return chromosomeRepository.findAllLoci();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Chromosome> findAll(Pageable pageable) {
		return chromosomeRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Chromosome> findAllWithLociAndDisease() {
		return chromosomeRepository.findAllWithLociAndDisease();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Locus> findAllLociByChromosome(Integer id, String lang) {
		return locusRepository.findAllLociByChromosome(id, lang);
	}

	@Override
	@Transactional(readOnly = true)
	public Locus findLocusById(Integer id) {
		return locusRepository.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true)
	public Locus findLocusByName(String name) {
		return locusRepository.findByName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Locus findLocusByName(String name, String lang) {
		return locusRepository.findByName(name, lang);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Chromosome> findAll(Pageable pageable, String lang) {
		return chromosomeRepository.findAll(pageable, lang);
	}

	@Override
	@Transactional
	public List<Chromosome> findAll(String lang) {
		return chromosomeRepository.findAll(lang);
	}

	@Override
	@Transactional(readOnly = true)
	public Chromosome findById(Integer id, String lang) {
		return chromosomeRepository.findById(id, lang);
	}

	@Transactional
	@Override
	public Chromosome findChromosomeByLocusName(String name, String lang) {
		return chromosomeRepository.findChromosomeByLocusName(name, lang);
	}

	@Override
	public Chromosome saveChromosome(Chromosome chromosome) {
		return chromosomeRepository.save(chromosome);
	}

	@Transactional
	@Override
	public Disease findDiseaseByLocusName(String name, String lang) {
		return diseaseRepository.findDiseaseByLocusName(name, lang);
	}

	@Override
	public Locus saveLocus(Locus locus) {
		return locusRepository.save(locus);
	}

	@Override
	public Disease saveDisease(Disease disease) {
		return diseaseRepository.save(disease);
	}

}
