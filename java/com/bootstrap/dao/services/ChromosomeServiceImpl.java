package com.bootstrap.dao.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootstrap.dao.model.Chromosome;
import com.bootstrap.dao.model.Disease;
import com.bootstrap.dao.model.Locus;

@Service
@Repository
@Transactional
public class ChromosomeServiceImpl implements ChromosomeService {

	@Autowired
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public List<Chromosome> findAll() {
		return em.createNamedQuery(Chromosome.FIND_ALL, Chromosome.class).getResultList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<Chromosome> findAllWithLoci() {
		return em.createNamedQuery(Chromosome.FIND_ALL_WITH_LOCI, Chromosome.class).getResultList();
	}

	@Override
	public Optional<Chromosome> findById(Integer id) {
		return Optional.of(
				em.createNamedQuery(Chromosome.FIND_BY_ID, Chromosome.class).setParameter("id", id).getSingleResult());
	}

	@Override
	public Chromosome findByName(String name) {
		return em.createQuery("select distinct c from Chromosome c where c.name = :name", Chromosome.class)
				.setParameter("name", name).getSingleResult();
	}

	@Override
	public List<Locus> findAllLoci() {
		return em.createNamedQuery(Locus.FIND_ALL_LOCI, Locus.class).getResultList();
	}

	@Override
	public List<Chromosome> findAllWithLociAndDisease() {
		return em.createNamedQuery(Chromosome.FIND_ALL_WITH_LOCI_AND_DISEASES, Chromosome.class).getResultList();
	}

	@Override
	public Page<Chromosome> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Chromosome> findAll(String lang) {
		return em.createQuery("select distinct c from Chromosome c where c.lang = :lang", Chromosome.class)
				.setParameter("lang", lang).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Locus> findAllLociByChromosome(Integer id, String lang) {
		return (Set<Locus>) em
				.createQuery("select l from Chromosome c join c.loci l where c.id = :id and c.lang = :lang",
						Locus.class)
				.setParameter("id", id).setParameter("lang", lang).getResultList();
	}

	@Override
	public Locus findLocusById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locus findLocusByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Chromosome> findAll(Pageable pageable, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chromosome findById(Integer id, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locus findLocusByName(String name, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chromosome findChromosomeByLocusName(String name, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chromosome saveChromosome(Chromosome chromosome) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Disease findDiseaseByLocusName(String name, String lang) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locus saveLocus(Locus locus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Disease saveDisease(Disease disease) {
		// TODO Auto-generated method stub
		return null;
	}

}
