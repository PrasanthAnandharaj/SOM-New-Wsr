package com.wsr.dao;

public class CommonActivitiesDaoHelper {

	public String getSearchByIncIdQuery(String incidentID) {
		
		return "Select * from dbo.vTestTempTable where IncidentID = \'"+ incidentID +"\'";
	}
	
	public String getAllMembersQuery() {
		
		return "Select UserID from dbo.Profiles where RoleID <> 1";
	}
	
	public String getSearchByKeywordQuery(String searchKey) {
		
		return "Select * from dbo.vTestTempTable where Title like \'%"+searchKey+"%\' Order By Assignee,DateCreated ASC";
		
	}
	
	public String getAllDomainOptions() {
		
		return "Select DomainName from dbo.vDomains";
	}

	public String getAllSubDomainOptions() {
		
		return "Select SubDomain from dbo.vSubDomains";
	}

/*	public String getDomainIdQuery(String selectedDomain) {
		
		return "Select DomainId from dbo.vDomains where DomainName = \'" +selectedDomain+ "\'";
	}

	public String getSubDomainListQuery(String domainId) {
		System.out.println();
		return "Select SubDomain from dbo.vSubDomains where subDomainID like \'" + domainId + "%\'";
	}*/

	public String getAllRootCauseQuery() {
		
		return "Select RootCause from dbo.vRootCause";
	}

	public String getAllCountriesQuery() {
		
		return "Select CountryName from dbo.vUpdateCountry";
	}
	
	public String getDomainToSubDomainMappingQuery(String forDomain){
		
		return "SELECT Distinct a.SubDomain"
				+ " FROM dbo.vSubDomains a"
				+ " JOIN vDomains b ON a.SubDomainID"
				+ " LIKE CONCAT((Select DomainId from dbo.vDomains where DomainName =\'"+forDomain+"\'),\'%\')";
	}

}
