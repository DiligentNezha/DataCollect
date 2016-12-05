package io.vicp.goradical.datacollect.model;

import java.util.Date;

public class CommentRecord {
	private int commentRecordId;
	private UserProfile userProfile;
	private FileInfo fileInfo;
	private String comment;
	private Date commentDate;

	public CommentRecord() {
	}

	public int getCommentRecordId() {
		return commentRecordId;
	}

	public void setCommentRecordId(int commentRecordId) {
		this.commentRecordId = commentRecordId;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	@Override
	public String toString() {
		return "CommentRecord{" +
				"commentRecordId=" + commentRecordId +
				", userProfile=" + userProfile +
				", fileInfo=" + fileInfo +
				", comment='" + comment + '\'' +
				", commentDate=" + commentDate +
				'}';
	}
}
