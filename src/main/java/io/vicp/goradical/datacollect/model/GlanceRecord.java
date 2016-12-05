package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class GlanceRecord {
	private int glanceRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private Date glanceDate;

	public GlanceRecord() {
	}

	public int getGlanceRecordId() {
		return glanceRecordId;
	}

	public void setGlanceRecordId(int glanceRecordId) {
		this.glanceRecordId = glanceRecordId;
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

	public Date getGlanceDate() {
		return glanceDate;
	}

	public void setGlanceDate(Date glanceDate) {
		this.glanceDate = glanceDate;
	}

	@Override
	public String toString() {
		return "GlanceRecord{" +
				"glanceRecordId=" + glanceRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", glanceDate=" + glanceDate +
				'}';
	}
}
