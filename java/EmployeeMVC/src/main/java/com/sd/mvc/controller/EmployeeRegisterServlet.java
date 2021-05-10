package com.sd.mvc.controller;

import com.sd.mvc.model.Employee;
import com.sd.mvc.model.EmployeeDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class EmployeeRegisterServlet extends HttpServlet {
	protected void doPost (HttpServletRequest request, HttpServletResponse response) {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		
		Employee emp = new Employee ();
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		
		EmployeeDao empDao = new EmployeeDao();
		try {
			empDao.registerEmployee(emp);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
