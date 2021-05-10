package com.sd.mvc.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class EmployeeDao {
	
	//private static final String driver = "com.mysql.cj.jdbc.Driver"; //.cj is new (without .cj is deprecated)
	//private static final String connStr = "jdbc:mysql://localhost:3306/emp";
	//private static final String uname = "jeetu";
	//private static final String pwd = "welcome";
	private static final String insertStmt = "INSERT INTO EMPLOYEE " +
			"(id, first_name, last_name) " +
			"VALUES (?, ?, ?)";
	private static int nextEmpId = 1;
	private static final String selectStmt = "SELECT * FROM EMPLOYEE WHERE ID = %d";

	//we return the inserted employee with emp id set   
	public Employee registerEmployee(Employee emp) throws ClassNotFoundException {
		//Class.forName(driver); //load the driver
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
				emp.setId(nextEmpId); //set id
				nextEmpId++;//increment id for next insert
				prepStmt.setInt(1, emp.getId()); 
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
		
		if (result == 0) { //insert failed
			return null;
		} else {
			return emp; //return the created employee
		}
	}
	
	public Employee getEmployee(int id) {
		Employee emp = new Employee(); //employee to return
		try {
				Context initCtx = new InitialContext(); //JNDI context
				try {					
					DataSource ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/emp"); //get data source from JNDI context
					Connection conn = ds.getConnection(); //get connection
					Statement stmt = conn.createStatement(); //create SELECT statement
					String selectStmtPrepared = String.format(selectStmt, id); //set parameter in the select statement
					ResultSet rs = stmt.executeQuery(selectStmtPrepared); //execute the statement to fetch the result set
					while (rs.next()) { //set employee fields from the result set
						emp.setId(rs.getInt(1));
						emp.setFirstName(rs.getString(2));
						emp.setLastName(rs.getString(3));
					}
				} catch (SQLException e) {
					System.err.println(e.getMessage());
				} //catch (SQLException e)
			} catch (NamingException e) {
				System.err.println(e.getMessage());
			} //catch (NamingException e)
		return emp; //return the employee object
	} //public Employee getEmployee(int id)
}
