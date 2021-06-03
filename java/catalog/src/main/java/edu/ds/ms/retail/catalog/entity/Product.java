package edu.ds.ms.retail.catalog.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
	/*
	 * https://stackoverflow.com/questions/50659505/spring-boot-project-fails-to-run-because-of-schema-validation-missing-sequence
	 * 	Hit this issue and below is my searching results:
		If you use GenerationType.AUTO in your java bean, then by default hibernate uses hibernate_sequence for the sequence.
		So one option is to create this sequence in the DB by:
		create sequence <schema>.hibernate_sequence
		or you can use @GeneratedValue(strategy = GenerationType.IDENTITY) instead in your java bean source code, which does not require such sequence.
	 */
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	
	@Column(name = "name", nullable = false)
	@NotNull
	@JsonProperty("name")
	String name;
	
	@Column(name = "description")
	@JsonProperty("description")
	String description;
	
	@Column(name = "image_path")
	@JsonProperty("image_path")
	String imagePath;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonProperty("category_id")
	//@JsonBackReference
	Category category;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sub_category_id")
	@JsonProperty("sub_category_id")
	//@JsonBackReference
	SubCategory subCategory;
}
