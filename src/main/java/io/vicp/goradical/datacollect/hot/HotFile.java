package io.vicp.goradical.datacollect.hot;

import java.io.Serializable;

public class HotFile implements Serializable{
	private static final long serialVersionUID = 1L;
	private String fileName;
	private String fileType;
	private String playUrl;
	private String downloadUrl;
	
	public HotFile() {
	}
	
	public HotFile(String fileName, String fileType, String playUrl, String downloadUrl) {
		this.fileName = fileName;
		this.fileType = fileType;
		this.playUrl = playUrl;
		this.downloadUrl = downloadUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	@Override
	public String toString() {
		return "HotFile [fileName=" + fileName + ", fileType=" + fileType + ", playUrl=" + playUrl + ", downloadUrl="
				+ downloadUrl + "]";
	}
	
}
