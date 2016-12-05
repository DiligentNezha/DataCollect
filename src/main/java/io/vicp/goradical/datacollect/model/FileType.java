package io.vicp.goradical.datacollect.model;

public class FileType {
	private int fileTypeId;
	private FileInfo fileInfo;
	private Type type;

	public FileType() {
	}

	public int getFileTypeId() {
		return fileTypeId;
	}

	public void setFileTypeId(int fileTypeId) {
		this.fileTypeId = fileTypeId;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FileType{" +
				"fileTypeId=" + fileTypeId +
				", fileInfo=" + fileInfo +
				", type=" + type +
				'}';
	}
}
