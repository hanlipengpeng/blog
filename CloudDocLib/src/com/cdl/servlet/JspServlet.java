package com.cdl.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JspServlet extends HttpServlet{
	public JspServlet(){
		super();
	}
	
	@Override
	public void init() throws ServletException {
		// put your code here
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String scope =request.getParameter("scope");
		String result =null;
		if(scope.equals("portalSession")){
			result = "zhangsan";
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(result);
		out.flush();
		out.close();
	}

	public void destroy(){
		super.destroy();// Just puts
	}
	
}
