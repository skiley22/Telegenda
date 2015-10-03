package com.telegenda.presentation;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.AclRule.Scope;
import com.telegenda.business.Listing;
import com.telegenda.business.SavedKeyword;
import com.telegenda.integration.GoogleCalendar;
import com.telegenda.integration.TelegendaCronDao;
import com.telegenda.integration.TvGuideDao;

public class CalendarCronServlet extends HttpServlet
{
	private static final long serialVersionUID = -6626029855378642630L;
	private static final String SERVICE_ACCOUNT_EMAIL = "769449426385-qhu7vh5u6jif611m3gq6e4bi731c0tuv@developer.gserviceaccount.com";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport httpTransport;
	private static final Logger log = Logger.getLogger(CalendarCronServlet.class.getName());
	//private key: "notasecret"
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		StringBuilder output = new StringBuilder();
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
				
			for(SavedKeyword o : TelegendaCronDao.getCrons())
				for(Listing l : TvGuideDao.getListings(o.getKeywordName()))
					GoogleCalendar.createEvent(service, o.getCalendarId(), l);
		}
		catch(Exception e)
		{
			output.append(ExceptionUtils.getStackTrace(e));
			log.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
		}
		response.getWriter().println(output);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		String calendarId = request.getParameter("calendarid");
		String keyword = request.getParameter("keyword"); 
		
		if(calendarId == null || keyword == null)
		{
			response.getWriter().println("Invalid parameters");
			return;
		}
		
		StringBuilder output = new StringBuilder();
		try
		{
			Calendar service = Utils.loadCalendarClient();
			
			for(SavedKeyword sk : TelegendaCronDao.getCrons(service))
				if(sk.getKeywordName().equals(keyword))
				{
					response.getWriter().println("You have already saved this keyword");
					return;
				}
			
			boolean hasPermission = false;
				
			for(AclRule r : service.acl().list(calendarId).execute().getItems())
				if(r.getScope().getValue().equals(SERVICE_ACCOUNT_EMAIL) && r.getRole().equals("writer"))
					hasPermission = true;
				
			if(!hasPermission)
			{
				AclRule rule = new AclRule();
				Scope scope = new Scope();
				scope.setType("user").setValue(SERVICE_ACCOUNT_EMAIL);
				rule.setScope(scope).setRole("writer");
				service.acl().insert(calendarId, rule).execute();
			}
			int result = TelegendaCronDao.createCron(new SavedKeyword(calendarId, keyword));	
			
			if(result > 0)
				output.append("Successfully saved keyword - check your calendar for new shows!");
			else
				throw new Exception("Keyword not saved"); 
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			output.append(ExceptionUtils.getStackTrace(e));
		}
		response.getWriter().println(output);
		doGet(null, response);
	}
}