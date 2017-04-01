package com.wsr.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.wsr.model.IncidentsBean;

public class ManagerActivitiesDaoHelper {

	List<String> filterSearchLs = new ArrayList<String>();
	ListIterator<String> lsIterator = null;
	private static boolean whereClauseAssigned = false;
	
	public String getTotalTktCountQuery() {
		
		return "Select COUNT(IncidentID) TotalCount from dbo.vTestTempTable";
	}

	public String getOpenTicketCountQuery() {
		
		return "Select COUNT(IncidentID) OpenCount from dbo.vTestTempTable where (Status <> \'BAM : Closed\' AND SM7Status <> \'Closed\' AND IsUpdated <> \'\')";
	}

	public String getOpenTicketsInTeamQuery() {
		
		return "Select distinct * from dbo.vTestTempTable where (Status <> \'BAM : Closed\' AND SM7Status <> \'Closed\' AND IsUpdated <> \'\')";
	}

	public String getSelectedMemberOpenTickets(String selectedMember) {
		
		return "Select distinct * from dbo.vTestTempTable where (Assignee = \'"+selectedMember+"\' AND Status <> \'BAM : Closed\' AND SM7Status <> \'Closed\')";
	}

	public String generateFilterQuery(Map<String, List<String>> searchFilterCriteriaMap, List<String[]> filterFieldsUtilLs) {
		
		String filter_ticket_query = "Select * from dbo.vTestTempTable";
		
		String selectedStatus = searchFilterCriteriaMap.get("Status").get(0);
		String fromDateSelected = searchFilterCriteriaMap.get("SelectedDates").get(0);
		String tillDateSelected = searchFilterCriteriaMap.get("SelectedDates").get(1);
		
		ListIterator filterLsIter =  filterFieldsUtilLs.listIterator();
		int index = 0;
		while(filterLsIter.hasNext()){
			
			filterLsIter.next();
			String[] utilArray = filterFieldsUtilLs.get(index);
			filter_ticket_query += generateConditionalClauses(utilArray[0], utilArray[1], utilArray[2], utilArray[3], searchFilterCriteriaMap, filter_ticket_query);
		//	filter_ticket_query += (!filterSearchLs.get(0).contains("ALL")) ? "\n"+getConnector()+utilArray[2]+" in (" + generateNestedQuery() +")": "";
			index++;
		}
		
		filter_ticket_query += getDateAndStatusFilterQuery(selectedStatus,fromDateSelected,tillDateSelected,filter_ticket_query);
		
		System.out.println(filter_ticket_query);
		return filter_ticket_query;
		
	/*	filter_ticket_query += generateConditionalClauses("Domain","DomainId","DomainName","dbo.vDomains",searchFilterCriteriaMap,filter_ticket_query);
		filter_ticket_query += generateConditionalClauses("SubDomain","SubDomainID","SubDomain","dbo.vSubDomains",searchFilterCriteriaMap,filter_ticket_query);
		filter_ticket_query += generateConditionalClauses("RootCause", "RootCauseId", "RootCause", "dbo.vRootCause", searchFilterCriteriaMap, filter_ticket_query);
		filter_ticket_query += generateConditionalClauses("Country", "CountryID", "Country","dbo.vCountries", searchFilterCriteriaMap, filter_ticket_query);
	//	filter_ticket_query += generateConditionalClauses("Member", "Assignee", null, "dbo.vProfiles", searchFilterCriteriaMap, filter_ticket_query);*/
		
	}

	private String generateConditionalClauses(String key, String keyId, String keyVal, String tableName, Map<String, List<String>> searchFilterCriteriaMap, String condtionalQuery) {
		
		filterSearchLs.clear();
		filterSearchLs = searchFilterCriteriaMap.get(key);
		lsIterator = filterSearchLs.listIterator();
		
		if(!filterSearchLs.get(0).contains("ALL")){
			
			String connector = getConnector();
			if(keyVal != null){
				condtionalQuery = "\n"+connector+keyVal+" in (" + generateNestedQuery() +")";
			}else{
				condtionalQuery = "\n"+connector+keyVal+" in ("+generateNestedQuery() + ")";
			}
			return condtionalQuery;
		}
		else{
			return "";
		}
	}
	
