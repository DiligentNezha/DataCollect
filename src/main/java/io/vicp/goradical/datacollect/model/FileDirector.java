package io.vicp.goradical.datacollect.model;

public class FileDirector {
	private int fileDirectorId;
	private FileInfo fileInfo;
	private Director director;

	public FileDirector() {
	}

	public int getFileDirectorId() {
		return fileDirectorId;
	}

	public void setFileDirectorId(int fileDirectorId) {
		this.fileDirectorId = fileDirectorId;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	@Override
	public String toString() {
		return "FileDirector{" +
				"fileDirectorId=" + fileDirectorId +
				", fileInfo=" + fileInfo +
				", director=" + director +
				'}';
	}
}
