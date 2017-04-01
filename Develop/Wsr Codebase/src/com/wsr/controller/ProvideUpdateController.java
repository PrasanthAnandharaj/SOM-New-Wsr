package com.wsr.controller;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wsr.dao.StaffActivitiesDao;
import com.wsr.dao.StaffActivitiesDaoHelper;
import com.wsr.model.IncidentsBean;
import com.wsr.model.TicketUpdateBean;

@SuppressWarnings({ "rawtypes","finally", "unchecked" })
public class ProvideUpdateController {

	String daoQuery = "";
	private static int oneRowAffected = 1;
	private static String STATUSCLOSED = "BAM : Closed";
	private static String DefaultSelection = "~~Please Select~~";
	
	ResultSet controllerRs;
	List commonUtilList = new ArrayList<>();
	List<TicketUpdateBean> provideUpdatesUtilLs = new ArrayList<>();
	
	IncidentsBean incBeanObj;
	TicketUpdateBean tktUpdateObj = new  TicketUpdateBean();
	StaffActivitiesDao staffDaoObj = new StaffActivitiesDao();
	StaffActivitiesDaoHelper staffDaoHelperObj = new StaffActivitiesDaoHelper();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public List<String> getdropDownOptionsAsList(String dropDownField) {
		
		//Clearing the objects for avoiding Overwrite..
		controllerRs=null;
		commonUtilList.clear();
		
		try{
			switch(dropDownField){
				
				case "Status" :
					daoQuery = staffDaoHelperObj.getAllStatusOptionsQuery();
					break;
					
				case "ClosureCode" :
					daoQuery = staffDaoHelperObj.getAllClosureCodeOptionQuery();
					break;
			}
			System.out.println("DaoQuery is "+daoQuery);
			controllerRs = staffDaoObj.getAllStatusOption(daoQuery);
			if(controllerRs.next() == true){
				do{		
					if(dropDownField.equals("Status")){
						commonUtilList.add(controllerRs.getString("Status"));
					}else{
						commonUtilList.add(controllerRs.getString("ClosureCode"));
					}
				}while(controllerRs.next() == true);
				
			}else{
				commonUtilList = null;
			}
			
		}catch(Exception e){
			System.out.println("ProvideUpdateController -- getStatusOptionsAsList :"+e.getMessage());
		}finally{
			return commonUtilList;
		}
		
	}

	public boolean invalidateAnUpdateComment(int updateIdToInvalidate) {
		
		boolean invalidateSuccessFlag = false;
		try{
			daoQuery = staffDaoHelperObj.getInvalidateAnUpdate(updateIdToInvalidate);
			int rowAffected = staffDaoObj.invalidateSelectedComment(daoQuery);
			invalidateSuccessFlag = (rowAffected == 1) ? true : false;
			System.out.println("Invalidate Success Flag returned :"+invalidateSuccessFlag);
		}catch(Exception e){
			System.out.println("ProvideUpdateController -- invalidateAnUpdateComment :"+e.getMessage());
			invalidateSuccessFlag = false;
		}finally{
			return invalidateSuccessFlag;
		}	
	}

	public boolean TicketProgressUpdate(Map<String,String> provideUpdateUtilMap) {
		
		boolean controllerFeedBack = false;
		tktUpdateObj = new TicketUpdateBean();
		tktUpdateObj = setProvideUpdateBean(provideUpdateUtilMap,tktUpdateObj);
		try{
			
			String progressUpdateQuery = staffDaoHelperObj.getProgressUpdateQuery(tktUpdateObj);
			int insertFeedBack = staffDaoObj.insertProgressiveComment(progressUpdateQuery);
			if(insertFeedBack == oneRowAffected){
			
				incBeanObj = new IncidentsBean();
				incBeanObj = setIncidentsBeanForProvieUpdate(provideUpdateUtilMap,incBeanObj);
				String updateInIncidentsQuery = staffDaoHelperObj.getProgressiveUpdateInIncidentQuery(incBeanObj);
				System.out.println(updateInIncidentsQuery);
				int updateFeedBack = staffDaoObj.UpdateIncidentForComment(updateInIncidentsQuery);
				if(updateFeedBack == oneRowAffected){
					controllerFeedBack =  true;
				}else{
					controllerFeedBack =  false;
					System.out.println("Multiple updates here");
				}
			}else{
				controllerFeedBack =  false;
			}
		}catch(Exception e){
			System.out.println("ProvideUpdateController -- TicketProgressUpdate :"+e.getMessage());
		}finally{
			return controllerFeedBack;
		}
	}

	private IncidentsBean setIncidentsBeanForProvieUpdate(Map<String, String> provideUpdateUtilMap, IncidentsBean incBeanObj) {
		
		incBeanObj.setIncidentID(provideUpdateUtilMap.get("TicketId"));
		incBeanObj.setWsrCurrentStatus(provideUpdateUtilMap.get("Status"));
		
		if(!provideUpdateUtilMap.get("ClosureCode").equals(DefaultSelection)
				&& provideUpdateUtilMap.get("Status").equals(STATUSCLOSED)){
			
			incBeanObj.setClosureCode(provideUpdateUtilMap.get("ClosureCode"));
			
		}if(!provideUpdateUtilMap.get("Domain").equals(DefaultSelection)){
			
			incBeanObj.setdomainName(provideUpdateUtilMap.get("Domain"));
			
		}if(!provideUpdateUtilMap.get("SubDomain").equals(DefaultSelection)){
			
			incBeanObj.setsubDomainName(provideUpdateUtilMap.get("SubDomain"));		
			
		}if(!provideUpdateUtilMap.get("RootCause").equals(DefaultSelection)){
			
			incBeanObj.setrootCauseName(provideUpdateUtilMap.get("RootCause"));
			
		}if(!provideUpdateUtilMap.get("IssueIn").equals(DefaultSelection)){
			
			incBeanObj.setupdateCountryName(provideUpdateUtilMap.get("IssueIn"));
			
		}
		
		return incBeanObj;
	}

	private TicketUpdateBean setProvideUpdateBean(Map<String,String> provideUpdateUtilMap, TicketUpdateBean tktUpdateObjs) {
		
		tktUpdateObj.setIncidentId(provideUpdateUtilMap.get("TicketId"));
		tktUpdateObj.setUpdateDate(sdf.format(new Date()));
		tktUpdateObj.setComment(provideUpdateUtilMap.get("Update"));
		tktUpdateObj.setStatus(provideUpdateUtilMap.get("Status"));
		tktUpdateObj.setUpdateBy(System.getProperty("user.name"));
		tktUpdateObj.setActionOn(System.getProperty("user.name"));
		tktUpdateObj.setIsValid("1");
		
		return tktUpdateObj;
	}
	
}
