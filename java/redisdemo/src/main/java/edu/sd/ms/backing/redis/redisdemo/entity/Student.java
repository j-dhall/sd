package edu.sd.ms.backing.redis.redisdemo.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "student")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	Integer id;
	
	@Column(name = "name", nullable = false, unique = true)
	@NotNull
	@JsonProperty("name")
	String name;
	
	@Column(name = "roll_number")
	@JsonProperty("rollNumber")
	String rollNumber;
}
