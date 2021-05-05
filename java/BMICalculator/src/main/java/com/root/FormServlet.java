package com.root;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FormServlet
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 * https://www.baeldung.com/intro-to-servlets
 */
@WebServlet("/calculateServlet")
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public FormServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String height = request.getParameter("height");
        String weight = request.getParameter("weight") ;

        try {
            double bmi = calculateBMI(
              Double.parseDouble(weight), 
              Double.parseDouble(height));
            
            request.setAttribute("bmi", bmi);
            response.setHeader("Test", "Success");
            response.setHeader("BMI", String.valueOf(bmi));

            RequestDispatcher dispatcher 
              = request.getRequestDispatcher("index.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("index.jsp");
        }
	}

	private Double calculateBMI(Double weight, Double height) {
        return weight / (height * height);
    }
}
