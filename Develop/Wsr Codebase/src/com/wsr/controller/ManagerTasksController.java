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
import com.wsr.dao.CommonActivitiesDao;
import com.wsr.dao.CommonActivitiesDaoHelper;
import com.wsr.dao.ManagerActivitiesDao;
import com.wsr.dao.ManagerActivitiesDaoHelper;
import com.wsr.model.IncidentsBean;

@SuppressWarnings("finally")
public class ManagerTasksController {
	
	ResultSet managerTasksRs;
	
	IncidentsBean tempBean = new IncidentsBean();
	List<String> mgrStringLs = new ArrayList<String>();
	List<IncidentsBean> mgrIncBeanLs = new ArrayList<IncidentsBean>();
	List<IncidentsBean> incBeanLs = new ArrayList<IncidentsBean>();
	Map<String, String> tableValueMap;
	Map<Integer, String> tableHeadingValueMap;
	
	ManagerActivitiesDaoHelper managerDaoHelper = new ManagerActivitiesDaoHelper();
	CommonActivitiesDaoHelper commonHelperObj = new CommonActivitiesDaoHelper();
	CommonActivitiesDao commonDaoObj = new CommonActivitiesDao();
	ManagerActivitiesDao mgrDoaObj = new ManagerActivitiesDao();
	
	boolean flag;
	
	public List<IncidentsBean> getIncidentsOpenWithTeam(){
		
		tempBean = null;
		managerTasksRs = null;
		mgrIncBeanLs.clear();
		String open_tickets_in_team_query = managerDaoHelper.getOpenTicketsInTeamQuery();

		try{
			managerTasksRs = mgrDoaObj.getIncidentsOpenWithTeam(open_tickets_in_team_query);
			if(managerTasksRs != null){
				while(managerTasksRs.next()){
					tempBean = new IncidentsBean();
					tempBean = settempBean(managerTasksRs,tempBean,"getIncidentsOpenWithTeam");
					mgrIncBeanLs.add(tempBean);
				}
			}else{
				managerTasksRs = null;
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
			managerTasksRs = mgrDoaObj.getSelectedMemberOpenTickets(selected_member_open_tickets);
			if(managerTasksRs != null){
				while(managerTasksRs.next()){
					tempBean = new IncidentsBean();
					tempBean = settempBean(managerTasksRs,tempBean,"getMemberSpecificOpenTicket");
					mgrIncBeanLs.add(tempBean);
				}
			}else{
				managerTasksRs = null;
			}
		}catch(Exception ex){
			System.out.println("ManagerTasksController :: getMemberSpecificOpenTicket -- "+ex.getMessage());
		}finally{
			return mgrIncBeanLs;
		}
	}
	
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
			managerTasksRs = mgrDoaObj.getFilteredSearchTickets(filterSearchQuery);
			if(managerTasksRs != null){
				while(managerTasksRs.next() == true){
					tempBean = new IncidentsBean();
					tempBean = settempBean(managerTasksRs,tempBean,"refinedSearchQueryBuilder");
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
	
	private IncidentsBean settempBean(ResultSet managerTasksRs, IncidentsBean tempBean, String methodType) throws SQLException {
		
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
		if(!(methodType.equals("getMemberSpecificOpenTicket")|| methodType.equals("getIncidentsOpenWithTeam") 
				|| methodType.equals("refinedSearchQueryBuilder"))){
		
			tempBean.setdomainName(managerTasksRs.getString("Domain_Name"));
			tempBean.setsubDomainName(managerTasksRs.getString("SubDomain"));
			tempBean.setrootCauseName(managerTasksRs.getString("RootCause"));
			tempBean.setupdateCountryName(managerTasksRs.getString("CountryName"));
			
		}
		return tempBean;
	}

	public boolean IterateThroughOpenSheet(CSVReader reader) throws ParseException {

		int count = 0;
		String row[];
		String rowHeading[];
		tempBean = null;
		boolean updateFlag = false;
		String query = null;
		try {
			
			// Getting Column Header
			rowHeading = reader.readNext();
			int size = rowHeading.length;
			tableHeadingValueMap = new HashMap<>();
			for(int i = 0; i < size; i++){
			tableHeadingValueMap.put( i , rowHeading[i]);
			}
			
			// Iterating through column
			while((row = reader.readNext()) != null){
				count++;
				incBeanLs.clear();
				for(int i=0;i<row.length;i++){
				String checkIncident = row[i].toString();
				if(checkIncident.startsWith("GIM")){
					flag = searchIncById(checkIncident);
				}
				}
				
				//tableValueList = new ArrayList<String>();
				tableValueMap  = new HashMap<>();
				
				for(int i=0;i<row.length;i++){
				tableValueMap.put(tableHeadingValueMap.get(i), row[i]);
				}
				
				/*for(int i=0;i<row.length;i++){
					tableValueList.add(row[i]);
					
				}*/
				tempBean =  new IncidentsBean();
                tempBean = setIncidentBean(tableValueMap,tempBean,"IterateThroughSheet");
                incBeanLs .add(tempBean);
                // Query Building Block
                int listCount = 0;
                if(flag == false){
                	query = managerDaoHelper.getInsertOpenIncidentListQuery(incBeanLs, listCount);
                	updateFlag = mgrDoaObj.getOpenIncidentListUpadtes(query);
                }
                else{
                	query = managerDaoHelper.getUpdateClosedIncidentListQuery(incBeanLs, listCount);
                	 updateFlag = mgrDoaObj.getCloseIncidentListUpadtes(query);
                }
                listCount++;

                System.out.println("queryupdated" + query);
				
				}
			System.out.println("count: "+count);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return updateFlag;
	}

	private IncidentsBean setIncidentBean(Map<String, String> tableValueMap, IncidentsBean tempBean, String IterateThroughSheet) throws ParseException {
		// TODO Auto-generated method stub
		
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
	
	public boolean searchIncById(String searchId) {
		
		managerTasksRs = null;
		
		String search_by_Inc_ID_Query = commonHelperObj.getSearchByIncIdQuery(searchId);
		
		try{
			
			managerTasksRs = commonDaoObj.searchByIncidentID(search_by_Inc_ID_Query);
			if(managerTasksRs.next()){
				flag = true;
			}else{
				flag = false;
			}
			
		}catch(Exception ex){
			System.out.println("CommonTasksController :: searchIncById -- "+ex.getMessage());
		}finally{	
			return flag;
		}
	}
	
}
