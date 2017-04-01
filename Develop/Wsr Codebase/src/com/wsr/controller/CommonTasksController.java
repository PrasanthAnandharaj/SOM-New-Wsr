package com.wsr.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wsr.dao.CommonActivitiesDao;
import com.wsr.dao.CommonActivitiesDaoHelper;
import com.wsr.dao.ManagerActivitiesDao;
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
	ManagerActivitiesDao mgrDoaObj = new ManagerActivitiesDao();
	CommonActivitiesDao commonDaoObj = new CommonActivitiesDao();
	StaffActivitiesDaoHelper staffDaoHelperObj = new StaffActivitiesDaoHelper();
	ManagerActivitiesDaoHelper managerDaoHelper = new ManagerActivitiesDaoHelper();
	List<IncidentsBean> incBeanLs = new ArrayList<IncidentsBean>();
	CommonActivitiesDaoHelper commonHelperObj = new CommonActivitiesDaoHelper();
	Map<String, List<String>> commonUtilMap = new HashMap<String, List<String>>();

	public List<String> getAllMembers() {
		
		commonTasksRs = null;
		commonUtilLs.clear();	
		String all_members_query = commonHelperObj.getAllMembersQuery();
		
		try{
			commonTasksRs = commonDaoObj.getAllMembers(all_members_query);
			while(commonTasksRs.next()){
				commonUtilLs.add(commonTasksRs.getString("UserID"));
			}
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getAllMembers -- "+ex.getMessage());
		}finally{	
			return commonUtilLs;
		}
		
	}

	public List<IncidentsBean> searchIncById(String searchId) {
		
		commonTasksRs = null;
		tempBean = null;
		incBeanLs.clear();
		String search_by_Inc_ID_Query = commonHelperObj.getSearchByIncIdQuery(searchId);
		
		try{
			
			commonTasksRs = commonDaoObj.searchByIncidentID(search_by_Inc_ID_Query);
			while(commonTasksRs.next()){
				tempBean = new IncidentsBean();
				tempBean = setIncidentBean(commonTasksRs,tempBean,"searchIncById");
				incBeanLs.add(tempBean);
			}
			
		}catch(Exception ex){
			System.out.println("CommonTasksController :: searchIncById -- "+ex.getMessage());
		}finally{	
			return incBeanLs;
		}
	}
	
	public List<IncidentsBean> searchTicketByKeyword(String searchKey) {
		
		ResultSet searchByKeyRs = null;
		tempBean = null;
		incBeanLs.clear();
		
		try{
			
			searchByKeyRs = commonDaoObj.searchTicketByKeyword(commonHelperObj.getSearchByKeywordQuery(searchKey));
			if(searchByKeyRs.next() == true){
				
				do{
					tempBean =  new IncidentsBean();
					tempBean = setIncidentBean(searchByKeyRs, tempBean, "searchTicketByKeyword");
					incBeanLs .add(tempBean);
					
				}while(searchByKeyRs.next() == true);
			}else{
				System.out.println("CommonTasksController :: getIncidentsOpenWithTeam -- No ticket with searched keyword in DB !!");
			}
			
		}catch(Exception ex){
			
		System.out.println("CommonTasksController -- searchTicketByKeyword :"+ex.getMessage());
			
		}
		return incBeanLs;
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
			commonTasksRs = mgrDoaObj.getTotalTicketCount(total_ticket_count_query);
			
			while(commonTasksRs.next()){

				ttlTktCount = commonTasksRs.getInt("TotalCount");
				commonIntegerLs.add(ttlTktCount);
				
			}
			commonTasksRs=null;
			commonTasksRs = mgrDoaObj.getOpenTicketsCount(open_ticket_count);
			
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
	
	public List<String> getAllDomains() {

		commonTasksRs = null;
		commonUtilLs.clear();
		
		String all_domain_options = commonHelperObj.getAllDomainOptions();
		try{
			commonTasksRs = commonDaoObj.getAllDomainOptions(all_domain_options);
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

	public List<String> getDomainToSubDomainMap(String selectedDomain) {
		
		commonTasksRs = null;
		commonUtilLs.clear();
		String domainId="";
		String get_DomainId_Query = commonHelperObj.getDomainIdQuery(selectedDomain);
		try{
			commonTasksRs = commonDaoObj.getDomainId(get_DomainId_Query);
			while(commonTasksRs.next()){
				domainId = commonTasksRs.getString("DomainId");
			}
			//clearing the rs before next assignment..
			commonTasksRs = null;
			if(!("").equals(get_DomainId_Query)){
				String get_Sub_Domain_as_Ls_query = commonHelperObj.getSubDomainListQuery(domainId);		
				commonTasksRs = commonDaoObj.getSubDomainsAsList(get_Sub_Domain_as_Ls_query);
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
	
	public List<String> getAllRootCause() {
		
		commonTasksRs = null;
		commonUtilLs.clear();
		String all_rootcause_query = commonHelperObj.getAllRootCauseQuery();
		try{
			commonTasksRs = commonDaoObj.getAllRootCause(all_rootcause_query);
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
			commonTasksRs = commonDaoObj.getAllCountries(all_country_query);
			while(commonTasksRs.next()){
				commonUtilLs.add(commonTasksRs.getString("CountryName"));
			}
			
		}catch(Exception ex){
			System.out.println("CommonTasksController :: getAllCountries -- "+ex.getMessage());
		}finally{
			commonTasksRs = null;
			return commonUtilLs;
		}	}

	private IncidentsBean setIncidentBean(ResultSet commonTasksRs,IncidentsBean incidentBean,String methodName) throws SQLException {
		
		incidentBean.setIncidentID(commonTasksRs.getString("IncidentID"));
		incidentBean.setTitle(commonTasksRs.getString("Title"));
		incidentBean.setSla_target_date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(commonTasksRs.getTimestamp("SLATargetDate")));
		incidentBean.setAssignee(commonTasksRs.getString("Assignee"));
		incidentBean.setCreatedDate(commonTasksRs.getString("DateCreated"));
		incidentBean.setClosedDate(commonTasksRs.getString("DateClosed"));
		incidentBean.setWsrCurrentStatus(commonTasksRs.getString("Status"));
		incidentBean.setSm9Status(commonTasksRs.getString("SM7Status"));
		incidentBean.setRaisedBy(commonTasksRs.getString("ContactWhoRaised"));
		incidentBean.setAssetName(commonTasksRs.getString("Asset"));
		incidentBean.setCountry(commonTasksRs.getString("Country"));
		incidentBean.setClosureCode(commonTasksRs.getString("ClosureCode"));
		incidentBean.setIsUpdated(commonTasksRs.getString("IsUpdated"));
		incidentBean.setdomainName(commonTasksRs.getString("Domain_Name"));
		incidentBean.setsubDomainName(commonTasksRs.getString("SubDomain"));
		incidentBean.setrootCauseName(commonTasksRs.getString("RootCause"));
		incidentBean.setupdateCountryName(commonTasksRs.getString("CountryName"));
		incidentBean.setSeverity(commonTasksRs.getInt("Severity"));
		
		return incidentBean;
		
	}

}
