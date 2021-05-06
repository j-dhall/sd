package com.root;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * Servlet to display the form asking vital statistics (weight, height, etc)
 * It also greets the user with name got from the request.
 */
@WebServlet("/formServlet")
public class FormServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html"); //response: Content-Type
		PrintWriter out = response.getWriter(); //response: writer
		
		String userName = request.getParameter("userName"); //get the user's name
		Cookie ckUserName = new Cookie("userName", userName); //create a cookie for session tracking
		response.addCookie(ckUserName); //add the cookie to the request
		
		//prepare a form for response
		out.print("Hello " + userName + "!"); //greet the user
		out.print("<form action=\"calculateServlet\" method=\"post\">  \r\n"
				+ "Height:<input type=\"text\" name=\"height\"/><br/>  \r\n" //ask for height
				+ "Weight:<input type=\"text\" name=\"weight\"/><br/>  \r\n" //ask for name
				+ "<input type=\"submit\" value=\"go\"/>  \r\n"
				+ "</form>");
		out.close();
	}
}
