package com.sd.mvc.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeDaoTest {

	Employee emp1, emp2;
	EmployeeDao empDao;
	
	@BeforeEach
	void setUp() throws Exception {
		//create employees
		emp1 = new Employee ();
		emp1.setFirstName("Keysha");
		emp1.setLastName("Sindri");
		
		emp2 = new Employee ();
		emp2.setFirstName("Anaya");
		emp2.setLastName("Arora");
		
		//create dao
		empDao = new EmployeeDao();
	}

	@Test
	void testRegisterEmployee() {
		try {
			empDao.registerEmployee(emp1);
			empDao.registerEmployee(emp2);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
