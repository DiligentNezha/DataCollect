package io.vicp.goradical.datacollect.model;

import java.util.Date;
import java.util.List;

public class FileInfo {
	private int fileInfoId;
	private String fileId;
	private String FileName;
	private String cover;
	private List<Director> directorList;
	private List<Actor> actorList;
	private Category category;
	private List<Type> typeList;
	private List<Nation> nationList;
	private Date published;
	private int duration;
	private String alias;
	private String summary;
	private String youkuSrcUrl;
	private String youkuPlayUrl;
	private String youkuDetailUrl;
	private String detailUrl;
	private String downloadUrl;
	private ActionInfo actionInfo;

	public FileInfo() {
	}

	public int getFileInfoId() {
		return fileInfoId;
	}

	public void setFileInfoId(int fileInfoId) {
		this.fileInfoId = fileInfoId;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return FileName;
	}

	public void setFileName(String fileName) {
		FileName = fileName;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public List<Director> getDirectorList() {
		return directorList;
	}

	public void setDirectorList(List<Director> directorList) {
		this.directorList = directorList;
	}

	public List<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public List<Nation> getNationList() {
		return nationList;
	}

	public void setNationList(List<Nation> nationList) {
		this.nationList = nationList;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getYoukuSrcUrl() {
		return youkuSrcUrl;
	}

	public void setYoukuSrcUrl(String youkuSrcUrl) {
		this.youkuSrcUrl = youkuSrcUrl;
	}

	public String getYoukuPlayUrl() {
		return youkuPlayUrl;
	}

	public void setYoukuPlayUrl(String youkuPlayUrl) {
		this.youkuPlayUrl = youkuPlayUrl;
	}

	public String getYoukuDetailUrl() {
		return youkuDetailUrl;
	}

	public void setYoukuDetailUrl(String youkuDetailUrl) {
		this.youkuDetailUrl = youkuDetailUrl;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public ActionInfo getActionInfo() {
		return actionInfo;
	}

	public void setActionInfo(ActionInfo actionInfo) {
		this.actionInfo = actionInfo;
	}

	@Override
	public String toString() {
		return "FileInfo{" +
				"fileInfoId=" + fileInfoId +
				", fileId='" + fileId + '\'' +
				", FileName='" + FileName + '\'' +
				", cover='" + cover + '\'' +
				", directorList=" + directorList +
				", actorList=" + actorList +
				", category=" + category +
				", typeList=" + typeList +
				", nationList=" + nationList +
				", published=" + published +
				", duration=" + duration +
				", alias='" + alias + '\'' +
				", summary='" + summary + '\'' +
				", youkuSrcUrl='" + youkuSrcUrl + '\'' +
				", youkuPlayUrl='" + youkuPlayUrl + '\'' +
				", youkuDetailUrl='" + youkuDetailUrl + '\'' +
				", detailUrl='" + detailUrl + '\'' +
				", downloadUrl='" + downloadUrl + '\'' +
				", actionInfo=" + actionInfo +
				'}';
	}
}
