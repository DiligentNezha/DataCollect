package io.vicp.goradical.datacollect.entity;

public class FileNation {
	private int fileNationId;
	private FileInfo fileInfo;
	private Nation nation;

	public FileNation() {
	}

	public int getFileNationId() {
		return fileNationId;
	}

	public void setFileNationId(int fileNationId) {
		this.fileNationId = fileNationId;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Nation getNation() {
		return nation;
	}

	public void setNation(Nation nation) {
		this.nation = nation;
	}

	@Override
	public String toString() {
		return "FileNation{" +
				"fileNationId=" + fileNationId +
				", fileInfo=" + fileInfo +
				", nation=" + nation +
				'}';
	}
}
