package com.telegenda.business;

import java.util.List;

public class ChannelSchedule 
{	
	private Channel channel;
	private List<Program> programSchedules;
		
	public ChannelSchedule(Channel channel, List<Program> programSchedules) {
		this.channel = channel;
		this.programSchedules = programSchedules;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	public List<Program> getProgramSchedules() {
		return programSchedules;
	}
	public void setProgramSchedules(List<Program> programSchedules) {
		this.programSchedules = programSchedules;
	}	
}
