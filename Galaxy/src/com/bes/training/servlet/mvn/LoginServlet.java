package com.bes.training.servlet.mvn;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		String name = req.getParameter("name");
		String id = req.getParameter("userID");
		LoginModel lm = new LoginModel(name, id);
		boolean isMember = lm.verifyLogin();
		req.setAttribute("verify", isMember);
		RequestDispatcher dispatcher = req.getRequestDispatcher("verify.jsp");
		dispatcher.forward(req, resp);
	}

}
