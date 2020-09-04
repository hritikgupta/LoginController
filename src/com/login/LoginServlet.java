package com.login;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.login.dao.LoginDao;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
       
    public LoginServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		
		LoginDao dao = new LoginDao();
		
		if(dao.checkCredentials(email,  pass)){			
			HttpSession session = request.getSession();
			session.setAttribute("email", email);
			
			response.setContentType("text/html;charset=UTF-8");			
            response.getWriter().write("True");
		}
		else{	
			String greetings = "Invalid Username/Password";
            response.setContentType("text/plain");
            response.getWriter().write(greetings);
		}
		
		
	}

}
