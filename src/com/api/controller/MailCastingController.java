package com.api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.api.model.UserModel;
import com.api.service.RegisterUser;
import com.api.service.SendMessage;
import com.api.service.VerifyLogin;


@WebServlet("/")
public class MailCastingController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

			doGet(request,response);
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
			String action =request.getServletPath();
			
			switch(action) {
			case "/register":
				registerUser(request,response);
				break;
			case "/login":
				loginUser(request,response);
				break;
			case "/logout":
				logOut(request,response);
				break;
			case "/composeEmail":
				composeEmail(request,response);
				break;
			case "/validate":
				validatePassword(request,response);
				break;
			default:
					showHome(request,response);
					break;
			}

			
	}
	
	private void validatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id=Integer.parseInt(request.getParameter("id"));
		
		String password=request.getParameter("password");
		System.out.println(VerifyLogin.validatePassword(id,password));
	
		if(VerifyLogin.validatePassword(id,password)) {
			request.setAttribute("success", "success");
			RequestDispatcher rd=request.getRequestDispatcher("myProfile.jsp");
			rd.include(request, response);
		}
		else {
			request.setAttribute("success", "Invalid");
			RequestDispatcher rd=request.getRequestDispatcher("myProfile.jsp");
			rd.include(request, response);
		}
		
		
	}
	private void showHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);	
	}
	private void composeEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter  out=response.getWriter()) {
		HttpSession session=request.getSession(false);
		
		
		String sender=(String)session.getAttribute("username");
		
		
		String reciever=request.getParameter("reciever_id");
		String msg=request.getParameter("message");
		String sub=request.getParameter("subject");
		int i=SendMessage.sendMsg(sender,reciever,sub,msg);
		if(i>0){
			
	            
			out.print("Message Sucessfully Sent");
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		}
		else{
			out.print("Sorry,Message was not sent");
			RequestDispatcher rd=request.getRequestDispatcher("compose.jsp");
			rd.include(request, response);
		}
		
		}
		
	}
	private void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		HttpSession session=request.getSession(false);
		session.invalidate();
		request.setAttribute("logout","You have been sucessfully logged out");
		RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
		rd.forward(request,response);
		
	}
	private void loginUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter  out=response.getWriter()) {
		
		
		String uname=request.getParameter("email");
		String password=request.getParameter("password");
		boolean status=VerifyLogin.checkLogin(uname,password);
		if(status==true){
			HttpSession session=request.getSession();
			session.setAttribute("username",uname);
			
		
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			rd.include(request, response);
		}
		else{
			String Error="Please check your EMail and Password";
			request.setAttribute("Error", Error);
			
			RequestDispatcher rd=request.getRequestDispatcher("index.jsp");
			rd.include(request, response);
			
		}
		}
		
	}

	
	private void registerUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		try (PrintWriter  out=response.getWriter()) {
		String email=request.getParameter("email");
		String password=request.getParameter("password");
		String name=request.getParameter("name");
		String gender=request.getParameter("gender");
		
		String contact=request.getParameter("contact");
		
		String country=request.getParameter("country");
		
		UserModel user=new UserModel(email, password, name, contact, gender, country);
		
		int status=RegisterUser.register(user);
		if(status>0){
			String register= "You are Successfully registered";
			request.setAttribute("register",register);
			RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");
			rd.include(request, response);
		}
		else{
			String registererror="Sorry,Registration failed. please try later";
			request.setAttribute("registererror",registererror);
			RequestDispatcher rd=request.getRequestDispatcher("Register.jsp");
			rd.include(request, response);
		}
		
	}
	}
		
	}
	


