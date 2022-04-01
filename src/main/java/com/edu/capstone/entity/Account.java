package com.edu.capstone.entity;

import java.time.LocalDateTime;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
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
@EqualsAndHashCode(exclude = {"schedules", "classes"})
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

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = { CascadeType.MERGE })
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Role> roles = new HashSet<>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "account", cascade = { CascadeType.MERGE })
	private Set<Image> images = new HashSet<>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE })
	@JoinColumn(name = "specialization_id", referencedColumnName = "id", insertable = true, updatable = true)
	private Specialization specialization;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(cascade = (CascadeType.MERGE))
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Set<Classs> classes = new HashSet<>();

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "teacher", cascade = { CascadeType.MERGE })
	private Set<Schedule> schedules = new HashSet<>();
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany(mappedBy = "teachers", cascade = {CascadeType.REMOVE})
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
		if (classes == null) classes = new HashSet<>();
		classes.remove(classs);
		classs.getStudents().remove(this);
	}
}
