package io.vicp.goradical.datacollect.model;

public class Type{
	private int typeId;
	private String typeName;
	private String description;

	public Type() {
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Type{" +
				"typeId=" + typeId +
				", typeName='" + typeName + '\'' +
				", description='" + description + '\'' +
				'}';
	}
}
