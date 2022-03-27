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
import javax.persistence.OneToMany;
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
 * @author NhatHH Date: Feb 13, 2022
 */
@Entity
@Table(name = "[SUBJECT]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"schedules", "specializations", "teachers"})
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "name")
	private String name;
	@Column(name = "subject_code")
	private String subjectCode;
	@Column(name = "semester")
	private int semester;

	@ToString.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Set<Specialization> specializations = new HashSet<>();

	@JsonIgnore
	@ToString.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "subject", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	private Set<Schedule> schedules = new HashSet<>();
	
	@ToString.Exclude
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { CascadeType.PERSIST })
	private Set<Account> teachers = new HashSet<>();
	
	public void addSchedule(Schedule schedule) {
		if (schedules == null) {
			schedules = new HashSet<>();
		}
		schedules.add(schedule);
		schedule.setSubject(this);
	}

	public void removeSchedule(Schedule schedule) {
		if (schedules == null) {
			schedules = new HashSet<>();
		}
		schedules.remove(schedule);
		schedule.setSubject(null);
	}
	
	public void addSpec(Specialization specialization) {
		if (specializations == null) {
			specializations = new HashSet<>();
		}
		specializations.add(specialization);
		specialization.getSubjects().add(this);
	}

	public void removeSpec(Specialization specialization) {
		if (specializations == null) {
			specializations = new HashSet<>();
		}
		specializations.remove(specialization);
		specialization.getSubjects().remove(this);
	}
	
	public void addTeacher(Account teacher) {
		if (teachers == null) {
			teachers = new HashSet<>();
		}
		teachers.add(teacher);
		teacher.getTeachSubjects().add(this);
	}

	public void removeTeacher(Account teacher) {
		if (teachers == null) {
			teachers = new HashSet<>();
		}
		teachers.remove(teacher);
		teacher.getTeachSubjects().remove(this);
	}
	
}
