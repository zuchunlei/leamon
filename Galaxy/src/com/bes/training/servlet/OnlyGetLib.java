package com.bes.training.servlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class OnlyGetLib extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		// out.println("Beer Selection Advice<br>");
		//String c = req.getParameter("color");
		Enumeration<?> elem = (Enumeration<?>) getServletConfig()
				.getInitParameterNames();
		while (elem.hasMoreElements()) {
			out.println("<br>" + elem.nextElement() + "<br>");
		}
		out.println("param is"
				+ getServletContext().getInitParameter("adminEmail") + "<br>");
		// out.println("<br>Got beer color" + c);
		//BeerExpert be = new BeerExpert();
		//List result = be.getBrands(c);
		// Iterator it = result.iterator();
		// while(it.hasNext()){
		// out.print("<br>try: "+ it.next());
		// }
		//req.setAttribute("styles", result);

	//	RequestDispatcher view = req.getRequestDispatcher("result.jsp");
	//	view.forward(req, resp);
    }
}