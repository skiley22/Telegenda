package com.telegenda.presentation;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList.List;
import com.google.api.services.calendar.model.CalendarList;
import com.google.gson.Gson;

public class CalendarListServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		Calendar client = Utils.loadCalendarClient();
	    List listRequest = client.calendarList().list();
	    listRequest.setFields("items(id,summary)");
	    CalendarList feed = listRequest.execute();
	    if (feed.getItems() != null)
	    	response.getWriter().println(new Gson().toJson(feed.getItems()));
	    else
	    	response.getWriter().println("No calendars");
	}
}