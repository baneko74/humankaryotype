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

import lombok.Data;

@Entity
@Table(name = "chromosome")
@Data
public class Chromosome implements Serializable {

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

	@Column
	private String imgurl;

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

}
