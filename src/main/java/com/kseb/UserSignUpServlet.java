package com.kseb;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/signup")
public class UserSignUpServlet extends HttpServlet {
	
	private  final static String query="insert into user_login_details(consumer_number,contact,password) values(?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw= resp.getWriter();
		resp.setContentType("text/html");
		
		String consumerNo = req.getParameter("consumerno");
		String contact=req.getParameter("contact");
		String password=req.getParameter("pass");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection con= DriverManager.getConnection("jdbc:mysql:///kseb","root","password");
				PreparedStatement ps= con.prepareStatement(query); ){
			ps.setString(1, consumerNo);
			ps.setString(2, contact);
			ps.setString(3, password);
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<script>alert('Signup done  successfully');</script>");

			}else {
				pw.println("<script>alert('Can't Sign Up ');</script>");
 
			}
			
		} catch (SQLException se) {
			pw.println("<h2>"+se.getMessage()+"<h2>");
			se.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req,resp);
	}
	
}
