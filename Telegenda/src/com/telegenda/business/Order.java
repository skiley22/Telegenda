package com.telegenda.business;

public class Order 
{
	private String calendarId;
	private String keyword;
	private int orderId;
	
	public Order(int orderId, String calendarId, String keyword)
	{
		this.orderId = orderId;
		this.calendarId = calendarId;
		this.keyword = keyword;
	}
	public Order(String calendarId, String keyword)
	{
		this.calendarId = calendarId;
		this.keyword = keyword;
	}
	public String getCalendarId() 
	{
		return calendarId;
	}
	public void setCalendarId(String calendarId) 
	{
		this.calendarId = calendarId;
	}
	public String getKeyword() 
	{
		return keyword;
	}
	public void setKeyword(String keyword) 
	{
		this.keyword = keyword;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "Order [calendarId=" + calendarId + ", keyword=" + keyword + ", orderId=" + orderId + "]";
	}
	
}