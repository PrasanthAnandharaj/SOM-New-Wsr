package com.wsr.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wsr.dao.WsrDbAccessor;
import com.wsr.dao.CommonActivitiesDaoHelper;
import com.wsr.dao.ManagerActivitiesDaoHelper;
import com.wsr.dao.StaffActivitiesDaoHelper;
import com.wsr.model.IncidentsBean;

@SuppressWarnings("finally")
public class CommonTasksController {
	
	IncidentsBean tempBean;
	ResultSet commonTasksRs;
	IncidentsBean incidentBean = new IncidentsBean();
	
	List<String> commonUtilLs = new ArrayList<String>();
	List<Integer> commonIntegerLs = new ArrayList<Integer>();
	List<IncidentsBean> incBeanLs = new ArrayList<IncidentsBean>();
	
	Map<String, List<String>> commonUtilMap = new HashMap<String, List<String>>();
	
	WsrDbAccessor dbAccessorObj = new WsrDbAccessor();
	StaffActivitiesDaoHelper staffDaoHelperObj = new StaffActivitiesDaoHelper();
	CommonActivitiesDaoHelper commonHelperObj = new CommonActivitiesDaoHelper();
	ManagerActivitiesDaoHelper managerDaoHelper = new ManagerActivitiesDaoHelper();

	//configure this..
	public List<String> fetchFromDB(String queryType,String selectedVal) {
		
		String daoQuery="";
		commonTasksRs = null;
		commonUtilLs.clear();	
		
		daoQuery = (queryType.equals("DomainToSubDomainMap")) ?	
								dbQueryRouter(queryType,selectedVal) :	dbQueryRouter(queryType, null);
		try{
			System.out.println("Ctrlr calling for method : "+queryType+"query :"+daoQuery);
			commonTasksRs = dbAccessorObj.queryToDb(daoQuery);
			while(commonTasksRs.next()){
				//As all queries returns the required data at first index starting 1..
				commonUtilLs.add(commonTasksRs.getString(1));
			}
		}catch(Exception ex){
			System.out.println("CommonTasksController :: fetchFromDB -- "+ex.getMessage());
		}finally{	
			return commonUtilLs;
		}	
	}

	public List<IncidentsBean> searchIncidents(String searchCriteria,String searchVal) {
		
		commonTasksRs = null;
		tempBean = null;
		incBeanLs.clear();
		String daoQuery = "";
		
		daoQuery = dbQueryRouter(searchCriteria, searchVal);
		try{		
			commonTasksRs = dbAccessorObj.queryToDb(daoQuery);
			while(commonTasksRs.next()){
				tempBean = new IncidentsBean();
				tempBean = setIncidentBean(commonTasksRs,tempBean,searchCriteria);
				incBeanLs.add(tempBean);
			}		
		}catch(Exception ex){
			System.out.println("CommonTasksController :: searchIncidents -- "+ex.getMessage());
		}finally{	
			return incBeanLs;
		}
	}
	
	public List<Integer> getTicketStatistics(String userLoggedInAs, String loggedUserId) {
		
		int ttlTktCount = 0,openTktCount=0;
		tempBean = null;
		commonIntegerLs.clear();
		commonTasksRs = null;
		
		String total_ticket_count_query = (userLoggedInAs.equals("Admin")) ? managerDaoHelper.getTotalTktCountQuery() 
												: staffDaoHelperObj.getTotalTktHandledCountQuery(loggedUserId) ;
		String open_ticket_count = (userLoggedInAs.equals("Admin")) ? managerDaoHelper.getOpenTicketCountQuery()
												: staffDaoHelperObj.getOpenTicketCountQuery(loggedUserId);	
		try{
			commonTasksRs = dbAccessorObj.queryToDb(total_ticket_count_query);
			while(commonTasksRs.next()){
				ttlTktCount = commonTasksRs.getInt("TotalCount");
				commonIntegerLs.add(ttlTktCount);				
			}
			commonTasksRs=null;
			commonTasksRs = dbAccessorObj.queryToDb(open_ticket_count);
			while(commonTasksRs.next()){
				openTktCount = commonTasksRs.getInt("OpenCount");
				commonIntegerLs.add(openTktCount);
			}		
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getTicketStatistics -- "+ex.getMessage());
		}finally{		
			return commonIntegerLs;
		}
	}

