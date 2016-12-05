package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class LikeRecord {
	private int likeRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private boolean like;
	private Date likeDate;

	public LikeRecord() {
	}

	public int getLikeRecordId() {
		return likeRecordId;
	}

	public void setLikeRecordId(int likeRecordId) {
		this.likeRecordId = likeRecordId;
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

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public Date getLikeDate() {
		return likeDate;
	}

	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}

	@Override
	public String toString() {
		return "LikeRecord{" +
				"likeRecordId=" + likeRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", like=" + like +
				", likeDate=" + likeDate +
				'}';
	}
}
