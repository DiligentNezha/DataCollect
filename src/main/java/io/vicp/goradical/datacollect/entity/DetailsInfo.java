package io.vicp.goradical.datacollect.entity;

import java.util.Date;

public class DetailsInfo {
	private int detailsInfoId;
	private String nickName;
	private String gender;
	private String region;
	private Date birthday;
	private String constellation;
	private String personalizedSignatures;

	public DetailsInfo() {
	}

	public int getDetailsInfoId() {
		return detailsInfoId;
	}

	public void setDetailsInfoId(int detailsInfoId) {
		this.detailsInfoId = detailsInfoId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getConstellation() {
		return constellation;
	}

	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}

	public String getPersonalizedSignatures() {
		return personalizedSignatures;
	}

	public void setPersonalizedSignatures(String personalizedSignatures) {
		this.personalizedSignatures = personalizedSignatures;
	}

	@Override
	public String toString() {
		return "DetailsInfo{" +
				"detailsInfoId=" + detailsInfoId +
				", nickName='" + nickName + '\'' +
				", gender='" + gender + '\'' +
				", region='" + region + '\'' +
				", birthday=" + birthday +
				", constellation='" + constellation + '\'' +
				", personalizedSignatures='" + personalizedSignatures + '\'' +
				'}';
	}
}
