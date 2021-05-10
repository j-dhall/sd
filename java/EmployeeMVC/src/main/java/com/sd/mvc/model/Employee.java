package com.sd.mvc.model;

import java.io.Serializable;

public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	private String firstName;
    private String lastName;
    
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
