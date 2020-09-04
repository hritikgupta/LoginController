package com.login.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class SignupDao {

	private static final String QUERY = "INSERT INTO users(username, password, email) VALUES(?, ?, ?)";
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/mylogin";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "root";
	
	private static String returnPasswordHash(String password){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA1"); 
			byte[] messageDigest = md.digest(password.getBytes()); // add standardcharset.utf-8  
			BigInteger no = new BigInteger(1, messageDigest); // encode as base64 
			
			String hashtext = no.toString(16); 
            
            return hashtext; 
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e); 
		}
	}
	
	public int createUser(String passedUname, String passedEmail, String passedPassword){
		
		Connection conn = null;
		PreparedStatement st = null;
		int rowsAffected = 0;
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
			st = (PreparedStatement) conn.prepareStatement(QUERY); 
			
			st.setString(1, passedUname);
			st.setString(2, returnPasswordHash(passedPassword));
			st.setString(3,	 passedEmail);
			
			rowsAffected = st.executeUpdate(); // use execute method [needs checking]
			
			// throw exception from here, and handle at caller end
			return rowsAffected;
						
		} catch (Exception e){
			e.printStackTrace();
		} finally { // change to utility
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
		
		return rowsAffected;
	}
	
}
