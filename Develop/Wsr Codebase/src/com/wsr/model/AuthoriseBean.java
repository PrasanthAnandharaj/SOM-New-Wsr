package com.wsr.model;

import java.util.Date;

public class AuthoriseBean {

	private String userId;
	private String firstName;
	private String lastName;
	private String role;
	
	private String incidentId;
	private String interactionId;
	private int sev;
	private Date dateOpened;
	private Date dateClosed;
	private String assignee;
	private String status;
	private String title;
	private String contactWhoRaised;
	private Date SLATargetDate;
	private int isUpdated ;
	private String actionOn;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	
	
	public String getInteractionId() {
		return interactionId;
	}
	public void setInteractionId(String interactionId) {
		this.interactionId = interactionId;
	}
	
	
	public int getSev() {
		return sev;
	}
	public void setSev(int sev) {
		this.sev = sev;
	}
	
	
	public Date getDateOpened() {
		return dateOpened;
	}
	public void setDateOpened(Date dateOpened) {
		this.dateOpened = dateOpened;
	}
	
	
	public Date getDateClosed() {
		return dateClosed;
	}
	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}
	
	
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	public String getContactWhoRaised() {
		return contactWhoRaised;
	}
	public void setContactWhoRaised(String contactWhoRaised) {
		this.contactWhoRaised = contactWhoRaised;
	}
	
	
	public Date getSLATargetDate() {
		return SLATargetDate;
	}
	public void setSLATargetDate(Date sLATargetDate) {
		SLATargetDate = sLATargetDate;
	}
	
	
	public int getIsUpdated() {
		return isUpdated;
	}
	public void setIsUpdated(int isUpdated) {
		this.isUpdated = isUpdated;
	}
	
	
	public String getActionOn() {
		return actionOn;
	}
	public void setActionOn(String actionOn) {
		this.actionOn = actionOn;
	}

}
