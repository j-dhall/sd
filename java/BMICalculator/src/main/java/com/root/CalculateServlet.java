package com.root;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class FormServlet
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 */
/*
 * Servlet to displat BMI based on the vital statistics (height, weight, etc) got from the request.
 */
@WebServlet("/calculateServlet")
public class CalculateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public CalculateServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String height = request.getParameter("height");
        String weight = request.getParameter("weight") ;

        //Method 4: Session Tracking using HTTP Session
        String uName = null;
        HttpSession session = request.getSession(false); //get an existing session
        if (session != null) {
        	uName = (String) session.getAttribute("uname");
        }
        
        try {
            double bmi = calculateBMI(
              Double.parseDouble(weight), 
              Double.parseDouble(height));
            
            request.setAttribute("bmi", bmi);
            response.setHeader("Test", "Success");
            response.setHeader("BMI", String.valueOf(bmi));
            
    		response.setContentType("text/html"); //response: Content-Type
    		PrintWriter out = response.getWriter(); //response: writer
    		
    		String userName = request.getCookies()[0].getValue(); //get user's name from the cookie
    		
    		//prepare a form for response
    		out.print("The BMI of " + userName + " is " + String.valueOf(bmi)); //show BMI to the user.
    		out.print("<form action=\"index.html\" method=\"post\">  \r\n"
    				+ "<input type=\"submit\" value=\"Start Again\"/>  \r\n"
    				+ "</form>");
    		out.close();

//            RequestDispatcher dispatcher 
//              = request.getRequestDispatcher("index.jsp");
//            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("index.jsp");
        }
	}

	private Double calculateBMI(Double weight, Double height) {
        return weight / (height * height);
    }
}