	private String dbQueryRouter(String queryType,String searchVal){
		
		String dbQuery = "";
		switch(queryType){
		
			case "AllMembers" :
				dbQuery = commonHelperObj.getAllMembersQuery();
				break;
			
			case "AllDomains" :
				dbQuery = commonHelperObj.getAllDomainOptions();
				break;
			
			case "AllRootCause" :
				dbQuery = commonHelperObj.getAllRootCauseQuery();
				break;
			
			case "AllCountries" :
				dbQuery = commonHelperObj.getAllCountriesQuery();
				break;
			
			case "IncidentsOpenWithTeam" :
				dbQuery = managerDaoHelper.getOpenTicketsInTeamQuery();
				break;
				
			case "LoggedUserIncidentsInQueue" :
				dbQuery = staffDaoHelperObj.getIncidentsInLoggedUserQuery();
				break;
			
			case "MemberSpecificOpenTicket" :
				dbQuery = managerDaoHelper.getSelectedMemberOpenTickets(searchVal);
				break;
			
			case "DomainToSubDomainMap" :
				dbQuery = commonHelperObj.getDomainToSubDomainMappingQuery(searchVal);
				break;
			
			case "SearchIncById" :
				dbQuery = commonHelperObj.getSearchByIncIdQuery(searchVal);
				break;
		
			case "searchTicketByKeyword" :
				dbQuery = commonHelperObj.getSearchByKeywordQuery(searchVal);
				break;		
			
		}
		return dbQuery;
	}
	
	private IncidentsBean setIncidentBean(ResultSet commonTasksRs,IncidentsBean incidentBean,String searchCriteria) throws SQLException {
		
		incidentBean.setIncidentID(commonTasksRs.getString("IncidentID"));
		incidentBean.setInteractionID(commonTasksRs.getString("InteractionID"));
		incidentBean.setTitle(commonTasksRs.getString("Title"));
		incidentBean.setSla_target_date(handleDateToString(commonTasksRs,"SLATargetDate"));//new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commonTasksRs.getTimestamp("SLATargetDate")));
		incidentBean.setAssignee(commonTasksRs.getString("Assignee"));
		incidentBean.setSeverity(commonTasksRs.getInt("Severity"));
		incidentBean.setCreatedDate(handleDateToString(commonTasksRs,"DateCreated"));//new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commonTasksRs.getTimestamp("DateCreated")));
		incidentBean.setClosedDate(handleDateToString(commonTasksRs,"DateClosed"));//new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commonTasksRs.getTimestamp("DateClosed")));
		incidentBean.setWsrCurrentStatus(commonTasksRs.getString("Status"));
		incidentBean.setSm9Status(commonTasksRs.getString("SM7Status"));
		incidentBean.setRaisedBy(commonTasksRs.getString("ContactWhoRaised"));
		incidentBean.setAssetName(commonTasksRs.getString("Asset"));
		incidentBean.setCountry(commonTasksRs.getString("Country"));
		incidentBean.setClosureCode(commonTasksRs.getString("ClosureCode"));
		incidentBean.setIsUpdated(commonTasksRs.getString("IsUpdated"));
		if(!(searchCriteria.equals("MemberSpecificOpenTicket")|| searchCriteria.equals("IncidentsOpenWithTeam") 
				|| searchCriteria.equals("LoggedUserIncidentsInQueue"))){	
			incidentBean.setdomainName(commonTasksRs.getString("Domain_Name"));
			incidentBean.setsubDomainName(commonTasksRs.getString("SubDomain"));
			incidentBean.setrootCauseName(commonTasksRs.getString("RootCause"));
			incidentBean.setupdateCountryName(commonTasksRs.getString("CountryName"));			
		}	
		return incidentBean;	
	}

