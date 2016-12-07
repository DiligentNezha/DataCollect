package io.vicp.goradical.datacollect.entity;

import java.util.Date;

public class CollectRecord {
	private int collectRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private Date collectDate;

	public CollectRecord() {
	}

	public int getCollectRecordId() {
		return collectRecordId;
	}

	public void setCollectRecordId(int collectRecordId) {
		this.collectRecordId = collectRecordId;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	@Override
	public String toString() {
		return "CollectRecord{" +
				"collectRecordId=" + collectRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", collectDate=" + collectDate +
				'}';
	}
}
