package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class SearchRecord {
	private int searchRecordId;
	private UserProfile userProfile;
	private String searchKeyWords;
	private int searchType;
	private Date searchDate;

	public SearchRecord() {
	}

	public int getSearchRecordId() {
		return searchRecordId;
	}

	public void setSearchRecordId(int searchRecordId) {
		this.searchRecordId = searchRecordId;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public String getSearchKeyWords() {
		return searchKeyWords;
	}

	public void setSearchKeyWords(String searchKeyWords) {
		this.searchKeyWords = searchKeyWords;
	}

	public int getSearchType() {
		return searchType;
	}

	public void setSearchType(int searchType) {
		this.searchType = searchType;
	}

	public Date getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(Date searchDate) {
		this.searchDate = searchDate;
	}

	@Override
	public String toString() {
		return "SearchRecord{" +
				"searchRecordId=" + searchRecordId +
				", userProfile=" + userProfile +
				", searchKeyWords='" + searchKeyWords + '\'' +
				", searchType=" + searchType +
				", searchDate=" + searchDate +
				'}';
	}
}
