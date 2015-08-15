package com.telegenda.business;

public class Listing {
	
	//search: copyText, episodeTitle, title
	
	private String title; //"" title
	private String copyText; // (description)
	private String episodeTitle; //ProgramSchedule / EpisodeTitle
	private String channel; //Channels / Name
	private long startTime; //"" /startTime (long)
	private long endTime; //"" / endTime (long)
	
	public Listing()
	{
		title = "";
		copyText = "";
		episodeTitle = "";
		channel = "";
		startTime = 0;
		endTime = 0;
	}
	
	public Listing(String title, String copyText, String episodeTitle, String channel, long startTime, long endTime) 
	{
		this.title = title;
		this.copyText = copyText;
		this.episodeTitle = episodeTitle;
		this.channel = channel;
		this.startTime = startTime*1000;
		this.endTime = endTime*1000;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return copyText;
	}

	public void setDescription(String description) {
		this.copyText = description;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	@Override
	public String toString() {
		return title + " on " + channel + " from " + startTime + " until " + endTime;
	}
}
