package com.wsr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectToDB {

	@SuppressWarnings("finally")
	public Connection getConnection(){
	
		Connection conn =null;
		
		String forName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String auth_details = "jdbc:sqlserver://10.47.37.90;DatabaseName=eHaulier_DR;integratedSecurity=true";	
	
		try{
		
			Class.forName(forName);	
			conn = DriverManager.getConnection(auth_details);
		
		}catch(SQLException sql_ex){
			JOptionPane.showMessageDialog(null,"Connection Prob");
			System.out.println("ConnectToDB -- getConnection() :: "+sql_ex);
		} catch (ClassNotFoundException cnf_ex) {
			JOptionPane.showMessageDialog(null,"Connection Prob");
			System.out.println("ConnectToDB -- getConnection() :: "+cnf_ex);
		}finally{
			
			return conn;
		}
	}
}
