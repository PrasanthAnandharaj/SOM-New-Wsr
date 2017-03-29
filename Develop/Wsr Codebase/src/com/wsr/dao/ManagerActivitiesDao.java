package com.wsr.dao;

import java.sql.ResultSet;

import com.wsr.connection.ConnectToDB;

@SuppressWarnings("finally")
public class ManagerActivitiesDao {
	
	ResultSet managerDaoRs;

	ConnectToDB connectionObj = new ConnectToDB();
	
	public ResultSet getTotalTicketCount(String total_ticket_count_query) {
		
		managerDaoRs = null;
		
		try{
		
			managerDaoRs = connectionObj.getConnection().createStatement().executeQuery(total_ticket_count_query);		
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getTotalTicketCount -- "+ex.getMessage());
		}finally{	
			return managerDaoRs;
		}
	}


	public ResultSet getOpenTicketsCount(String open_ticket_count) {

		managerDaoRs = null;
		
		try{
			managerDaoRs = connectionObj.getConnection().createStatement().executeQuery(open_ticket_count);
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getOpenTicketsCount -- "+ex.getMessage());
		}finally{					
			return managerDaoRs;
		}
	
	}


	public ResultSet getIncidentsOpenWithTeam(String open_tickets_in_team_query) {
		
		managerDaoRs = null;
		
		try{
			managerDaoRs = connectionObj.getConnection().createStatement().executeQuery(open_tickets_in_team_query);
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getIncidentsOpenWithTeam -- "+ex.getMessage());
		}finally{		
			return managerDaoRs;
		}
	}


	public ResultSet getSelectedMemberOpenTickets(String selected_member_open_tickets) {

		managerDaoRs = null;
		
		try{
			managerDaoRs = connectionObj.getConnection().createStatement().executeQuery(selected_member_open_tickets);
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getSelectedMemberOpenTickets -- "+ex.getMessage());
		}finally{		
			return managerDaoRs;
		}		
	}


	public ResultSet getFilteredSearchTickets(String filterSearchQuery) {
		
		managerDaoRs = null;
		
		try{
			managerDaoRs = connectionObj.getConnection().createStatement().executeQuery(filterSearchQuery);
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getFilteredSearchTickets -- "+ex.getMessage());
		}finally{		
			return managerDaoRs;
		}		
	}


	public boolean getCloseIncidentListUpadtes(String query) {
		
		boolean flag = false;
		
		try{
			connectionObj.getConnection().createStatement().executeUpdate(query);
			flag = true;
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getIncidentOpenOrCloseUpdates -- "+ex.getMessage());
			flag = false;
		}
		
		return flag;
	}


	public boolean getOpenIncidentListUpadtes(String query) {
		
		boolean flag = false;
		
		try{
			connectionObj.getConnection().createStatement().executeUpdate(query);
			flag = true;
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getIncidentOpenOrCloseUpdates -- "+ex.getMessage());
			flag = false;
		}
		
		return flag;
	}

}
	
