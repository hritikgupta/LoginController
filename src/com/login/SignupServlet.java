package com.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.dao.LoginDao;
import com.login.dao.SignupDao;

/**
 * Servlet implementation class SignupServlet
 */
@WebServlet("/signup")
public class SignupServlet extends HttpServlet {
       
    public SignupServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//String body = request.getReader() json conversion from body
		
		String uname = request.getParameter("uname");
		String pass = request.getParameter("pass");
		String email = request.getParameter("email");
				
		SignupDao dao = new SignupDao();
		
		int rowsAffected = dao.createUser(uname, email, pass);
		
		response.setContentType("text/html;charset=UTF-8");
		if (rowsAffected == 0){
	        response.getWriter().write("Some error occurred. Try again with a different email!");
		} else{
			response.getWriter().write("True");
		}
	}

}
