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
import com.wsr.dao.StaffActivitiesDao;
import com.wsr.dao.StaffActivitiesDaoHelper;
import com.wsr.model.IncidentsBean;
import com.wsr.model.TicketUpdateBean;

public class StaffActivitiesController {

	StaffActivitiesDao staffDaoObj = new StaffActivitiesDao();
	StaffActivitiesDaoHelper daohelper = new StaffActivitiesDaoHelper();
	CommonActivitiesDao commonDaoObj = new CommonActivitiesDao();
	CommonActivitiesDaoHelper staffDaoObjHelper = new CommonActivitiesDaoHelper();
	
	List<IncidentsBean> staffUtilIncLs = new ArrayList<IncidentsBean>();
	
	List<TicketUpdateBean> prevCommentsList = new ArrayList<TicketUpdateBean>();
	
	@SuppressWarnings("finally")
	public List<IncidentsBean> getLoggedUserIncidentsInQueue() {
	
		staffUtilIncLs.clear();
		boolean is_incidentRs_null = true;		
		ResultSet incidentsInMyQueueRs = null;
		
		try{

			String incident_in_my_queue_query = daohelper.getIncidentsInLoggedUserQuery();		
			incidentsInMyQueueRs = staffDaoObj.getIncidentsInLoggedUserQueue(incident_in_my_queue_query);
			
			if(incidentsInMyQueueRs.next() == true){
								
				do{
					
					IncidentsBean incidentsInMyQueueBean = new IncidentsBean();
					incidentsInMyQueueBean = setIncidentsBeans(incidentsInMyQueueRs,incidentsInMyQueueBean,"getUserIncidentInQueue");
					staffUtilIncLs.add(incidentsInMyQueueBean);
				
				}while(incidentsInMyQueueRs.next() == true);
				
					is_incidentRs_null = false;
			}
		}catch(Exception ex){
			
			System.out.println("StaffActivites -- getLoggedUserIncidentsInQueue :"+ex.getMessage());
			
		}finally{
			
			if(is_incidentRs_null == false){
				
				return staffUtilIncLs;
			}else{
				
				return null;
			}
		}
		
	}

/*	public List<IncidentsBean> searchTicketByIncidentID(String incidentID) {
		
		staffUtilIncLs.clear();
		String search_Ticket_Status = "";
		ResultSet searchByIncidentRs = null; 
		
		//Map<String,List<IncidentsBean>> searchIncidentFeedback= new HashMap<String,List<IncidentsBean>>();
		
		
		try{
					
				IncidentsBean incidentsBean = new IncidentsBean();

				String search_By_IncidentID_Query = staffDaoObjHelper.getSearchByIncIdQuery(incidentID);
				searchByIncidentRs =  commonDaoObj.searchByIncidentID(search_By_IncidentID_Query);
				
				// Checking for only one row..
				if(searchByIncidentRs.next() == true){
									
					incidentsBean = setIncidentsBeans(searchByIncidentRs,incidentsBean,"searchTicketByIncidentID");
					System.out.println("Checking incBean : "+incidentsBean.getIncidentID());
					
					search_Ticket_Status = "success";
					staffUtilIncLs.add(incidentsBean);
					
					staffUtilIncLs.put(search_Ticket_Status,staffUtilIncLs);
					System.out.println("passed :"+staffUtilIncLs.size()+"  -- Checking ::" +staffUtilIncLs.get(0).getIncidentID() );
					
					if(searchByIncidentRs.next() == true){	
						
						System.out.println("FlowController -- searchTicketByIncidentID :: Duplicate records in DB");						
						search_Ticket_Status = "duplicate";
						
						//Making bean empty, as table not to display.
						incidentsBean = new IncidentsBean();
						staffUtilIncLs.add(incidentsBean);
						staffUtilIncLs.put(search_Ticket_Status,staffUtilIncLs);
					}
					
				}else{
					
					System.out.println("FlowController -- searchTicketByIncidentID :: SearchTicket is null");
					search_Ticket_Status = "norecord";
					
					//Making bean empty, as table not to display.
					incidentsBean = new IncidentsBean();
					staffUtilIncLs.add(incidentsBean);
					staffUtilIncLs.put(search_Ticket_Status,incidentsList);
				}
			
			}catch(Exception ex){
					
				System.out.println("FlowController -- searchTicketByIncidentID :"+ex.getMessage());
					
			}

		return staffUtilIncLs;
	}*/
	
	public int getTotalTicketsHandled() {
		
		int total_tickets_handled = 0;
		
		try{
		
			total_tickets_handled = staffDaoObj.getTotalTicketsHandled(daohelper.getTotTktHandledQuery());
		
		}catch(Exception ex){
			
		System.out.println("FlowController -- getTotalTicketsHandled :"+ex.getMessage());
			
	}
		
		return total_tickets_handled;
	}
	
