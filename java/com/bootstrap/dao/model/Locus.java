package com.bootstrap.dao.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotEmpty;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "locus")
@NamedQueries({ @NamedQuery(name = "Locus.findAllLoci", query = "select l from Chromosome c JOIN c.loci l") })
@SqlResultSetMapping(name = "locusResult", entities = @EntityResult(entityClass = Locus.class))
public class Locus implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL_LOCI = "Locus.findAllLoci";

	@JsonIgnore
	@Version
	@Column(name = "version")
	private Integer version;

	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "{locus.new.not.empty.error}")
	@Column
	private String name;

	@NotEmpty(message = "{locus.new.not.empty.fname.error}")
	@Column(name = "full_name")
	private String fullName;

	@NotEmpty(message = "{locus.new.not.empty.pos.error}")
	@Column
	private String position;

	@NotEmpty(message = "{locus.new.not.empty.role.error}")
	@JsonIgnore
	@Column(name = "bio_role", columnDefinition = "text")
	@Type(type = "text")
	private String bioRole;

	@NotEmpty(message = "{locus.new.not.empty.lang.error}")
	@JsonIgnore
	@Column
	private String lang;

	@Column
	private String link;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "chrom_id")
	private Chromosome chromosome;

	@OneToOne(mappedBy = "locus", cascade = CascadeType.MERGE)
	private Disease disease;

	public Locus() {

	}

	public Locus(String name, String fullName, String position, String bioRole, Chromosome chromosome) {
		this.name = name;
		this.fullName = fullName;
		this.position = position;
		this.bioRole = bioRole;
		this.chromosome = chromosome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBioRole() {
		return bioRole;
	}

	public void setBioRole(String bioRole) {
		this.bioRole = bioRole;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Chromosome getChromosome() {
		return chromosome;
	}

	public void setChromosome(Chromosome chromosome) {
		this.chromosome = chromosome;
	}

	@JsonGetter
	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "Locus [name=" + name + ", fullName=" + fullName + ", position=" + position + ", bioRole=" + bioRole
				+ "]";
	}

}
