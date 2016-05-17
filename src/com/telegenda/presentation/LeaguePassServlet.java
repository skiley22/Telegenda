package com.telegenda.presentation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.telegenda.business.Game;
import com.telegenda.integration.ExternalDataDao;
import com.telegenda.integration.GoogleCalendar;

public class LeaguePassServlet extends HttpServlet
{
	private static final long serialVersionUID = -6626029855378642630L;
	private static final String SERVICE_ACCOUNT_EMAIL = "769449426385-qhu7vh5u6jif611m3gq6e4bi731c0tuv@developer.gserviceaccount.com";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport httpTransport;
	private static final Logger log = Logger.getLogger(LeaguePassServlet.class.getName());
	private String tvCalendarId = "f71jkiedt0pp7chdik16pe7teo@group.calendar.google.com";
	//private key: "notasecret"

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String output = "Output: ";
		
		try
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

			Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY,
					credential).setApplicationName("telegenda-webservice").build();
		
			List<Game> games = ExternalDataDao.getSchedule();
			Map<String, Double> winPercentsMap = ExternalDataDao.getWinPercents();
			
			for(Game g : games)
			{
				DateTime currentDate = new DateTime();
				DateTime futureDate = currentDate.plusDays(8);
				
				if(g.getDateTime().equals(currentDate) || 
						(g.getDateTime().after(currentDate.toDate()) && g.getDateTime().before(futureDate.toDate())))				
					if(winPercentsMap.get(g.getHome()) > .5 && winPercentsMap.get(g.getVisitor()) > .5)
						output += GoogleCalendar.createEvent(service, tvCalendarId, g);
			}
		}
		catch(Exception e)
		{
			output += ExceptionUtils.getStackTrace(e);
			log.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
		}
		response.getWriter().println(output);
	}
}