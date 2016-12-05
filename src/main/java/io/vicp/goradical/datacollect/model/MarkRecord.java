package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class MarkRecord {
	private int markRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private int mark;
	private Date markDate;

	public MarkRecord() {
	}

	public int getMarkRecordId() {
		return markRecordId;
	}

	public void setMarkRecordId(int markRecordId) {
		this.markRecordId = markRecordId;
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

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public Date getMarkDate() {
		return markDate;
	}

	public void setMarkDate(Date markDate) {
		this.markDate = markDate;
	}

	@Override
	public String toString() {
		return "MarkRecord{" +
				"markRecordId=" + markRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", mark=" + mark +
				", markDate=" + markDate +
				'}';
	}
}