	private String handleDateToString(ResultSet commonTasksRs, String dateOfColumn) throws SQLException {
		String dateString = (!(commonTasksRs.getTimestamp(dateOfColumn) == null)) ? 
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commonTasksRs.getTimestamp("SLATargetDate")) :  "not set" ; 
		return dateString;
	}
	
	//Move this changes to join query and handle it in fetchFromDb()..
/*	public List<String> getDomainToSubDomainMap(String selectedDomain) {
		
		commonTasksRs = null;
		commonUtilLs.clear();
		String domainId="";
		String get_DomainId_Query = commonHelperObj.getDomainIdQuery(selectedDomain);
		try{
			commonTasksRs = dbAccessorObj.queryToDb(get_DomainId_Query);
			while(commonTasksRs.next()){
				domainId = commonTasksRs.getString("DomainId");
			}
			//clearing the rs before next assignment..
			commonTasksRs = null;
			if(!("").equals(get_DomainId_Query)){
				String get_Sub_Domain_as_Ls_query = commonHelperObj.getSubDomainListQuery(domainId);		
				commonTasksRs = dbAccessorObj.queryToDb(get_Sub_Domain_as_Ls_query);
				while(commonTasksRs.next()){
					commonUtilLs.add(commonTasksRs.getString("SubDomain"));
				}
			}	
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getDomainToSubDomainMap -- "+ex.getMessage());
		}finally{
			commonTasksRs = null;
			return commonUtilLs;
		}
	}
	
		public List<String> getAllDomains() {

	commonTasksRs = null;
	commonUtilLs.clear();
	
	String all_domain_options = commonHelperObj.getAllDomainOptions();
	try{
		commonTasksRs = dbAccessorObj.queryToDb(all_domain_options);
		System.out.println("Is rs null : "+commonTasksRs == null);
		while(commonTasksRs.next()){
			commonUtilLs.add(commonTasksRs.getString("DomainName"));	
		}
	}catch(Exception ex){
		System.out.println("CommonTasksController :: getMemberSpecificOpenTicket -- "+ex.getMessage());
	}finally{
		System.out.println("Comtroller Rs size"+commonUtilLs.size());
		return commonUtilLs;
	}
	}

	public List<String> getAllRootCause() {
		
		commonTasksRs = null;
		commonUtilLs.clear();
		String all_rootcause_query = commonHelperObj.getAllRootCauseQuery();
		try{
			long startTime4  = System.currentTimeMillis();
		
			commonTasksRs = dbAccessorObj.queryToDb(all_rootcause_query);
		
			long stopTime4 = System.currentTimeMillis();
			System.out.println("Time taken for CTRL RootCause Drop down : "+ (float)(stopTime4 - startTime4)/1000 + " seconds");
			
			while(commonTasksRs.next()){
				commonUtilLs.add(commonTasksRs.getString("RootCause"));
			}
			
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getAllRootCause -- "+ex.getMessage());
		}finally{
			commonTasksRs = null;
			return commonUtilLs;
		}
	}

	public List<String> getAllCountries() {

		commonTasksRs = null;
		commonUtilLs.clear();
		String all_country_query = commonHelperObj.getAllCountriesQuery();
		try{
			commonTasksRs = dbAccessorObj.queryToDb(all_country_query);
			while(commonTasksRs.next()){
				commonUtilLs.add(commonTasksRs.getString("CountryName"));
			}
			
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getAllCountries -- "+ex.getMessage());
		}finally{
			commonTasksRs = null;
			return commonUtilLs;
		}	
		
	}
		
	public List<String> getAllMembers() {
	
	commonTasksRs = null;
	commonUtilLs.clear();	
	String all_members_query = commonHelperObj.getAllMembersQuery();
	
	try{
		commonTasksRs = dbAccessorObj.queryToDb(all_members_query);
		while(commonTasksRs.next()){
			commonUtilLs.add(commonTasksRs.getString("UserID"));
		}
	}catch(Exception ex){
		System.out.println("CommonTasksController :: getAllMembers -- "+ex.getMessage());
	}finally{	
		return commonUtilLs;
	}
}*/

}
