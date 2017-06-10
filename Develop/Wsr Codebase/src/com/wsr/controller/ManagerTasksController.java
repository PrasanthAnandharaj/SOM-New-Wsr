package com.wsr.controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.wsr.dao.WsrDbAccessor;
import com.wsr.dao.CommonActivitiesDaoHelper;
import com.wsr.dao.ManagerActivitiesDaoHelper;
import com.wsr.model.IncidentsBean;

@SuppressWarnings("finally")
public class ManagerTasksController {
	
	boolean flag;
	ResultSet managerTasksRs;
	
	Map<String, String> tableValueMap;
	Map<Integer, String> tableHeadingValueMap;
	IncidentsBean tempBean = new IncidentsBean();
	List<String> mgrStringLs = new ArrayList<String>();
	List<IncidentsBean> incBeanLs,mgrIncBeanLs = new ArrayList<IncidentsBean>();
	
	WsrDbAccessor dbAccessorObj = new WsrDbAccessor();
	CommonActivitiesDaoHelper commonHelperObj = new CommonActivitiesDaoHelper();
	ManagerActivitiesDaoHelper managerDaoHelper = new ManagerActivitiesDaoHelper();	
	
	public List<IncidentsBean> refinedSearchQueryBuilder(Map<String,List<String>> searchFilterCriteriaMap) {
		
		managerTasksRs = null;
		mgrIncBeanLs.clear();
		List<String[]> filterFieldsUtilLs = new  ArrayList<>();
		
		String[] domainUtil = new String[]{"Domain","DomainId","Domain_Name","dbo.vDomains"};
		String[] subDomainUtil = new String[]{"SubDomain","SubDomainID","SubDomain","dbo.vSubDomains"};
		String[] rootCauseUtil = new String[]{"RootCause", "RootCauseId", "RootCause", "dbo.vRootCause"};
		String[] countryUtil = new String[]{"Country", "CountryID", "CountryName","dbo.vCountries"};
		String[] memberUtil = new String[]{"Member", "Assignee", "Assignee", "dbo.vProfiles"};
		
		try{
			filterFieldsUtilLs.add(domainUtil);
			filterFieldsUtilLs.add(subDomainUtil);
			filterFieldsUtilLs.add(rootCauseUtil);
			filterFieldsUtilLs.add(countryUtil);
			filterFieldsUtilLs.add(memberUtil);
			
			String filterSearchQuery = managerDaoHelper.generateFilterQuery(searchFilterCriteriaMap,filterFieldsUtilLs);
			managerTasksRs = dbAccessorObj.queryToDb(filterSearchQuery);
			if(managerTasksRs != null){
				while(managerTasksRs.next() == true){
					tempBean = new IncidentsBean();
					tempBean = setTempBean(managerTasksRs,tempBean,"refinedSearchQueryBuilder");
					mgrIncBeanLs.add(tempBean);
				}
			}
			
		}catch(Exception ex){
			System.out.println("ManagerTasksController :: refinedSearchQueryBuilder -- "+ex.getMessage());
		}finally{
			filterFieldsUtilLs.clear();
			return mgrIncBeanLs;
		}
	}	

