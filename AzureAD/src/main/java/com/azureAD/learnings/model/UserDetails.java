package com.azureAD.learnings.model;

import java.util.List;

public class UserDetails {
	
	private List<String> businessPhones;
	private String displayName;
	private String givenName;
	private String jobTitle;
	private String mail;
	private String mobilePhone;
	private String officeLocation;
	private String preferredLanguage;
	private String surname;
	private String userPrincipalName;
	private String id;
	public synchronized List<String> getBusinessPhones() {
		return businessPhones;
	}
	public synchronized void setBusinessPhones(List<String> businessPhones) {
		this.businessPhones = businessPhones;
	}
	public synchronized String getDisplayName() {
		return displayName;
	}
	public synchronized void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public synchronized String getGivenName() {
		return givenName;
	}
	public synchronized void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public synchronized String getJobTitle() {
		return jobTitle;
	}
	public synchronized void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public synchronized String getMail() {
		return mail;
	}
	public synchronized void setMail(String mail) {
		this.mail = mail;
	}
	public synchronized String getMobilePhone() {
		return mobilePhone;
	}
	public synchronized void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public synchronized String getOfficeLocation() {
		return officeLocation;
	}
	public synchronized void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}
	public synchronized String getPreferredLanguage() {
		return preferredLanguage;
	}
	public synchronized void setPreferredLanguage(String preferredLanguage) {
		this.preferredLanguage = preferredLanguage;
	}
	public synchronized String getSurname() {
		return surname;
	}
	public synchronized void setSurname(String surname) {
		this.surname = surname;
	}
	public synchronized String getUserPrincipalName() {
		return userPrincipalName;
	}
	public synchronized void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}
	public synchronized String getId() {
		return id;
	}
	public synchronized void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "UserDetails [businessPhones=" + businessPhones + ", displayName=" + displayName + ", givenName="
				+ givenName + ", jobTitle=" + jobTitle + ", mail=" + mail + ", mobilePhone=" + mobilePhone
				+ ", officeLocation=" + officeLocation + ", preferredLanguage=" + preferredLanguage + ", surname="
				+ surname + ", userPrincipalName=" + userPrincipalName + ", id=" + id + "]";
	}
	
}
