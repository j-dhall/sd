package com.sd.mt;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/msg")
public class MTSDSevlet extends HttpServlet {

	private String instanceMessage; //BUGGY BUGGY BUGGY: servlet should not have instance data since it is multithreaded
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = req.getParameter("message"); //get message from the request parameter
		instanceMessage = req.getParameter("message"); //get message from the request parameter and store it in the instance variable (this is not thread-safe) BUGGY BUGGY BUGGY
		PrintWriter out = resp.getWriter(); //get response writer
		out.print("Safe Text: '" + message + "'. Unsafe Text: '" + instanceMessage + "'."); //write safe and unsafe text to the output
		out.close(); //close the response output
	}
	
}
