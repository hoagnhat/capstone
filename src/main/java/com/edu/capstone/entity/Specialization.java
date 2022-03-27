package com.edu.capstone.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author NhatHH
 * Date: Jan 31, 2022
 */
@Entity
@Table(name = "[SPECIALIZATION]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "subjects")
public class Specialization {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name", unique = true)
	private String name; // Tên chuyên ngành
	
	@JsonIgnore
	@ToString.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "specializations", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Subject> subjects = new HashSet<>();
	
	public void addSubjects(Subject subject) {
		if (subjects == null) {
			subjects = new HashSet<>();
		}
		subjects.add(subject);
		subject.getSpecializations().add(this);
	}

	public void removeSubjects(Subject subject) {
		if (subjects == null) {
			subjects = new HashSet<>();
		}
		subjects.remove(subject);
		subject.getSpecializations().remove(this);
	}
	
}
