package com.bankofavalos.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection conn = null;
	
	private ConnectionUtil() {
		super();
	}

	public static Connection getConnection() {
		
		try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		// This above statement uses Reflection to confirm that a class with this
		// fully qualified name is available
		
			try {
				conn = DriverManager.getConnection("jdbc:oracle:thin:@revature-training.cehe1xne1cax.us-east-1.rds.amazonaws.com:1521:orcl"
						, "bankofavalos", "password");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conn;
	}

}
