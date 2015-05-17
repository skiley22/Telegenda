package com.telegenda.business;

import java.util.Date;

public class Program implements Comparable<Program>{
	
	//search: copyText, episodeTitle, title
	
	private String title; //"" title
	private String description; //ProgramSchedule / CopyText
	private String episodeTitle; //ProgramSchedule / EpisodeTitle
	private String channel; //Channels / Name
	private Date startTime; //"" /startTime (long)
	private Date endTime; //"" / endTime (long)
	
	public Program(String title, String description, String episodeTitle, String channel, long startTime, long endTime) 
	{
		this.title = title;
		this.description = description;
		this.episodeTitle = episodeTitle;
		this.channel = channel;
		this.startTime = new Date(startTime*1000);
		this.endTime = new Date(endTime*1000);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	@Override
	public int compareTo(Program compareProgram) {
		return (int) (this.startTime.getTime() - compareProgram.getStartTime().getTime());
	}
}
