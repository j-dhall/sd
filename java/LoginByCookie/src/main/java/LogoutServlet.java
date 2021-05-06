import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/logoutServlet")
public class LogoutServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Cookie userName = new Cookie("userName", ""); //make a replacement cookie
		userName.setMaxAge(0); //expire the cookie
		//add the expired cookie so that session tracking ends
		//and the user effectively logs out
		response.addCookie(userName);
		
		//get the response writer
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); //response: Content-Type

		request.getRequestDispatcher("link.html").include(request, response); //add the link html at the top of the next servlet

		out.write("you are successfully logged out!");
		out.close(); //close the response
	}
}