	private String getConnector() {
		
		if(whereClauseAssigned == false){
			whereClauseAssigned = true;
			return "where ";
		}else{
			return "And ";
		}
	}

	private String generateNestedQuery() {
		String textAppended="";
		boolean firstItemPointed = true;
		while(lsIterator.hasNext()){
			
			textAppended += (firstItemPointed == true) ? ("\'"+lsIterator.next().toString()+"\'") 
					: (",\'"+lsIterator.next().toString()+"\'") ;
			firstItemPointed = false;
		}
		return textAppended;
	}
	
	private String getDateAndStatusFilterQuery(String selectedStatus,String fromDateSelected, String tillDateSelected, String filter_ticket_query) {
		
		if(selectedStatus.contains("Tickets alive between selected Dates")){
			filter_ticket_query = "\n"+getConnector()+"((DateCreated Between \'"+fromDateSelected+"\' And \'"+tillDateSelected+"\')"
					+" Or (DateClosed Between \'"+fromDateSelected+"\' AND \'"+tillDateSelected+"\')"
					+" Or ((DateCreated < \'"+fromDateSelected+"\') And (DateClosed > \'"+tillDateSelected+"\')))";
		}else if(selectedStatus.contains("includes Only tickets opened between selected Dates")){
			filter_ticket_query = "\n"+getConnector()+"(DateCreated Between \'"+fromDateSelected+"\' And \'"+tillDateSelected+"\')";
		}else if(selectedStatus.contains("includes Only tickets Closed between selected Dates")){
			filter_ticket_query = "\n"+getConnector()+"(DateClosed Between \'"+fromDateSelected+"\' And \'"+tillDateSelected+"\')";
		}
		//resetting the whereClauseAssigned to false again for next search..
		whereClauseAssigned = false;
		return filter_ticket_query;
	}

	public String getInsertOpenIncidentListQuery(List<IncidentsBean> incBeanLs, int listCount) {
		
		String incidentID = incBeanLs.get(listCount).getIncidentID();
		String interactionID = incBeanLs.get(listCount).getInteractionID();
		int severity = incBeanLs.get(listCount).getSeverity();
		String dateCreated = incBeanLs.get(listCount).getCreatedDate();
		String dateClosed = incBeanLs.get(listCount).getClosedDate();
		if(dateClosed == null){
			dateClosed = null;
		}else{
			dateClosed = "\'"+dateClosed+"\'";
		}
		String assignee = incBeanLs.get(listCount).getAssignee();
		String status = incBeanLs.get(listCount).getSm9Status();
		String title = incBeanLs.get(listCount).getTitle();
		String contactWhoRaised = incBeanLs.get(listCount).getRaisedBy();
		String asset = incBeanLs.get(listCount).getAssetName();
		String slaTargetDate = incBeanLs.get(listCount).getSla_target_date();
		String slaBreached = incBeanLs.get(listCount).getSla_brached();
		String updatedTime = incBeanLs.get(listCount).getUpdateTime();
		String country = incBeanLs.get(listCount).getCountry();
		String closureCode = incBeanLs.get(listCount).getClosureCode();
		String isUpdated = incBeanLs.get(listCount).getIsUpdated();
		String domainID = incBeanLs.get(listCount).getdomainName();
		String subDomainID = incBeanLs.get(listCount).getsubDomainName();
		String rootCauseID = incBeanLs.get(listCount).getrootCauseName();
		String updateCountryID = incBeanLs.get(listCount).getupdateCountryName();
		String query=  "INSERT INTO dbo.TestTempTable VALUES(\'"+ incidentID + "\',\'"+interactionID+"\',\'"+ severity + "\',\'"+ dateCreated +"\'," + dateClosed +",\'"+ assignee +"\',null,\'"+ status +"\',\'"+ title + "\',\'"+ contactWhoRaised +"\',\'"+ asset +"\',null,null,null,null,\'"+ slaTargetDate +"\',\'"+ slaBreached +"\',null,null,"+ country +","+ closureCode +",\'"+ updatedTime +"\',null,"+ isUpdated +",null,"+ domainID +","+ subDomainID +","+ rootCauseID +","+ updateCountryID +")";
			  // "VALUES (\'"+incBeanLs.get(1).getIncidentID()+"\',\'InteractionID\',\'"+incBeanLs.get(3).getSeverity()+"\',\'DateCreated\',\'DateClosed\',\'"+incBeanLs.get(4).getAssignee()+"\',\'Status\',\'SM7Status\',\'"+incBeanLs.get(2).getTitle()+"\',\'ContactWhoRaised\',\'Asset\',\'GSAP_IncidentID\',\'DefectID\',\'ProblemID\',\'ActionOn\',\'"+incBeanLs.get(5).getSla_target_date()+"\',\'SLABreached\',\'Priority\',\'Proactive\',\'Country\',\'ClosureCode\',\'SM7LastUpdate\',\'ReleaseID\',\'IsUpdated\',\'Color\',\'DomainID\',\'subDomainID\',\'RootCauseID\',\'UpdateCountryID\')";
		return query;
	}
	
/*
 * DAO helper to add basic comment to new open incidents.
 * */	
public String getBasicCommentUpdateQuery(List<IncidentsBean> incBeanLs, int listCount){
		
		String incidentID = incBeanLs.get(listCount).getIncidentID();
		//String updatedDate =
		//String updatedTime = incBeanLs.get(listCount).getUpdateTime();
		Date date = new Date( );
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	    String updatedDate= dateFormat.format(date);
	    String update = "Investigation in progress";
	    String actionOn = incBeanLs.get(listCount).getAssignee();
	    String satusID = "2";
	    String isValid = "1";
	    String updatedBy = "Auto";
		//String query = "INSERT INTO dbo.HistoricActivities VALUES(\'"+ incidentID +"\',\'"+satusID+"\',\'"+actionOn+"\',\'"+ update + "\',\'"+ updatedDate + "\',\'"+ updatedBy + "\',\'"+ isValid+"\')";
		String query = "INSERT INTO dbo.HistoricActivities VALUES(\'"+ incidentID +"\',(select distinct StatusID from [dbo].[Status] where Status = 'BAM : Investigating'),\'"+actionOn+"\',\'"+ update + "\',\'"+ updatedDate + "\',\'"+ updatedBy + "\',\'"+ isValid+"\')";
		return query;
	}

