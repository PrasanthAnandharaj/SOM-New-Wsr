package com.wsr.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.wsr.connection.ConnectToDB;

@SuppressWarnings("finally")
public class CommonActivitiesDao {
	
	ResultSet commonDaoRs;
	ConnectToDB connectionObj = new ConnectToDB();

	public ResultSet getAllMembers(String all_members_query) {
		
		commonDaoRs = null;	
		try{
      
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_members_query);		
		}catch(Exception ex){
			System.out.println("ManagerActivitiesDao :: getTotalTicketCount -- "+ex.getMessage());
		}finally{	
			return commonDaoRs;
		}
	}
	
	public ResultSet searchByIncidentID(String search_By_IncidentID_Query) {
		
		commonDaoRs = null;
		try {
			
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(search_By_IncidentID_Query);
			
		}catch(Exception ex){			
			System.out.println("CommonActivitiesDao -- searchByIncidentD :"+ex.getMessage());		
		}
		return commonDaoRs;
		
	}	
	
	public ResultSet searchTicketByKeyword(String searchByKeywordQuery) {
		
		commonDaoRs = null;
		try{
			
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(searchByKeywordQuery);			

		}catch (SQLException sql_ex) {
		
			System.out.println("CommonActivitiesDao -- searchTicketByKeyword : "+sql_ex.getMessage());
			
		}catch(Exception ex){
			
			System.out.println("CommonActivitiesDao -- searchTicketByKeyword :"+ex.getMessage());	
		}
		return commonDaoRs;
	}
	
	public ResultSet getAllDomainOptions(String all_domain_options) {
		
		commonDaoRs = null;
		
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_domain_options);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getAllDomainOptions -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}	
	}

	public ResultSet getAllSubDomainOptions(String all_SubDomain_Query) {
		
		commonDaoRs = null;
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_SubDomain_Query);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getAllSubDomainOptions -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}	
	}

	public ResultSet getDomainId(String get_DomainId_Query) {
		
		commonDaoRs = null;
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(get_DomainId_Query);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getDomainId -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}
	}

	public ResultSet getSubDomainsAsList(String get_Sub_Domain_as_Ls_query) {
		commonDaoRs = null;
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(get_Sub_Domain_as_Ls_query);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getSubDomainsAsList -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}
	}

	public ResultSet getAllRootCause(String all_rootcause_query) {
		commonDaoRs = null;
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_rootcause_query);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getAllRootCause -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}
	}

	public ResultSet getAllCountries(String all_countries_query) {
		commonDaoRs = null;
		try{
			commonDaoRs = connectionObj.getConnection().createStatement().executeQuery(all_countries_query);
		}catch(Exception ex){
			System.out.println("CommonActivitiesDao :: getAllCountries -- "+ex.getMessage());
		}finally{		
			return commonDaoRs;
		}
	}

	

}
