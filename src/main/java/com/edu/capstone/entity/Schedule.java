package com.edu.capstone.entity;

import java.util.Date;
import java.util.HashSet;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH
 * Date: Feb 19, 2022
 */
@Entity
@Table(name = "[SCHEDULE]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
public class Schedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "time_start")
	private Date timeStart;
	@Column(name = "time_end")
	private Date timeEnd;
	@Column(name = "room")
	private int room;
	@Column(name = "status")
	private String status;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = true, updatable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Account teacher;
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = true, updatable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Subject subject;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "class_id", referencedColumnName = "id", insertable = true, updatable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Classs classs;
	
}
