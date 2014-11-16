package com.telegenda.business;

import java.util.Date;

public class Program {
	
	private int airingAttrib;
	private int catId; 
	private Date endTime;
	private int programId;
	private Date startTime;
	private String title;
	
	public Program(int airingAttrib, int catId, long endTime, int programId,
			long startTime, String title) {
		this.airingAttrib = airingAttrib;
		this.catId = catId;
		this.endTime = new Date(endTime*1000);
		this.programId = programId;
		this.startTime = new Date(startTime*1000);
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
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = new Date(endTime*1000);
	}
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = new Date(startTime*1000);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
