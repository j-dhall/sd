package edu.ds.ms.retail.catalog.entity;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	BigInteger id;
	
	@Column(name = "name")
	@JsonProperty("name")
	String name;
	
	@Column(name = "description")
	@JsonProperty("description")
	String description;
	
	@Column(name = "image_path")
	@JsonProperty("image_path")
	String imagePath;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonProperty("category_id")
	Category category;
	
	@ManyToOne
	@JoinColumn(name = "sub_category_id")
	@JsonProperty("sub_category_id")
	SubCategory subCategory;
}
