package io.vicp.goradical.datacollect.model;

public class Nation{
	private int nationId;
	private String nationName;
	private String description;

	public Nation() {
	}

	public int getNationId() {
		return nationId;
	}

	public void setNationId(int nationId) {
		this.nationId = nationId;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Nation{" +
				"nationId=" + nationId +
				", nationName='" + nationName + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
