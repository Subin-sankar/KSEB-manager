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

@WebServlet("/register")
public class EmployeeRegServlet extends HttpServlet {
	private final static String query = "insert into employee_details(user_type,firstname,lastname,age,address,contact_no,email) values(?,?,?,?,?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");

		String userType = req.getParameter("userType");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String age = req.getParameter("age");
		String address = req.getParameter("address");
		String contact = req.getParameter("contact");
		String email = req.getParameter("email");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection("jdbc:mysql:///kseb", "root", "password");
				PreparedStatement ps = con.prepareStatement(query);) {

			ps.setString(1, userType);
			ps.setString(2, firstName);
			ps.setString(3, lastName);
			ps.setString(4, age);
			ps.setString(5, address);
			ps.setString(6, contact);
			ps.setString(7, email);

			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<script>alert('Employee registered successfully');</script>");
			} else {
				pw.println("<script>alert('Can't Register');</script>");	
			}

		} catch (SQLException se) {
			pw.println("<h2>" + se.getMessage() + "<h2>");
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
