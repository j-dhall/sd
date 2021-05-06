import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpRequest;
import java.rmi.ServerException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//get the response writer before calling request.getRequestDispatcher.include(), 
		// else it causes java.lang.IllegalStateException
		PrintWriter out = response.getWriter();
		response.setContentType("text/html"); //response: Content-Type
		request.getRequestDispatcher("link.html").include(request, response); //add the link html at the top of the next servlet
		
		String userName = request.getParameter("userName"); //get username from request
		String userPassword = request.getParameter("userPassword"); //get password from request
		if (userPassword.equals("tiger")) { //verify password
			Cookie ckUserName = new Cookie("userName", userName); //create a cookie for session tracking
			response.addCookie(ckUserName); //add the cookie to the request
			out.print("You are successfully logged in!\r\nWelcome, " + userName);
		} else {
			out.print("Sorry, username or password error!");
			request.getRequestDispatcher("login.html").include(request, response); //add a login html at the bottom of the next servlet
		}
		out.close(); //close the response
	}
}
