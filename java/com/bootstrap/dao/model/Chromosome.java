package com.bootstrap.dao.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "chromosome")
public class Chromosome implements Serializable {

	public static final String FIND_ALL = "Chromosome.FIND_ALL";
	public static final String FIND_BY_ID = "Chromosome.FIND_BY_ID";
	public static final String FIND_ALL_WITH_LOCI = "Chromosome.FIND_ALL_WITH_LOCI";
	public static final String FIND_ALL_WITH_LOCI_AND_DISEASES = "Chromosome.FIND_ALL_WITH_LOCI_AND_DISEASES";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Version
	@Column(name = "version")
	private Integer version;

	@Column
	private String name;

	@Column
	private String size;

	@Column
	private Integer genes;

	@Column
	private String lang;

	@Column(columnDefinition = "text") // not fully portable
	@Type(type = "text")
	private String description;

	@OneToMany(mappedBy = "chromosome", cascade = CascadeType.MERGE, orphanRemoval = true)
	@OrderBy("name asc")
	private Set<Locus> loci = new HashSet<>();

	public Chromosome() {
	}

	public Chromosome(String name, String size, Integer genes, String description) {
		this.name = name;
		this.size = size;
		this.genes = genes;
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public Integer getGenes() {
		return genes;
	}


	public String getDescription() {
		return description;
	}

	public String getLang() {
		return lang;
	}

	public Set<Locus> getLoci() {
		return loci;
	}

	public void setLoci(Set<Locus> loci) {
		this.loci = loci;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void addLocus(Locus locus) {
		this.loci.add(locus);
		locus.setChromosome(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lang == null) ? 0 : lang.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lang == null) {
			if (other.lang != null)
				return false;
		} else if (!lang.equals(other.lang))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Chromosome [name=" + name + ", size=" + size + ", genesNumber=" + genes + ", description=" + description
				+ "]";
	}

}
