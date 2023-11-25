package com.kseb;
import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/WorkAllocation")
public class WorkAllocationServlet extends HttpServlet {
    private final static String INSERT_QUERY = "insert into work_allocation_details(date,complaint_id,lineman) values(?,?,?)";
    private final static String SELECT_QUERY = "select date, complaint_id, lineman from work_allocation_details";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String date = req.getParameter("date");
        String complaintNo = req.getParameter("complaintNo");
        String lineMan = req.getParameter("lineMan");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///kseb", "root", "password");
             PreparedStatement insertPs = con.prepareStatement(INSERT_QUERY);
             PreparedStatement selectPs = con.prepareStatement(SELECT_QUERY)) {

            // Insert the data into the database
            insertPs.setString(1, date);
            insertPs.setString(2, complaintNo);
            insertPs.setString(3, lineMan);
            int insertCount = insertPs.executeUpdate();

            if (insertCount == 1) {
                // Retrieve the inserted data from the database
                ResultSet resultSet = selectPs.executeQuery();

                out.println("<h2>Inserted Data</h2>");
                out.println("<table border='1'>");
                out.println("<tr><th>Date</th><th>Complaint No</th><th>Line Man</th></tr>");

                while (resultSet.next()) {
                    out.println("<tr>");
                    out.println("<td>" + resultSet.getString("date") + "</td>");
                    out.println("<td>" + resultSet.getString("complaint_id") + "</td>");
                    out.println("<td>" + resultSet.getString("lineman") + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            } else {
                out.println("<p>No data inserted.</p>");
            }

        } catch (SQLException se) {
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
