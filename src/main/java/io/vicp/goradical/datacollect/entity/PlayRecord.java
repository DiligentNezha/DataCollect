package io.vicp.goradical.datacollect.entity;

import java.util.Date;

public class PlayRecord {
	private int playRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private Date playDate;

	public PlayRecord() {
	}

	public int getPlayRecordId() {
		return playRecordId;
	}

	public void setPlayRecordId(int playRecordId) {
		this.playRecordId = playRecordId;
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

	public Date getPlayDate() {
		return playDate;
	}

	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	@Override
	public String toString() {
		return "PlayRecord{" +
				"playRecordId=" + playRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", playDate=" + playDate +
				'}';
	}
}
