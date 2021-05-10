package com.sd.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.sd.mvc.model.Employee;
import com.sd.mvc.model.EmployeeDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class EmployeeRegisterServlet extends HttpServlet {
	protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		String firstName = request.getParameter("firstName"); //get employee first name from the request
		String lastName = request.getParameter("lastName"); //get employee last name from the request
		
		//create an instance of employee from the request parameters
		//id will be assigned inside the dao
		Employee emp = new Employee ();
		emp.setFirstName(firstName);
		emp.setLastName(lastName);
		
		EmployeeDao empDao = new EmployeeDao(); //get employee dao
		try {
			emp = empDao.registerEmployee(emp); //the returned emp has id set, will be used by employee details jsp
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("emp", emp); //set employee as a request attribute to be accessed in the jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("EmployeeDetails.jsp"); //forward request to employee details jsp
		dispatcher.forward(request, response);
	}
}
