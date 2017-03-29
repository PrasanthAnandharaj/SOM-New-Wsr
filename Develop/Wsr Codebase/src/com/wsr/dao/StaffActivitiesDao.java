package com.wsr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;

import com.wsr.connection.ConnectToDB;
import com.wsr.model.AuthoriseBean;
//import com.wsr.model.FilterTicketsBean;

public class StaffActivitiesDao {
	
	ResultSet staffCommonUtilRs;
	
	ConnectToDB connectionObj = new ConnectToDB();
	AuthoriseBean authoriseBeanObj = new AuthoriseBean();
	
	public ResultSet authoriseUser(String authorisationQuery) throws SQLException{
		
		staffCommonUtilRs = null;
		try{	
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(authorisationQuery);
		}catch(SQLException sql_ex){
			System.out.println("StaffActivitiesDao -- authoriseUser : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- authoriseUser :"+ex.getMessage());
		}
		return staffCommonUtilRs;
	}

	public ResultSet getIncidentsInLoggedUserQueue(String incident_in_my_queue_query) {
	
		staffCommonUtilRs = null;
		try{	
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(incident_in_my_queue_query);
		}catch(SQLException sql_ex){
			System.out.println("StaffActivitiesDao -- authoriseUser : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- authoriseUser :"+ex.getMessage());
		}
		return staffCommonUtilRs;
	}

	public int getTotalTicketsHandled(String totTktHandledQuery) {
		
		staffCommonUtilRs = null;
		int rowcount = 0;
		
		try{
			
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(totTktHandledQuery);
			while(staffCommonUtilRs.next() == true){	
				rowcount++; 
			}
		}catch (SQLException sql_ex) {
			System.out.println("StaffActivitiesDao -- totTktHandledQuery : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- totTktHandledQuery :"+ex.getMessage());	
		}
		return rowcount;
	}


	public ResultSet getIncidentsInGroupQueue(String myGroupsIncInQueueQuery) {
		
		staffCommonUtilRs = null;
		try{		
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(myGroupsIncInQueueQuery);			
		}catch (SQLException sql_ex) {
			System.out.println("StaffActivitiesDao -- searchTicketByKeyword : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- searchTicketByKeyword :"+ex.getMessage());	
		}
		return staffCommonUtilRs;
	
	}

	public ResultSet getAllMemberDetails(String allMemberDetailsQuery) {
		
		staffCommonUtilRs = null;
		try{
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(allMemberDetailsQuery);			
		}catch (SQLException sql_ex) {
			System.out.println("StaffActivitiesDao -- getAllMemberDetails : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- getAllMemberDetails :"+ex.getMessage());	
		}
		return staffCommonUtilRs;
		
	}

	public ResultSet findRoleID(String findRoleIdQuery) {
		
		staffCommonUtilRs = null;
		try{			
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(findRoleIdQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- findRoleID :"+ex.getMessage());	
		}
		return staffCommonUtilRs;
	}

	public int AddMember(String addMemberQuery) {
		
		int rowAffected = 0;
		try{		
			rowAffected  = connectionObj.getConnection().createStatement().executeUpdate(addMemberQuery);
		}catch(SQLException sql_ex){
			System.out.println("StaffActivitiesDao -- AddMember : "+sql_ex.getMessage());
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- AddMember :"+ex.getMessage());
		}
		return rowAffected;
	}

	public int updateSelectedMember(String editQuery) {
		
		int rowAffected = 0;
		try{
			rowAffected = connectionObj.getConnection().createStatement().executeUpdate(editQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- editQuery :"+ex.getMessage());
		}
		return rowAffected;
	}

	public int InvalidateMember(String invalidateQuery) {
		
		int rowAffected = 0;
		try{
			rowAffected = connectionObj.getConnection().createStatement().executeUpdate(invalidateQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- InvalidateMember :"+ex.getMessage());
		}
		return rowAffected;
	
	}

	public ResultSet getPreviousUpdates(String previousCommentsQuery) {
		
		staffCommonUtilRs = null;
		try{
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(previousCommentsQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- getPreviousUpdates :"+ex.getMessage());
		}
		return staffCommonUtilRs;
	}

	public ResultSet getAllStatusOption(String get_all_status_query ) {
		
		staffCommonUtilRs = null;
		try{
			staffCommonUtilRs = connectionObj.getConnection().createStatement().executeQuery(get_all_status_query);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- getAllStatusOption :"+ex.getMessage());
		}
		return staffCommonUtilRs;
	}

	public int invalidateSelectedComment(String invalidate_comment_query) {
		
		int rowAffected = 0;
		try{
			rowAffected = connectionObj.getConnection().createStatement().executeUpdate(invalidate_comment_query);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- InvalidateMember :"+ex.getMessage());
		}
		return rowAffected;
	}

	public int insertProgressiveComment(String progressUpdateQuery) {
		
		int rowAffected = 0;
		try{
			rowAffected = connectionObj.getConnection().createStatement().executeUpdate(progressUpdateQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- insertProgressiveComment :"+ex.getMessage());
		}
		return rowAffected;
	}

	public int UpdateIncidentForComment(String updateInIncidentsQuery) {
		
		int rowAffected = 0;
		try{
			rowAffected = connectionObj.getConnection().createStatement().executeUpdate(updateInIncidentsQuery);
		}catch(Exception ex){
			System.out.println("StaffActivitiesDao -- UpdateIncidentForComment :"+ex.getMessage());
		}
		return rowAffected;
		
	}
}
