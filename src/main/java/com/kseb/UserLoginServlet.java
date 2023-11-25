package com.kseb;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginServlet")
public class UserLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/kseb";
	final String user = "root";
	final String password = "Mysql@Pass";
	
	Connection conn = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	  public UserLoginServlet() {
	        try {
	            // Load the JDBC driver
	            Class.forName(driver);
	            // Create a connection
	            conn = DriverManager.getConnection(url, user, password);
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace(); // Handle the exception appropriately
	        }
	    }
	  private void logUnexpectedDepartment(int departmentId) {
		    // You can log the unexpected department to a file or a logging system
		    System.err.println("Unexpected department encountered: " + departmentId);
		}
	  private boolean isFixedAdminUser(String email, String password) {
		    // Define fixed email and password for the admin
		    String adminEmail = "admin@123.com";
		    String adminPassword = "adminPassword"; // You should use a strong and secure password here

		    // Check if the provided email and password match the fixed admin credentials
		    return adminEmail.equals(email) && adminPassword.equals(password);
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

     // Check if the user is the fixed admin user
        if (isFixedAdminUser(email, password)) {
            // Admin user, redirect to AdminHomePage
            HttpSession session = request.getSession();
            session.setAttribute("designationId", 1); // Assuming Admin's designation_id is 1
            session.setAttribute("departmentId", 1); // Assuming Admin's department_id is 1
            response.sendRedirect("AdminHomePage");
            return;
        }

        // Proceed to check the database for other users
        try  {
            // Query the database to retrieve user details
             pst = conn.prepareStatement(
                    "SELECT designation_id, department_id FROM staff_login_details WHERE staff_personal_id = ? AND staff_password = ?");
            pst.setString(1, email);
            pst.setString(2, password);
            rs = pst.executeQuery();

            if (rs.next()) {
                int designationId = rs.getInt("designation_id");
                int departmentId = rs.getInt("department_id");

                // Store user details in session
                HttpSession session = request.getSession();
                session.setAttribute("designationId", designationId);
                session.setAttribute("departmentId", departmentId);

             // Redirect based on department
                switch (departmentId) {
                    case 1: // Admin
                        response.sendRedirect("Admin_home.html");
                        break;
                    case 2: // LineMan
                        response.sendRedirect("LineManHomePage");
                        break;
                    case 3: // Engineer
                        response.sendRedirect("EngineerHomePage");
                        break;
                    case 4: // Material
                        response.sendRedirect("MaterialHomePage");
                        break;
                    default:
                        // Handle unexpected department (optional)
                    	 logUnexpectedDepartment(departmentId); // Log the unexpected department
                         response.sendRedirect("ErrorPage"); // Redirect to a generic error page
                         break;
                      
                }
            } else {
                // Invalid login
                response.sendRedirect("Login?error=invalid");
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }
}