	public String getUpdateClosedIncidentListQuery(List<IncidentsBean> incBeanLs, int listCount) {

		String incidentID = incBeanLs.get(listCount).getIncidentID();
		String interactionID = incBeanLs.get(listCount).getInteractionID();
		int severity = incBeanLs.get(listCount).getSeverity();
		String dateCreated = incBeanLs.get(listCount).getCreatedDate();
		String dateClosed = incBeanLs.get(listCount).getClosedDate();
		if(dateClosed == null ){
			dateClosed = null;
		}else{
			dateClosed = "\'"+dateClosed+"\'";
		}
		String assignee = incBeanLs.get(listCount).getAssignee();
		String status = incBeanLs.get(listCount).getSm9Status();
		String title = incBeanLs.get(listCount).getTitle();
		String contactWhoRaised = incBeanLs.get(listCount).getRaisedBy();
		String asset = incBeanLs.get(listCount).getAssetName();
		String slaTargetDate = incBeanLs.get(listCount).getSla_target_date();
		String slaBreached = incBeanLs.get(listCount).getSla_brached();
		String updatedTime = incBeanLs.get(listCount).getUpdateTime();
		
		String query = "UPDATE dbo.TestTempTable" 
		+" SET InteractionID = \'"+ interactionID +"\', Severity = \'"+ severity +"\', DateCreated = \'"+ dateCreated +"\', DateClosed = "+ dateClosed +", Assignee = \'"+ assignee +"\', SM7Status = \'"+ status +"\', Title = \'"+ title +"\', ContactWhoRaised = \'"+ contactWhoRaised +"\', Asset = \'"+ asset +"\', SLATargetDate = \'"+ slaTargetDate +"\', SLABreached = \'"+ slaBreached +"\', SM7LastUpdate = \'"+ updatedTime +"\'"
		+" WHERE IncidentID = \'"+ incidentID +"\'";
		return query;
	}

}
