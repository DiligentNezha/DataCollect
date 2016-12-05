package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class UserRecommend {
	private int userRecommendId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private boolean recommend;
	private Date recommendDate;

	public UserRecommend() {
	}

	public int getUserRecommendId() {
		return userRecommendId;
	}

	public void setUserRecommendId(int userRecommendId) {
		this.userRecommendId = userRecommendId;
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

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getRecommendDate() {
		return recommendDate;
	}

	public void setRecommendDate(Date recommendDate) {
		this.recommendDate = recommendDate;
	}

	@Override
	public String toString() {
		return "UserRecommend{" +
				"userRecommendId=" + userRecommendId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", recommend=" + recommend +
				", recommendDate=" + recommendDate +
				'}';
	}
}
