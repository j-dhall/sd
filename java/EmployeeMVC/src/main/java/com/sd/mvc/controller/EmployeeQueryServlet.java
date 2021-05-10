package com.sd.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import com.sd.mvc.model.Employee;
import com.sd.mvc.model.EmployeeDao;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/query")
public class EmployeeQueryServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = null;
		
		//a post /register from Advanced REST Client will set session attribute
		//but a get from this IDE tab will fetch null, since they are different sessions
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		
		//the servlet context attribute set by a post from Advanced REST Client
		// is available in a get session from IDE tab
		ServletContext ctx = request.getSession().getServletContext();
		String globalName = (String) ctx.getAttribute("nameGlobal");
		
		//since we are now not including link.html from here, (instead including from jsp directly), we do not need PrintWrite and out.close() to avoid java.lang.IllegalStateException 
		//link.html will not show up on top of employee details jsp, better include from the jsp
		//PrintWriter out = response.getWriter(); //get response writer //to avoid java.lang.IllegalStateException
		//response.setContentType("text/html"); //response: Content-Type
		//dispatcher = request.getRequestDispatcher("WEB-INF/link.html");
		//dispatcher.include(request, response); //add the link html at the top of the response

		int id = Integer.valueOf(request.getParameter("id")); //get employee id from the request
		EmployeeDao empDao = new EmployeeDao(); //get employee dao
		Employee emp = empDao.getEmployee(id); //query employee from dao
		request.setAttribute("emp", emp); //set employee as a request attribute to be accessed in the jsp
		//System.out.println(emp.toString());
		
		//whether we use PrintWriter or not
		//out.print("Employee Details:\n"); //to avoid java.lang.IllegalStateException, we need to fetch and close PrintWriter.
		//Also, what we write on PrintWriter ("Employee Details:\n") doesn't show up on the forwarded page 
		
		//RequestDispatcher
		dispatcher = request.getRequestDispatcher("EmployeeDetails.jsp"); //forward request to employee details jsp
		dispatcher.forward(request, response);
		
		//out.close(); //close response writer //to avoid java.lang.IllegalStateException
	}
}
