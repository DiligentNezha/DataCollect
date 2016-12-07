package io.vicp.goradical.datacollect.entity;

public class Director{
	private int directorId;
	private String directorName;
	private String description;

	public Director() {
	}

	public int getDirectorId() {
		return directorId;
	}

	public void setDirectorId(int directorId) {
		this.directorId = directorId;
	}

	public String getDirectorName() {
		return directorName;
	}

	public void setDirectorName(String directorName) {
		this.directorName = directorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Director{" +
				"directorId=" + directorId +
				", directorName='" + directorName + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
