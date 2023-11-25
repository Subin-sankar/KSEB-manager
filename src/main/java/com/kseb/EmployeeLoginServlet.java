package com.kseb;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class EmployeeLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String jdbcURL = "jdbc:mysql://localhost:3306/kseb";
        String dbUser = "root";
        String dbPassword = "password";

        String enteredEmail = request.getParameter("email");
        String enteredPassword = request.getParameter("pass");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            if (isAdmin(enteredEmail, enteredPassword)) {
                response.sendRedirect("ComplaintRegServlet");
            } else if (isEmployee(enteredEmail, enteredPassword, connection)) {
                response.sendRedirect("Employee_home.html");
            } else {
                out.println("<script>alert('Invalid credentials. Please try again.');"
                            + "window.location='login.html';</script>");
            }

            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('An error occurred. Please try again later.');"
                        + "window.location='login.html';</script>");
        }
    }

    private boolean isAdmin(String email, String password) {
        String adminEmail = "admin@example.com";
        String adminPassword = "admin123";

        // Check if password is not null before invoking equals
        return email.equals(adminEmail) && adminPassword.equals(password);
    }


    private boolean isEmployee(String email, String password, Connection connection) {
        return checkCredentials(email, password, "employee_details", connection);
    }

    private boolean checkCredentials(String email, String password, String tableName, Connection connection) {
        String query = "SELECT * FROM " + tableName + " WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

