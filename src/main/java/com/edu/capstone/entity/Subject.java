package com.edu.capstone.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH Date: Feb 13, 2022
 */
@Entity
@Table(name = "[SUBJECT]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "schedules")
public class Subject {

	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "semester")
	private int semester;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "specialization_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Specialization specialization;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "subject", cascade = { CascadeType.MERGE })
	private Set<Schedule> schedules = new HashSet<>();
	
}
