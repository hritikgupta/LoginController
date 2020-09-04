package com.login.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class LoginDao {
	
	private static final String QUERY = "select * from users where email=? and password=?";
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/mylogin";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASS = "root";
	
	
	// add this function to another file
	private static String returnPasswordHash(String password){
		try{
			MessageDigest md = MessageDigest.getInstance("SHA1"); 
			byte[] messageDigest = md.digest(password.getBytes()); 
			BigInteger no = new BigInteger(1, messageDigest); 
			
			String hashtext = no.toString(16); 
            
            return hashtext; 
		} catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e); 
		}
	}
	
	public boolean checkCredentials(String passedEmail, String passedPassword) {
		
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		
		try{
			Class.forName(JDBC_DRIVER);
			conn = (Connection) DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASS);
			st = (PreparedStatement) conn.prepareStatement(QUERY); 
			
			st.setString(1, passedEmail);
			st.setString(2, returnPasswordHash(passedPassword));
			
			rs = st.executeQuery();
			if(rs.next()){
				return true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
		} finally {
			if(rs != null){
				try{
					rs.close();
				} catch (SQLException e){}
			}
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
		return false;
	}

}
