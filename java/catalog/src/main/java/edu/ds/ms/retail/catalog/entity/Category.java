package edu.ds.ms.retail.catalog.entity;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	BigInteger id;
	
	@Column(name = "name")
	@JsonProperty("name")
	String name;
	
	@OneToMany(mappedBy = "category") //objSubCategory.category
	Set<SubCategory> subCategories = new HashSet<SubCategory>();
	
	@OneToMany(mappedBy = "category") //objProduct.category
	Set<Product> products = new HashSet<Product>();
}
