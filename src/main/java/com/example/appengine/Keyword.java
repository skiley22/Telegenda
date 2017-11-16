package com.example.appengine;

public class Keyword
{
	private String calendarId;
	private String keywordName;
	private int keywordId;

	public Keyword(int keywordId, String calendarId, String keywordName)
	{
		this.keywordId = keywordId;
		this.calendarId = calendarId;
		this.keywordName = keywordName;
	}
	public Keyword(String calendarId, String keywordName)
	{
		this.calendarId = calendarId;
		this.keywordName = keywordName;
	}
	public String getCalendarId()
	{
		return calendarId;
	}
	public void setCalendarId(String calendarId)
	{
		this.calendarId = calendarId;
	}
	public String getKeywordName()
	{
		return keywordName;
	}
	public void setKeywordName(String keywordName)
	{
		this.keywordName = keywordName;
	}
	public int getKeywordId() {
		return keywordId;
	}
	public void setKeywordId(int keywordId) {
		this.keywordId = keywordId;
	}
}