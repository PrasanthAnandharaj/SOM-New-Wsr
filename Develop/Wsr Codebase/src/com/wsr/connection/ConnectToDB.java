package com.wsr.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class ConnectToDB {

	private static Connection conn = null; 
	
	@SuppressWarnings("finally")
	public Connection getConnection(){
	
		long startTime3  = System.currentTimeMillis();
		
		if(conn == null){
			
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
				long stopTime3 = System.currentTimeMillis();
				System.out.println("Time taken for CONN Status Drop down : "+ (float)(stopTime3 - startTime3)/1000 + " seconds");
				return conn;
			}
		}else{
			System.out.println("-- Taken from Static--");
			return conn;
		}
	}
}
