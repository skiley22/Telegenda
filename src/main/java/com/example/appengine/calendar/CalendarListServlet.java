package com.example.appengine.calendar;

import java.io.IOException;

import com.example.appengine.utils.Utils;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.calendar.model.CalendarList;
import com.google.gson.Gson;

@WebServlet(
		name = "CalendarList",
		description = "Returns user's calendars",
		urlPatterns = "/CalendarList"
)
public class CalendarListServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Calendar client = Utils.loadCalendarClient();

		if(client == null)
			response.getWriter().println("No calendars");


		List listRequest = client.calendarList().list();
	    listRequest.setFields("items(id,summary)");
	    CalendarList feed = listRequest.execute();
	    if (feed.getItems() != null)
	    	response.getWriter().println(new Gson().toJson(feed.getItems()));
	    else
	    	response.getWriter().println("No calendars");
	}
}