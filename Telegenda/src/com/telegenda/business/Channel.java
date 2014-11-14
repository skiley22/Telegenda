package com.telegenda.business;

public class Channel {

	private String fullName;
	private String name;
	private String number;
	private int sourceId;
	
	public Channel()
	{
		this.fullName = "fullname";
		this.name = "name";
		this.number = "number";
		this.sourceId = 0;
	}
	
	public Channel(String fullName, String name, String number, int sourceId) {
		this.fullName = fullName;
		this.name = name;
		this.number = number;
		this.sourceId = sourceId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	
}
