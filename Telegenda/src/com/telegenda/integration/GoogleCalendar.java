package com.telegenda.integration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.TimeZone;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.telegenda.business.Listing;

public class GoogleCalendar 
{
	private static final TimeZone EST = TimeZone.getTimeZone("America/New_York");
	private String summary, description;
	private EventDateTime startTime, endTime;
	private ArrayList<EventAttendee> attendees;
	private Calendar service;
	
	
	public GoogleCalendar(Calendar c) throws GeneralSecurityException, IOException
	{
		service = c;
	}	
	public String createEvent(Listing l) throws GeneralSecurityException, IOException
	{
		summary = l.getProgram().getTitle();
		description = l.getProgram().getTitle() + " on Channel " + l.getChannel().getNumber();
		startTime = new EventDateTime().setDateTime(new DateTime(l.getProgram().getStartTime(), EST));
		endTime = new EventDateTime().setDateTime(new DateTime(l.getProgram().getEndTime(), EST));
		
		Event event = new Event();
		event.setSummary(summary);
		event.setDescription(description);
		event.setStart(startTime);
		event.setEnd(endTime);
		event.setAttendees(attendees);
				
		Event e = service.events().insert(service.calendarList().list().execute().getItems().get(0).getId(), event).execute();
		
		return "New event: " + e.getId() + "Title: " + e.getSummary();
	}
}
