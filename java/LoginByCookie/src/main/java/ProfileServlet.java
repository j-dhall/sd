import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/profileServlet")
public class ProfileServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//get the response writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); //response: Content-Type

		request.getRequestDispatcher("link.html").include(request, response); //add the link html at the top of the next servlet
		
		//check if the user has logged in
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies[0].getName().equals("userName")) {
			//yes, the user is logged in
			out.print("Welcome to Profile");
			out.print("Welcome, " + cookies[0].getValue());
		} else {
			//no, the user is not logged in
			out.print("Please login first");
		}
		out.close(); //close the response
	}
}
