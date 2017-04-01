package com.wsr.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.wsr.dao.StaffActivitiesDao;
import com.wsr.dao.StaffActivitiesDaoHelper;
import com.wsr.ui.ErrorLandingPage;
import com.wsr.ui.ManagerLandingPage;
import com.wsr.model.AuthoriseBean;

public class WsrLauncher {
	
	public static void main(String[] args) throws SQLException {
		
			String userRole = "";
			ResultSet authorisationRs = null;
			
			StaffActivitiesDao commondao = new StaffActivitiesDao();
			StaffActivitiesDaoHelper daohelper = new StaffActivitiesDaoHelper();
	//testing git commit		
			
	//		StaffLandingPage screenObj = new StaffLandingPage();
			ManagerLandingPage landingPageObj = new ManagerLandingPage();
			
			try{
				
				String authorisationQuery = daohelper.getAuthoriseUserQuery();				
				authorisationRs = commondao.authoriseUser(authorisationQuery);
		
				if(authorisationRs.next()) {
						
					AuthoriseBean loggedUserBean = new AuthoriseBean();
					
				loggedUserBean = setAuthoriseBean(authorisationRs,loggedUserBean);
					// Comment above line and use the below lines for Checking Manager functions till Dev and then remove..
					  /*loggedUserBean.setFirstName("Divya");
						loggedUserBean.setLastName("Saxena");
						loggedUserBean.setUserId("D.Saxena");
						loggedUserBean.setRole("Admin");*/
						
					
					landingPageObj.diplayManagerPageUI(loggedUserBean);
				}else{
					
					ErrorLandingPage.main(null);
				}
			}catch(Exception sql_ex){
				
				System.out.println("In WsrLauncher -- main() ::"+sql_ex);
			}
	}
	private static AuthoriseBean setAuthoriseBean(ResultSet authorisationRs,AuthoriseBean loggedUserBean) throws SQLException,Exception {
		
		loggedUserBean.setFirstName(authorisationRs.getString("Firstname"));
		loggedUserBean.setLastName(authorisationRs.getString("Lastname"));
		loggedUserBean.setUserId(authorisationRs.getString("UserID"));
		loggedUserBean.setRole(authorisationRs.getString("RoleName"));
			
		return loggedUserBean;
	}

}
