package com.edu.capstone.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@EqualsAndHashCode(exclude = {"schedules", "students"})
public class Classs {

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "semester")
	private int semester;

	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "specialization_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Specialization specialization;
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@OneToMany(mappedBy = "classs", cascade = { CascadeType.MERGE })
	private Set<Schedule> schedules = new HashSet<>();
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToMany(mappedBy = "classes", cascade = (CascadeType.MERGE))
	private Set<Account> students = new HashSet<>();
	
	public void addStudent(Account teacher) {
		students.add(teacher);
		teacher.getClasses().add(this);
	}

	public void removeStudent(Account student) {
		students.remove(student);
		student.getClasses().remove(this);
	}
	
	public void addSchedule(Schedule schedule) {
		if (schedules == null) schedules = new HashSet<>();
		schedules.add(schedule);
		schedule.setClasss(this);
	}
	
	public void removeSchedule(Schedule schedule) {
		if (schedules == null) schedules = new HashSet<>();
		schedules.remove(schedule);
		schedule.setClasss(null);
	}

}
