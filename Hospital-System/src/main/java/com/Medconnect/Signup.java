package com.Medconnect;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/SignupUser")

public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public Signup() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String fullname = request.getParameter("fullname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String phone = request.getParameter("phone");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to DB
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/medconnect_db", "root", "prerna25");

            // Check if user already exists
            PreparedStatement psCheck = con.prepareStatement("SELECT * FROM users WHERE email = ?");
            psCheck.setString(1, email);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                out.println("Email already registered!");
            } else {
                // Insert new user
                PreparedStatement psInsert = con.prepareStatement(
                    "INSERT INTO users (fullname, email, password, phone) VALUES (?, ?, ?, ?)");
                psInsert.setString(1, fullname);
                psInsert.setString(2, email);
                psInsert.setString(3, password);
                psInsert.setString(4, phone);

                int i = psInsert.executeUpdate();
                if (i > 0) {
                    out.println("Account created successfully!");
                } else {
                    out.println("Error inserting user.");
                }
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Database error: " + e.getMessage());
        }
	}
}