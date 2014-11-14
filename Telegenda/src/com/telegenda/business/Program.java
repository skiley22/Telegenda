package com.telegenda.business;

public class Program {
	
	private int airingAttrib;
	private int catId; 
	private int endTime;
	private int programId;
	private int startTime;
	private String title;
	
	public Program()
	{
		this.airingAttrib = 0;
		this.catId = 0;
		this.endTime = 0;
		this.programId = 0;
		this.startTime = 0;
		this.title = "title";
	}
	
	public Program(int airingAttrib, int catId, int endTime, int programId,
			int startTime, String title) {
		this.airingAttrib = airingAttrib;
		this.catId = catId;
		this.endTime = endTime;
		this.programId = programId;
		this.startTime = startTime;
		this.title = title;
	}
	public int getAiringAttrib() {
		return airingAttrib;
	}
	public void setAiringAttrib(int airingAttrib) {
		this.airingAttrib = airingAttrib;
	}
	public int getCatId() {
		return catId;
	}
	public void setCatId(int catId) {
		this.catId = catId;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
