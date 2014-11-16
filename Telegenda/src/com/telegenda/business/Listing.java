package com.telegenda.business;

public class Listing 
{
	private Channel channel;
	private Program program;
		
	public Listing(Channel channel, Program program) 
	{
		this.channel = channel;
		this.program = program;
	}
	public Channel getChannel() 
	{
		return channel;
	}
	public void setChannel(Channel channel) 
	{
		this.channel = channel;
	}
	public Program getProgram() 
	{
		return program;
	}
	public void setProgram(Program program) 
	{
		this.program = program;
	}
}
