package com.wsr.dao;

import java.util.List;

import com.wsr.model.IncidentsBean;
import com.wsr.model.TicketUpdateBean;

public class StaffActivitiesDaoHelper {

	public String getAuthoriseUserQuery(){
		
		return "Select * from dbo.vProfiles where UserID =\'"+System.getProperty("user.name")+"\'";
	}
	
	public String getIncidentsInLoggedUserQuery(){
		System.out.println("Select * from dbo.vOpenTicketsQueue where Assignee = \'"+System.getProperty("user.name")+"\'" + "Order By SLATargetDate Asc");
		return "Select * from dbo.vOpenTicketsQueue where Assignee = \'"+System.getProperty("user.name")+"\'" + "Order By SLATargetDate Asc";
	//	return "Select * from dbo.vIncidentsInQueue where Assignee = 'Sandhya.S.Parimi' Order By SLATargetDate Asc";
	}

	public String getTotTktHandledQuery() {
		
		return "Select COUNT(DISTINCT IncidentID) from dbo.vTestTempTable where Assignee = \'"+System.getProperty("user.name")+"\'";
	}

	public String getMyGroupsIncInQueueQuery() {
		
		System.out.println("Select * from dbo.vTestTempTable where  (DateClosed is Null AND SM7Status = \'Accepted\' AND [Status] <> \'BAM : Closed\' ) order by Assignee");
		return "Select * from dbo.vTestTempTable where  (DateClosed is Null AND SM7Status = \'Accepted\' AND [Status] <> \'BAM : Closed\' ) order by Assignee";

		
	}
	
	public String getTotalTktHandledCountQuery(String loggedUserId) {
		
		return "Select COUNT(IncidentID) TotalCount from dbo.vTestTempTable where Assignee = \'"+loggedUserId+"\'";
	}

	public String getOpenTicketCountQuery(String loggedUserId) {
		
		return "Select COUNT(IncidentID) OpenCount from dbo.vTestTempTable where (Assignee = \'"+loggedUserId+"\'  AND Status <> \'BAM : Closed\' AND SM7Status <> \'Closed\')";
	}

	public String getAllMemberDetails() {
		
		return "Select p.UserID,p.FirstName,p.LastName,r.RoleName,p.Email,p.ContactNo,p.IsValid "
				+"from dbo.Profiles p join dbo.Roles r "
				+"on p.RoleID = r.RoleID "
				+"where isValid = \'1\' order by UserID asc";
		
	}

	public String getInsertQuery(List<String> addMemberInputDetails) {
		
		System.out.println("insert into dbo.Profiles values (\'"+addMemberInputDetails.get(0)+"\',\'"+addMemberInputDetails.get(1)+"\',\'"+addMemberInputDetails.get(2)+"\',\'"+addMemberInputDetails.get(3)+"\',"+addMemberInputDetails.get(4)+",NULL,"+addMemberInputDetails.get(5)+",NULL,NULL,0,1,\'"+addMemberInputDetails.get(6)+"\')");
		return "insert into dbo.Profiles values (\'"+addMemberInputDetails.get(0)+"\',\'"+addMemberInputDetails.get(1)+"\',\'"+addMemberInputDetails.get(2)+"\',\'"+addMemberInputDetails.get(3)+"\',"+addMemberInputDetails.get(4)+",NULL,"+addMemberInputDetails.get(5)+",NULL,NULL,0,1,\'"+addMemberInputDetails.get(6)+"\')";
	}

	public String getFindRoleId(String role) {
		
		return "select RoleID from dbo.Roles where RoleName = \'"+role+"\'";
		
	}

	public String getEditMemberQuery(List<String> editMemberList) {
		
		return "Update dbo.Profiles "
				+"set FirstName = \'"+editMemberList.get(1)+"\',LastName=\'"+editMemberList.get(2)+"\',Email=\'"+editMemberList.get(3)+"\',RoleID="+editMemberList.get(4)+",ContactNo="+editMemberList.get(5)+",Alias=\'"+editMemberList.get(6)+"\' " 
				+"where UserID=\'"+editMemberList.get(0)+"\'";
	}

	public String getDeleteQuery(String userToDelete) {
		
		return "Update dbo.Profiles set IsValid = 0 where UserID = \'"+userToDelete+"\'";
		
	}

	public String getTicketUpdates(String incidentToBeUpdated) {
		
		return "Select distinct * from dbo.vHistoricActivities "+
				"where IncidentID = \'"+incidentToBeUpdated+"\' AND isValid = 1 "+
				"order by UpdateID asc";
		
		}

	public String getAllStatusOptionsQuery() {
		
		return "Select distinct Status from dbo.Status";
		
	}
	
	public String getAllClosureCodeOptionQuery(){
		
		return "Select * from dbo.ClosureCodes";
	}

	public String getInvalidateAnUpdate(int updateIdToInvalidate) {
		
		return "Update dbo.HistoricActivities set IsValid = \'0\' where UpdateID = \'"+updateIdToInvalidate+"\'";
	}

	public String getProgressUpdateQuery(TicketUpdateBean tktUpdateObj) {
				 
		return "insert into dbo.HistoricActivities (IncidentID,StatusID,ActionOn,[Update],UpdateDate,UpdateBy,IsValid) " +
						"values ("+"\'"+tktUpdateObj.getIncidentId()+"\',(Select distinct StatusID from dbo.Status where Status=\'"+tktUpdateObj.getStatus()+"\'),\'"+tktUpdateObj.getActionOn()+
						"\',\'"+tktUpdateObj.getComment()+"\',\'"+tktUpdateObj.getUpdateDate()+"\',\'"+tktUpdateObj.getUpdateBy()+"\',\'"+tktUpdateObj.getIsValid()+"\')";
	}

	public String getProgressiveUpdateInIncidentQuery(IncidentsBean incBeanObj) {

		return "update dbo.TestTempTable set"+ 
				"[Status]=\'"+incBeanObj.getWsrCurrentStatus()+"\',"+
				"IsUpdated=\'1\',"+
				"ClosureCode=\'"+incBeanObj.getClosureCode()+"\',"+
				"DomainID=(Select distinct DomainId from dbo.vDomains where DomainName=\'"+incBeanObj.getdomainName()+"\'),"+
				"subDomainID=(Select distinct SubDomainID from dbo.vSubDomains where SubDomain=\'"+incBeanObj.getsubDomainName()+"\'),"+
				"RootCauseID=(Select distinct RootCauseId from dbo.vRootCause where RootCause=\'"+incBeanObj.getrootCauseName()+"\'),"+
				"UpdateCountryID=(Select distinct CountryId from dbo.vUpdateCountry where CountryName=\'"+incBeanObj.getupdateCountryName()+"\')"+
				"where IncidentID =\'"+incBeanObj.getIncidentID()+"\'";
	}

}
