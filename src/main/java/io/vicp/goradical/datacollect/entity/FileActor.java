package io.vicp.goradical.datacollect.entity;

public class FileActor {
	private int fileActorId;
	private FileInfo fileInfo;
	private Actor actor;

	public FileActor() {
	}

	public int getFileActorId() {
		return fileActorId;
	}

	public void setFileActorId(int fileActorId) {
		this.fileActorId = fileActorId;
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public void setFileInfo(FileInfo fileInfo) {
		this.fileInfo = fileInfo;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	@Override
	public String toString() {
		return "FileActor{" +
				"fileActorId=" + fileActorId +
				", fileInfo=" + fileInfo +
				", actor=" + actor +
				'}';
	}
}