	public List<IncidentsBean> getMyGroupsIncInQueue() {
		
		ResultSet incInMyGroupRs = null;
		staffUtilIncLs.clear();
		
		try{
			
			incInMyGroupRs = staffDaoObj.getIncidentsInGroupQueue(daohelper.getMyGroupsIncInQueueQuery());
			if(incInMyGroupRs.next() == true){
				
				do{
					IncidentsBean incInMyGroupBean = new IncidentsBean();
					incInMyGroupBean = setIncidentsBeans(incInMyGroupRs, incInMyGroupBean, "getMyGroupsIncInQueueQuery");
					staffUtilIncLs.add(incInMyGroupBean);
					
				}while(incInMyGroupRs.next() == true);
			
			}else{
				
				return null;
			}
		
		}catch(Exception ex){
			
		System.out.println("FlowController -- getMyGroupsIncInQueue :"+ex.getMessage());
			
		}
		
		return staffUtilIncLs;
	}
	
	public List<TicketUpdateBean> getTicketUpdates(String incidentToBeUpdated) {
		
		ResultSet previousCommentsRs = null;
		prevCommentsList.clear();
		
		try{
			
			String previousCommentsQuery = daohelper.getTicketUpdates(incidentToBeUpdated);
			previousCommentsRs = staffDaoObj.getPreviousUpdates(previousCommentsQuery);
			if(previousCommentsRs.next() == true){
				
				do{
					
					TicketUpdateBean updateBean = new TicketUpdateBean();
					updateBean = setUpdateBeans(previousCommentsRs,updateBean);
					prevCommentsList.add(updateBean);
					
				}while(previousCommentsRs.next() == true);
				
			}else{
				
				return null;
			}
			
		}catch(Exception ex){
			
			System.out.println("FlowController -- getTicketUpdates :"+ex.getMessage());
			
		}
		return prevCommentsList;
		
	}

	private IncidentsBean setIncidentsBeans(ResultSet incidentsRs, IncidentsBean incidentsBeanObj, String call_From_Method) throws Exception {
		
		incidentsBeanObj.setIncidentID(incidentsRs.getString("IncidentID"));
		incidentsBeanObj.setTitle(incidentsRs.getString("Title"));
		incidentsBeanObj.setAssignee(incidentsRs.getString("Assignee"));
		incidentsBeanObj.setSla_target_date(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(incidentsRs.getTimestamp("SLATargetDate")));
		incidentsBeanObj.setCreatedDate(incidentsRs.getString("DateCreated"));
		incidentsBeanObj.setClosedDate(incidentsRs.getString("DateClosed"));
		incidentsBeanObj.setWsrCurrentStatus(incidentsRs.getString("Status"));
		incidentsBeanObj.setSm9Status(incidentsRs.getString("SM7Status"));
		incidentsBeanObj.setRaisedBy(incidentsRs.getString("ContactWhoRaised"));
		incidentsBeanObj.setAssetName(incidentsRs.getString("Asset"));
		incidentsBeanObj.setCountry(incidentsRs.getString("Country"));
		incidentsBeanObj.setClosureCode(incidentsRs.getString("ClosureCode"));
		incidentsBeanObj.setIsUpdated(incidentsRs.getString("IsUpdated"));
		
		if(call_From_Method.equals("getUserIncidentInQueue") || call_From_Method.equals("getIncidentsInGroupQueue")){
			
			incidentsBeanObj.setSeverity(incidentsRs.getInt("Severity"));
			
		}else{// if(call_From_Method.equals("searchTicketByIncidentID") || call_From_Method.equals("searchTicketByKeyword")||call_From_Method.equals("getMyGroupsIncInQueueQuery")){
			
			incidentsBeanObj.setSeverity(incidentsRs.getInt("Sev"));
			incidentsBeanObj.setdomainName(incidentsRs.getString("Domain_Name"));
			incidentsBeanObj.setsubDomainName(incidentsRs.getString("SubDomain"));
			incidentsBeanObj.setrootCauseName(incidentsRs.getString("RootCause"));
			incidentsBeanObj.setupdateCountryName(incidentsRs.getString("CountryName"));
		}
		return incidentsBeanObj;
	}
	
	
	private TicketUpdateBean setUpdateBeans(ResultSet previousCommentsRs, TicketUpdateBean updateBean) throws SQLException {
		
		updateBean.setIncidentId(previousCommentsRs.getString("IncidentID"));
		updateBean.setUpdateDate(previousCommentsRs.getString("UpdateDate"));
		updateBean.setComment(previousCommentsRs.getString("Update"));
		updateBean.setStatus(previousCommentsRs.getString("Status"));
		updateBean.setUpdateId(previousCommentsRs.getInt("UpdateID"));
		updateBean.setIsValid(previousCommentsRs.getString("IsValid"));
		updateBean.setFirstName(previousCommentsRs.getString("FirstName"));
		updateBean.setUpdateBy(previousCommentsRs.getString("UpdateBy"));
		updateBean.setActionOn(previousCommentsRs.getString("ActionOn"));
		
		return updateBean;
	}


	
}
