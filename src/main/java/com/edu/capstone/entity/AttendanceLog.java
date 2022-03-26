package com.edu.capstone.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH
 * Date: Feb 19, 2022
 */
@Entity
@Table(name = "[ATTENDANCE_LOG]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "student_id")
	private String studentId;
	@Column(name = "status")
	private String status;
	@Column(name = "slot_id")
	private int slotId;
	@Column(name = "description")
	private String description;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "slot_id", referencedColumnName = "id", insertable = false, updatable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Schedule schedule;
	
}
