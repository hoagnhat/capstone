package com.edu.capstone.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH Date: Feb 13, 2022
 */
@Entity
@Table(name = "[CLASS]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "schedules")
public class Classs {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "semester")
	private int semester;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "specialization_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Specialization specialization;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { CascadeType.MERGE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Subject> subjects = new HashSet<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "classs", cascade = { CascadeType.MERGE })
	private Set<Schedule> schedules = new HashSet<>();
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "classes", cascade = (CascadeType.MERGE))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Account> students = new HashSet<>();

}