	public boolean IterateThroughOpenSheet(CSVReader reader) throws ParseException, SQLException {
		
		tempBean = null; 
		String row[],rowHeading[];
		int size =0,count = 0,rowAffected = 0;
		String query = null,basicCommentUpdateQuery = null;
		try {
			// Getting Column Header
			rowHeading = reader.readNext();
			size = rowHeading.length;
			tableHeadingValueMap = new HashMap<>();
			for(int i = 0; i < size; i++){
				tableHeadingValueMap.put( i , rowHeading[i]);
			}
			// Iterating through column
			while((row = reader.readNext()) != null){
				count++;
			//  change this line..	incBeanLs.clear();
				incBeanLs = new ArrayList<IncidentsBean>();
				for(int i=0;i<row.length;i++){
					String checkIncident = row[i].toString();
					if(checkIncident.startsWith("GIM")){
						
						String search_by_Inc_ID_Query = commonHelperObj.getSearchByIncIdQuery(checkIncident);
						managerTasksRs = dbAccessorObj.queryToDb(search_by_Inc_ID_Query);			
						flag = (managerTasksRs.next()) ? true : false ;
						//flag = searchIncById(checkIncident);
					}
				}
				
				//tableValueList = new ArrayList<String>();
				tableValueMap  = new HashMap<>();
				
				for(int i=0;i<row.length;i++){
					if(row[i].contains("\'")){
						row[i] = row[i].replace("'", "\''");
					}
				tableValueMap.put(tableHeadingValueMap.get(i), row[i]);
				}
				
				tempBean =  new IncidentsBean();
                tempBean = setIncidentBean(tableValueMap,tempBean,"IterateThroughSheet");
                incBeanLs .add(tempBean);
                // Query Building Block
                int listCount = 0;
                if(flag == false){
                	query = managerDaoHelper.getInsertOpenIncidentListQuery(incBeanLs, listCount);
                	rowAffected = dbAccessorObj.performCudIntoDb(query);
                	System.out.println("inserted into testtemptable");
                // Adding the basic comment for new open tickets
                	
                	basicCommentUpdateQuery = managerDaoHelper.getBasicCommentUpdateQuery(incBeanLs,listCount);
                	System.out.println("basicqueryupdated" + basicCommentUpdateQuery);
                	System.out.println("inserted into historicactivities");
                }
                else{
                	query = managerDaoHelper.getUpdateClosedIncidentListQuery(incBeanLs, listCount);
                	rowAffected = dbAccessorObj.performCudIntoDb(query);
                }
                listCount++;

                System.out.println("queryupdated" + query);
				
				}
			System.out.println("count: "+count);
		}catch (IOException e) {
			e.printStackTrace();
		}	
		if(rowAffected == 1)
			return true;
		else
			return false;
	}

private IncidentsBean setTempBean(ResultSet managerTasksRs, IncidentsBean tempBean, String methodType) throws SQLException {
		
		tempBean.setIncidentID(managerTasksRs.getString("IncidentID"));
		tempBean.setTitle(managerTasksRs.getString("Title"));
		tempBean.setSeverity(managerTasksRs.getInt("Severity"));
		tempBean.setAssignee(managerTasksRs.getString("Assignee"));
		tempBean.setSla_target_date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(managerTasksRs.getTimestamp("SLATargetDate")));
		tempBean.setCreatedDate(managerTasksRs.getString("DateCreated"));
		tempBean.setClosedDate(managerTasksRs.getString("DateClosed"));
		tempBean.setWsrCurrentStatus(managerTasksRs.getString("Status"));
		tempBean.setSm9Status(managerTasksRs.getString("SM7Status"));
		tempBean.setRaisedBy(managerTasksRs.getString("ContactWhoRaised"));
		tempBean.setAssetName(managerTasksRs.getString("Asset"));
		tempBean.setCountry(managerTasksRs.getString("Country"));
		tempBean.setClosureCode(managerTasksRs.getString("ClosureCode"));
		tempBean.setIsUpdated(managerTasksRs.getString("IsUpdated"));
		if(!(methodType.equals("refinedSearchQueryBuilder")|| methodType.equals("getIncidentsOpenWithTeam") 
				|| methodType.equals("getMemberSpecificOpenTicket"))){	
			tempBean.setdomainName(managerTasksRs.getString("Domain_Name"));
			tempBean.setsubDomainName(managerTasksRs.getString("SubDomain"));
			tempBean.setrootCauseName(managerTasksRs.getString("RootCause"));
			tempBean.setupdateCountryName(managerTasksRs.getString("CountryName"));
			
		}
		return tempBean;
	}

	private IncidentsBean setIncidentBean(Map<String, String> tableValueMap, IncidentsBean tempBean, String IterateThroughSheet) throws ParseException {
		
		DateFormat outputFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:sss");
		DateFormat inputFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
		
		tempBean.setIncidentID(tableValueMap.get("Incident ID"));
		tempBean.setInteractionID(tableValueMap.get("Interaction ID"));
		
		int severity = Integer.parseInt(String.valueOf(tableValueMap.get("Severity").charAt(0)));
		tempBean.setSeverity(severity);
		
		String dateCreated = tableValueMap.get("Date Created");
		Date dateCreatedDate = inputFormat.parse(dateCreated);
		String dateCreatedOutput = outputFormat.format(dateCreatedDate);
		tempBean.setCreatedDate(dateCreatedOutput);
		
		String dateClosed = tableValueMap.get("Close Time");
		if(dateClosed.equalsIgnoreCase("")){
			tempBean.setClosedDate(null);
		}else{
		Date dateClosedDate = inputFormat.parse(dateClosed);
		String dateClosedOutput = outputFormat.format(dateClosedDate);
		tempBean.setClosedDate(dateClosedOutput);
		}
		
		tempBean.setAssignee(tableValueMap.get("Assignee"));
		tempBean.setSm9Status(tableValueMap.get("Status"));
		tempBean.setTitle(tableValueMap.get("Title"));
		tempBean.setRaisedBy(tableValueMap.get("Contact Full Name"));
		tempBean.setAssetName(tableValueMap.get("Asset"));
		
		String inputText = tableValueMap.get("SLA Target Date");
		Date date = inputFormat.parse(inputText);
		String outputText = outputFormat.format(date);
		
		tempBean.setSla_target_date(outputText);
		tempBean.setSla_brached(tableValueMap.get("SLA Breached"));
		
		String updateTime = tableValueMap.get("Update Time");
		Date updateTimeDate = inputFormat.parse(updateTime);
		String updateTimeOutput = outputFormat.format(updateTimeDate);
		tempBean.setUpdateTime(updateTimeOutput);
		
		return tempBean;
	}
	
	/*	public boolean searchIncById(String searchId) {
		
		flag = false;
		managerTasksRs = null;
		
		String search_by_Inc_ID_Query = commonHelperObj.getSearchByIncIdQuery(searchId);
		try{
			managerTasksRs = dbAccessorObj.queryToDb(search_by_Inc_ID_Query);			
			flag = (managerTasksRs.next()) ? true : false ;
		}catch(Exception ex){
			System.out.println("CommonTasksController :: searchIncById -- "+ex.getMessage());
		}finally{	
			return flag;
		}
	}
	
	public List<IncidentsBean> getIncidentsOpenWithTeam(){
	
	tempBean = null;
	managerTasksRs = null;
	mgrIncBeanLs.clear();
	String open_tickets_in_team_query = managerDaoHelper.getOpenTicketsInTeamQuery();

	try{
		managerTasksRs = dbAccessorObj.queryToDb(open_tickets_in_team_query);
		if(managerTasksRs != null){
			while(managerTasksRs.next()){
				tempBean = new IncidentsBean();
				tempBean = setTempBean(managerTasksRs,tempBean,"getIncidentsOpenWithTeam");
				mgrIncBeanLs.add(tempBean);
			}
		}else{
			System.out.println("ManagerTasksController :: getIncidentsOpenWithTeam -- IncOpenWithTeam is null in DB");
		}
	}catch(Exception ex){
		System.out.println("ManagerTasksController :: getIncidentsOpenWithTeam -- "+ex.getMessage());
	}finally{
		return mgrIncBeanLs;
	}
}

public List<IncidentsBean> getMemberSpecificOpenTicket(String selectedMember) {
	
	tempBean = null;
	managerTasksRs = null;
	mgrIncBeanLs.clear();
	String selected_member_open_tickets = managerDaoHelper.getSelectedMemberOpenTickets(selectedMember);
	try{
		managerTasksRs = dbAccessorObj.queryToDb(selected_member_open_tickets);
		if(managerTasksRs != null){
			while(managerTasksRs.next()){
				tempBean = new IncidentsBean();
				tempBean = setTempBean(managerTasksRs,tempBean,"getMemberSpecificOpenTicket");
				mgrIncBeanLs.add(tempBean);
			}
		}else{
			System.out.println("ManagerActivites -- getMemberSpecificOpenTicket : selectedMember has no open tkt in DB !!");
			//	return null;
		}
	}catch(Exception ex){
		System.out.println("ManagerTasksController :: getMemberSpecificOpenTicket -- "+ex.getMessage());
	}finally{
		return mgrIncBeanLs;
	}
}*/
	
}
