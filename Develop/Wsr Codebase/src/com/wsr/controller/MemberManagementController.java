package com.wsr.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wsr.dao.StaffActivitiesDaoHelper;
import com.wsr.dao.WsrDbAccessor;
import com.wsr.model.MemberBean;

public class MemberManagementController {

	ResultSet controllerRs = null;
	
	WsrDbAccessor dbAcceessorObj = new WsrDbAccessor();
	StaffActivitiesDaoHelper daoObj = new StaffActivitiesDaoHelper();
	List<MemberBean> allMemberDetailsList = new ArrayList<MemberBean>();
	
	public List<MemberBean> getAllMemberList() {
		
		controllerRs = null;
		try{
			controllerRs = dbAcceessorObj.queryToDb(daoObj.getAllMemberDetails());
			if(controllerRs.next() == true){
				do{
					MemberBean memberBean = new MemberBean();
					setBean(controllerRs,memberBean);
					allMemberDetailsList.add(memberBean);
				}while(controllerRs.next() == true);
			}else{		
				return null;
			}
		}catch(Exception ex){
			System.out.println("MemberManagementController :: getAllMemberList"+ex.getMessage());
		}
		return allMemberDetailsList;
	}
	
	public boolean addOrUpdateMember(String actionOnMember,List<String> memberInputDetails) {
		
		//Clearing the Rs object..
		controllerRs = null;		
		int roleId = 0,no_of_row_affected = 0;
	
		try{		
			//getting the Role Id(int) from Role Name selected the drop down..
			controllerRs = dbAcceessorObj.queryToDb(daoObj.getFindRoleId(memberInputDetails.get(4)));
			if(controllerRs.next() == true ){
				roleId = controllerRs.getInt("RoleID");
				//Setting the List with Role ID got from the previous query..
				memberInputDetails.set(4, String.valueOf(roleId));
			}
			String memberActionQuery = (actionOnMember.equals("AddMember")) ? 
					daoObj.getInsertQuery(memberInputDetails) : daoObj.getEditMemberQuery(memberInputDetails);
			//returns the number of rows affected into the variable [No_of_row_affected]
			no_of_row_affected = dbAcceessorObj.performCudIntoDb(memberActionQuery);		
		}catch(Exception ex){		
			System.out.println("MemberManagementController :: addMember"+ex.getMessage());
		}
		if(no_of_row_affected == 1)
			return  true;
		else
			return false;
	}
	
	public boolean invalidateMember(String userToDelete) {
		
		controllerRs = null;
		int no_of_row_affected = 0;
		String invalidateQuery = "";
		
		try{
			invalidateQuery = daoObj.getDeleteQuery(userToDelete);
			no_of_row_affected = dbAcceessorObj.performCudIntoDb(invalidateQuery);	
		}catch(Exception ex){
			System.out.println("MemberManagementController :: invalidateMember"+ex.getMessage());
		}
		if(no_of_row_affected == 1)
			return  true;
		else
			return false;
		
	}
	

	private void setBean(ResultSet allMembersDetailsRs, MemberBean memberBean) throws SQLException {
		
		memberBean.setUserId(allMembersDetailsRs.getString("UserID"));
		memberBean.setFirstName(allMembersDetailsRs.getString("FirstName"));
		memberBean.setLastName(allMembersDetailsRs.getString("LastName"));
		memberBean.setRole(allMembersDetailsRs.getString("RoleName"));
		memberBean.setEmailId(allMembersDetailsRs.getString("Email"));
		memberBean.setContactnum(allMembersDetailsRs.getString("ContactNo"));
		memberBean.setAliasId(allMembersDetailsRs.getString("IsValid"));
		
		
	}
	
	/*	public boolean addMember(List<String> addMemberInputDetails) {
	
	//Clearing the Rs object..
	controllerRs = null;		
	int roleId = 0,No_of_row_affected = 0;

	try{		
		//getting the Role Id(int) from Role Name selected the drop down..
		controllerRs = dbAcceessorObj.queryToDb(daoObj.getFindRoleId(addMemberInputDetails.get(4)));
		if(controllerRs.next() == true ){
			roleId = controllerRs.getInt("RoleID");
			//Setting the List with Role ID got from the previous query..
			addMemberInputDetails.set(4, String.valueOf(roleId));
		}
		String addMemberQuery = daoObj.getInsertQuery(addMemberInputDetails);
		//returns the number of rows affected into the variable [No_of_row_affected]
		No_of_row_affected = dbAcceessorObj.performCudIntoDb(addMemberQuery);		
	}catch(Exception ex){		
		System.out.println("MemberManagementController :: addMember"+ex.getMessage());
	}
	boolean addMemberFeedback = (No_of_row_affected == 1) ?  true :  false;
	
	return addMemberFeedback;
	
}

public boolean editMember(List<String> editMemberList) {
	
	
	int roleId = 0;
	int No_of_row_affected = 0;
	controllerRs = null;
	
	
	try{
		//Setting the roleId for the selected role name in the list before update..
		controllerRs = dbAcceessorObj.queryToDb(daoObj.getFindRoleId(editMemberList.get(4)));
		if(controllerRs.next() == true){
			
			roleId = controllerRs.getInt("RoleID");
			editMemberList.set(4, String.valueOf(roleId));
		}
		
	
		String editQuery = daoObj.getEditMemberQuery(editMemberList);
		No_of_row_affected = dbAcceessorObj.performCudIntoDb(editQuery);
		
	}catch(Exception ex){
		
		System.out.println("MemberManagementController :: editMemberList"+ex.getMessage());
	}
	boolean editFeedback = (No_of_row_affected == 1) ?  true :  false;
	
	return editFeedback;
}*/

}
