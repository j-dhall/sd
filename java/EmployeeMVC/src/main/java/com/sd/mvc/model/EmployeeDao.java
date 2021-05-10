package com.sd.mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmployeeDao {
	
	private static final String driver = "com.mysql.cj.jdbc.Driver"; //.cj is new (without .cj is deprecated)
	private static final String connStr = "jdbc:mysql://localhost:3306/emp";
	private static final String uname = "jeetu";
	private static final String pwd = "welcome";
	private static final String insertStmt = "INSERT INTO EMPLOYEE " +
			"(id, first_name, last_name) " +
			"VALUES (?, ?, ?)";
	private static int nextEmpId = 1;

	public int registerEmployee(Employee emp) throws ClassNotFoundException {
		Class.forName(driver); //load the driver
		int result = 0; //default to - 0 for SQL statements that return nothing 
		
		//DB Connection using JNDI
		Context initCtx;
		try {
			initCtx = new InitialContext();
			//https://www.baeldung.com/jndi
			// The first token of the string represents the global context, after that each string added represents the next sub-context
			DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/emp");
			//Context envCtx = (Context) initCtx.lookup("java:comp/env");
			//DataSource ds = (DataSource) envCtx.lookup("jdbc/emp");
			
			try {
				Connection conn = ds.getConnection(); //get a connection
				
				//prepare an INSERT statement
				PreparedStatement prepStmt = conn.prepareStatement(insertStmt);
				prepStmt.setInt(1, nextEmpId); //set id
				nextEmpId++;//increment id for next insert
				prepStmt.setString(2, emp.getFirstName());
				prepStmt.setString(3, emp.getLastName());
				System.out.println(prepStmt.toString());
				
				//execute the insert
				result = prepStmt.executeUpdate(); //returns the row count for SQL Data Manipulation Language (DML) statements
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		return result; //return 
	}
	
}
