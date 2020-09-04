package com.login.dao;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class WelcomeDao {

	private static final String QUERY = "select username from users where email=?";
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/mylogin";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "root";
	
	public String getUsername(String passedEmail){
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
			st = (PreparedStatement) conn.prepareStatement(QUERY); 
			
			System.out.println(passedEmail);
			
			st.setString(1,	 passedEmail);
			rs = st.executeQuery();
			
			if(rs.next()){
				return rs.getString(1);
			}
									
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(st != null){
				try{
					st.close();
				} catch(SQLException e){}
			}
			if(conn != null){
				try{
					conn.close();
				} catch(SQLException e){}
			}
		}
		
		return "user";
	}
	
}	
