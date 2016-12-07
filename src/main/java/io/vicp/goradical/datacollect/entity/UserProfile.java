package io.vicp.goradical.datacollect.entity;

public class UserProfile {
	private int userProfileId;
	private int userId;
	private String userName;
	private String email;
	private String password;
	private int userLevel;
	private String headPhotoSmall;
	private String headPhotoMiddle;
	private String headPhotoLarge;
	private DetailsInfo detailsInfo;

	public UserProfile() {
	}

	public int getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}

	public String getHeadPhotoSmall() {
		return headPhotoSmall;
	}

	public void setHeadPhotoSmall(String headPhotoSmall) {
		this.headPhotoSmall = headPhotoSmall;
	}

	public String getHeadPhotoMiddle() {
		return headPhotoMiddle;
	}

	public void setHeadPhotoMiddle(String headPhotoMiddle) {
		this.headPhotoMiddle = headPhotoMiddle;
	}

	public String getHeadPhotoLarge() {
		return headPhotoLarge;
	}

	public void setHeadPhotoLarge(String headPhotoLarge) {
		this.headPhotoLarge = headPhotoLarge;
	}

	public DetailsInfo getDetailsInfo() {
		return detailsInfo;
	}

	public void setDetailsInfo(DetailsInfo detailsInfo) {
		this.detailsInfo = detailsInfo;
	}

	@Override
	public String toString() {
		return "UserProfile{" +
				"userProfileId=" + userProfileId +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				", userLevel=" + userLevel +
				", headPhotoSmall='" + headPhotoSmall + '\'' +
				", headPhotoMiddle='" + headPhotoMiddle + '\'' +
				", headPhotoLarge='" + headPhotoLarge + '\'' +
				", detailsInfo=" + detailsInfo +
				'}';
	}
}
