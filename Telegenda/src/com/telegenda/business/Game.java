package com.telegenda.business;

import java.util.Date;

public class Game 
{
	private Date dateTime;
	private String home;	
	private String visitor;
	
	public Game(Date dateTime, String home, String visitor)
	{
		this.dateTime = dateTime;
		this.home = home;
		this.visitor = visitor;
	}
	public Date getDateTime()
	{
		return dateTime;
	}
	public void setDateTime(Date dateTime)
	{
		this.dateTime = dateTime;
	}
	public String getHome()
	{
		return home;
	}
	public void setHome(String home) 
	{
		this.home = home;
	}	
	public String getVisitor()
	{
		return visitor;
	}
	public void setVisitor(String visitor)
	{
		this.visitor = visitor;
	}
	@Override
	public String toString() 
	{
		return String.format("%s vs %s", home, visitor);
	}
}
