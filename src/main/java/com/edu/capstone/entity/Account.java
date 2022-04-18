package com.edu.capstone.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.edu.capstone.common.constant.RegexConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author NhatHH Date: Jan 16, 2022
 */
@Entity
@Table(name = "[ACCOUNT]")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@EqualsAndHashCode(exclude = { "schedules", "classes" })
public class Account {

	@Id
	@Column(name = "id")
	private String id;
	@Email
	@Column(name = "email", unique = true)
	private String email; // Địa chỉ email dùng để đăng nhập hệ thống
	@Size(min = RegexConstant.PASSWORD_LENGTH)
	@Column(name = "password")
	private String password; // Mật khẩu
	@Column(name = "is_actived")
	@ColumnDefault("0")
	private int isActived; // Kiểm soát tính active của tài khoản (1: actived, 0: not actived)
	@Column(name = "verify_token")
	private String verifyToken; // Token để xác thực tài khoản
	@Column(name = "token_expired_date")
	private LocalDateTime tokenExpiredDate;
	@Column(name = "status")
	private String status;
	@ManyToMany(cascade = { CascadeType.MERGE },fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Fetch(value = FetchMode.SUBSELECT)
	private Set<Role> roles = new HashSet<>();

//	@OneToMany(mappedBy = "account", cascade = { CascadeType.MERGE },fetch = FetchType.LAZY)
//	private Set<Image> images = new HashSet<>();

	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "specialization_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Specialization specialization;

	@ManyToMany(cascade = (CascadeType.MERGE), fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@Fetch(value = FetchMode.SELECT)
	@BatchSize(size = 50)
	private Set<Classs> classes = new HashSet<>();

	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@BatchSize(size = 100)
	private Set<Schedule> schedules = new HashSet<>();

	@JsonIgnore
	@ManyToMany(mappedBy = "teachers", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY)
	@Fetch(value = FetchMode.SELECT)
	@BatchSize(size = 50)
	private Set<Subject> teachSubjects = new HashSet<>();

	public void addSubjects(Subject subject) {
		if (teachSubjects == null) {
			teachSubjects = new HashSet<>();
		}
		teachSubjects.add(subject);
		subject.getTeachers().add(this);
	}

	public void removeSubjects(Subject subject) {
		if (teachSubjects == null) {
			teachSubjects = new HashSet<>();
		}
		teachSubjects.remove(subject);
		subject.getTeachers().remove(this);
	}

	public void addSchedule(Schedule schedule) {
		if (schedules == null) {
			schedules = new HashSet<>();
		}
		schedules.add(schedule);
		schedule.setTeacher(this);
	}

	public void removeSchedule(Schedule schedule) {
		if (schedules == null) {
			schedules = new HashSet<>();
		}
		schedules.remove(schedule);
		schedule.setTeacher(null);
	}

	public void removeClass(Classs classs) {
		if (classes == null)
			classes = new HashSet<>();
		classes.remove(classs);
		classs.getStudents().remove(this);
	}
}
