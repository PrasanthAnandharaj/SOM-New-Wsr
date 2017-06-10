package com.wsr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wsr.connection.ConnectToDB;

@SuppressWarnings("finally")
public class WsrDbAccessor {
	
	ResultSet commonDaoRs;
	ConnectToDB connectionObj = new ConnectToDB();

	public ResultSet queryToDb(String all_members_query) {
		
		commonDaoRs = null;	
		try{     
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_members_query);		
		}catch(SQLException sql_ex){
			System.out.println("The error is :"+all_members_query);
			System.out.println("WsrDbAccessor -- queryToDb  -- "+all_members_query+":: "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("WsrDbAccessor -- queryToDb  -- "+all_members_query+":: "+ex.getMessage());
		}finally{	
			return commonDaoRs;
		}
	}

	public boolean getOpenOrCloseIncidentListUpdates(String query) {
		
		boolean flag = false;
		
		try{
			connectionObj.getConnection().createStatement().executeUpdate(query);
			flag = true;
		}catch(SQLException sql_ex){
			flag = false;
			System.out.println("WsrDbAccessor -- getOpenOrCloseIncidentListUpdates :: "+sql_ex.getMessage());
		}catch(Exception ex){
			flag = false;
			System.out.println("WsrDbAccessor -- getOpenOrCloseIncidentListUpdates :: "+ex.getMessage());
		}finally{		
			return flag;
		}
	}
	
	public int performCudIntoDb(String addMemberQuery) {
		
		int rowAffected = 0;
		try{		
			rowAffected  = connectionObj.getConnection().createStatement().executeUpdate(addMemberQuery);
		}catch(SQLException sql_ex){
			System.out.println("WsrDbAccessor -- performCudIntoDb :: "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("WsrDbAccessor -- performCudIntoDb :: "+ex.getMessage());
		}finally{	
			return rowAffected;
		}
	}	
}
