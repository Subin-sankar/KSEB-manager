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

@WebServlet("/submit")
public class ComplaintRegServlet extends HttpServlet{
private  final static String query="insert into complaint_details(date,consumer_number,consumer_name,location,post_no,complaint_description,complaint_status) values(?,?,?,?,?,?,?)";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw= resp.getWriter();
		resp.setContentType("text/html");
		
		String date=req.getParameter("date");
		String ConsumerNumber = req.getParameter("consumerNumber");
		String consumerNmae=req.getParameter("consumerName");
		String location=req.getParameter("location");
		String postNumber=req.getParameter("postNumber");
		String complaintDescription=req.getParameter("complaintDescription");
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection con= DriverManager.getConnection("jdbc:mysql:///kseb","root","password");
				PreparedStatement ps= con.prepareStatement(query); ){
			ps.setString(1, date);
			ps.setString(2, ConsumerNumber);
			ps.setString(3, consumerNmae);
			ps.setString(4, location);
			ps.setString(5, postNumber);
			ps.setString(6, complaintDescription);
			ps.setString(7, "Pending");
			
			
			
			
			int count = ps.executeUpdate();
			if(count==1) {
				pw.println("<script>alert('Complaint reported successfully');</script>");
			}else {
				pw.println("<script>alert('Couldn't Connect');</script>");
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
