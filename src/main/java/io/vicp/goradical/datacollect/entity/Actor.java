package io.vicp.goradical.datacollect.entity;

public class Actor{
	private int actorId;
	private String actorName;
	private String description;

	public Actor() {
	}

	public int getActorId() {
		return actorId;
	}

	public void setActorId(int actorId) {
		this.actorId = actorId;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Actor{" +
				"actorId=" + actorId +
				", actorName='" + actorName + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
