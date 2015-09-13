package com.telegenda.presentation;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.services.calendar.Calendar;
import com.google.gson.Gson;
import com.telegenda.business.Listing;
import com.telegenda.integration.GoogleCalendar;

public class EventServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		try
		{
			String calendar = request.getParameter("calendar");
			String listingString = request.getParameter("listing"); 
			
			if(calendar == null || listingString == null)
			{
				response.getWriter().println("Invalid parameters");
				return;
			}
			
			Listing listing = new Gson().fromJson(listingString, Listing.class);
			
			Calendar service = Utils.loadCalendarClient();
			
			String success = GoogleCalendar.createEvent(service, calendar, listing);
			
			response.getWriter().println(success);
		}
		catch(GeneralSecurityException e)
		{
			response.getWriter().println("error!");
		}
	}
}