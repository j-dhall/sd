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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Table(name = "sub_category")
@Data
public class SubCategory {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;

	@Column(name = "name")
	@JsonProperty("name")
	String name;
	
	@OneToMany(mappedBy = "subCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //objProduct.subCategory
	//@JsonManagedReference
	@JsonIgnore
	Set<Product> products = new HashSet<Product>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id")
	@JsonProperty("category_id")
	//@JsonBackReference
	@JsonIgnore
	Category category;
	
	public void addProduct(Product product) {
		product.setSubCategory(this);
		products.add(product);
	}
	
	//https://www.programmersought.com/article/1673633146/
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof SubCategory)) return false;
		if(!super.equals(o)) return false;
		
		SubCategory subCat = (SubCategory) o;
		
		if(!getId().equals(subCat.getId())) return false;
		return getName().equals(subCat.getName());
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
