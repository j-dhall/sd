package edu.ds.ms.retail.catalog.entity;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "category")
@Data
public class Category {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	
	@Column(name = "name")
	@JsonProperty("name")
	String name;

	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //objProduct.category
	//@JsonManagedReference
	@JsonIgnore
	Set<Product> products = new HashSet<Product>();
	
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //objSubCategory.category
	//@JsonManagedReference
	//@JsonIgnore
	Set<SubCategory> subCategories = new HashSet<SubCategory>();

	public void addSubCategory(SubCategory subCategory) {
		subCategory.setCategory(this);
		subCategories.add(subCategory);
	}
	
	public void addProduct(Product product) {
		product.setCategory(this);
		products.add(product);
	}
	
	//https://www.programmersought.com/article/1673633146/
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Category)) return false;
		if(!super.equals(o)) return false;
		
		Category cat = (Category) o;
		
		if(!getId().equals(cat.getId())) return false;
		return getName().equals(cat.getName());
	}
	
	//https://www.programmersought.com/article/1673633146/
	@Override
	public int hashCode() {
		int result = super.hashCode();
		//result = 31*result + getId().hashCode();
		result = 31*result + getName().hashCode();
		return result;
	}
}
