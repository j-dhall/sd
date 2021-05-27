package edu.ds.ms.retail.catalog.entity;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "sub_category")
@Data
public class SubCategory {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	BigInteger id;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonProperty("category_id")
	Category category;
	
	@Column(name = "name")
	@JsonProperty("name")
	String name;
	
	@OneToMany(mappedBy = "subCategory") //objProduct.subCategory
	Set<Product> products = new HashSet<Product>();
}
