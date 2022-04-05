package com.edu.capstone.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

import com.edu.capstone.entity.key.CSKey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CLASS_SUBJECTS")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubject {

	@EmbeddedId
	private CSKey key;
	private Date dateStart;
	private Date dateEnd;
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Account teacher;
	
	@LazyCollection(LazyCollectionOption.TRUE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "subject_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Subject subject;
	
}
