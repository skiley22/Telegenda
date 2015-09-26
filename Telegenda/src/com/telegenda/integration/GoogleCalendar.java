package com.telegenda.integration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.telegenda.business.Listing;

public class GoogleCalendar 
{
	private static final String EST = "America/New_York";
	
	public static List<String> getCalendarIdList(Calendar service) throws IOException
	{
		ArrayList<String> calendars = new ArrayList<String>();
		
		for(CalendarListEntry cle : service.calendarList().list().execute().getItems())
			calendars.add(cle.getId());
			
		return calendars;
	}
	
	public static String createEvent(Calendar service, String calendarId, Listing listing) throws GeneralSecurityException, IOException
	{
		String summary = listing.getTitle();
		String description = listing.toString();
		EventDateTime startTime = new EventDateTime()
				.setTimeZone(EST)
				.setDateTime(new DateTime(listing.getStartTime()));
		EventDateTime endTime = new EventDateTime()
				.setTimeZone(EST)
				.setDateTime(new DateTime(listing.getEndTime()));
		
		Event event = new Event()
		.setSummary(summary)
		.setDescription(description)
		.setStart(startTime)
		.setEnd(endTime);
		
		boolean exists = false;
		
		String pageToken = null;
		do {
		  Events events = service.events().list(calendarId).setPageToken(pageToken).execute();
		  List<Event> items = events.getItems();
		  for (Event e : items) {
			  if(e.getSummary() != null 
						&& e.getSummary().equals(listing.getTitle())
						&& e.getDescription() != null && e.getDescription().equals(listing.toString())
						&& e.getStart().getDateTime().getValue() == listing.getStartTime()
						&& e.getEnd().getDateTime().getValue() == listing.getEndTime())
				  exists = true;
		  }
		  pageToken = events.getNextPageToken();
		} while (pageToken != null);
		
		if(!exists)
		{
			Event e = service.events().insert(calendarId, event).execute();
			return "Added '" + e.getSummary() + "' to '" + service.calendars().get(calendarId).execute().getSummary() + "' calendar";
		}
		else
			return "Event already exists in calendar";
	}
}