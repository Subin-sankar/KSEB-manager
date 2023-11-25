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

@WebServlet("/consRegistration")
public class ConsumerRegistration extends HttpServlet {
	private final static String query = "insert into consumer_details(consumer_number,consumer_name,meter_no,contact,address,house_no) values(?,?,?,?,?,?)";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");

		String consumerNumber = req.getParameter("consumerNo");
		String consumerName = req.getParameter("consumerName");
		String meterNo = req.getParameter("meterNo");
		String contact = req.getParameter("contact");
		String address = req.getParameter("address");
		String houseNo = req.getParameter("houseNo");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (Exception e) {
			e.printStackTrace();
		}
		try (Connection con = DriverManager.getConnection("jdbc:mysql:///kseb", "root", "password");
				PreparedStatement ps = con.prepareStatement(query);) {

			ps.setString(1, consumerNumber);
			ps.setString(2, consumerName);
			ps.setString(3, meterNo);
			ps.setString(4, contact);
			ps.setString(5, address);
			ps.setString(6, houseNo);
		

			int count = ps.executeUpdate();
			if (count == 1) {
				pw.println("<script>alert('Consumer registered successfully');</script>");
			} else {
				pw.println("<script>alert('Couldn't connect!');</script>");
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
