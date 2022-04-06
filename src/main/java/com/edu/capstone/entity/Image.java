//package com.edu.capstone.entity;
//
//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//import org.hibernate.annotations.OnDelete;
//import org.hibernate.annotations.OnDeleteAction;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
///**
// * @author NhatHH Date: Jan 30, 2022
// */
//@Entity
//@Table(name = "[Image_Gallery]")
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class Image {
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	private int id;
//	@Column(name = "image_path")
//	private String imagePath;
//	
//	@ManyToOne(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY)
//	@JoinColumn(name = "account_id", referencedColumnName = "id", insertable = true, updatable = true)
//	@OnDelete(action = OnDeleteAction.CASCADE)
//	private Account account;
//
//}
