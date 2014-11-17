package com.telegenda.integration;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.telegenda.business.Listing;
import com.telegenda.business.Program;

public class GoogleCalendar 
{
	private static final String APPLICATION_NAME = "Telegenda";
	private static final String SERVICE_ACCOUNT_EMAIL = "769449426385-vik1epa1ak3vj9qnfla6oqam7jjqm615@developer.gserviceaccount.com";
	String privateKeyPw= "notasecret";
	private static final String MASTER_ACCOUNT = "skiley22@gmail.com";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final TimeZone EST = TimeZone.getTimeZone("America/New_York");
	private static HttpTransport httpTransport;
	private String summary, description, vendorCalendarId;
	private EventDateTime startTime, endTime;
	private ArrayList<EventAttendee> attendees;
	private Calendar service;
	
	public GoogleCalendar() throws GeneralSecurityException, IOException
	{
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		GoogleCredential credential = new GoogleCredential.Builder()
				.setTransport(httpTransport)
				.setJsonFactory(JSON_FACTORY)
				.setServiceAccountId(SERVICE_ACCOUNT_EMAIL)
				.setServiceAccountScopes(
						Collections.singleton(CalendarScopes.CALENDAR))
				.setServiceAccountPrivateKeyFromP12File(new File("key.p12"))
				.build();

		service = new Calendar.Builder(httpTransport, JSON_FACTORY,
				credential).setApplicationName(APPLICATION_NAME).build();
	}	
	public String createEvent(Listing l) throws GeneralSecurityException,
	IOException {
		summary = l.getProgram().getTitle();
		description = l.getProgram().getTitle() + " on Channel " + l.getChannel().getNumber();
		startTime = new EventDateTime().setDateTime(new DateTime(l.getProgram().getStartTime(), EST));
		endTime = new EventDateTime().setDateTime(new DateTime(l.getProgram().getEndTime(), EST));
		
		System.out.println("Start time: " + startTime.toPrettyString() + " End time: " + l.getProgram().getEndTime().getTime());
		
		attendees = new ArrayList<EventAttendee>();
		attendees.add(new EventAttendee().setEmail("skiley22@gmail.com"));
		
		Event event = new Event();
		event.setSummary(summary);
		event.setDescription(description);
		event.setStart(startTime);
		event.setEnd(endTime);
		event.setAttendees(attendees);
				
		Event e = service.events().insert("f71jkiedt0pp7chdik16pe7teo@group.calendar.google.com", event).execute();
		
		return "New event: " + e.getId() + "Title: " + e.getSummary();
		}
}
