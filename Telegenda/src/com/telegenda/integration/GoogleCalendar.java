package com.telegenda.integration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.telegenda.business.Listing;

public class GoogleCalendar 
{
	private static final String EST = "America/New_York";
	private String summary, description;
	private EventDateTime startTime, endTime;
	private ArrayList<EventAttendee> attendees;
	private Calendar service;
	
	public GoogleCalendar(Calendar service) throws GeneralSecurityException, IOException
	{
		this.service = service;
	}	
	public String createEvent(String calendarId, Listing listing) throws GeneralSecurityException, IOException
	{
		summary = listing.getTitle();
		description = listing.getDescription() + " on " + listing.getChannel();
		startTime = new EventDateTime()
				.setTimeZone(EST)
				.setDateTime(new DateTime(listing.getStartTime()));
		endTime = new EventDateTime()
				.setTimeZone(EST)
				.setDateTime(new DateTime(listing.getEndTime()));
		
		Event event = new Event();
		event.setSummary(summary);
		event.setDescription(description);
		event.setStart(startTime);
		event.setEnd(endTime);
		event.setAttendees(attendees);
				
		Event e = service.events().insert(calendarId, event).execute();
		
		return "New event: " + e.getId() + "Title: " + e.getSummary();
	}
}