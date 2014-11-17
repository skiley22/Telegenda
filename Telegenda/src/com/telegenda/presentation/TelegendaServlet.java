package com.telegenda.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.telegenda.integration.GoogleCalendar;
import com.telegenda.integration.TvGuide;

public class TelegendaServlet extends AbstractAppEngineAuthorizationCodeServlet {
	
	private static final long serialVersionUID = -1552183100089568485L;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		try
		{
		    GoogleCalendar gc = new GoogleCalendar(Utils.loadCalendarClient());
		    gc.createEvent(new TvGuide().getListings().get(0));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected AuthorizationCodeFlow initializeFlow() throws ServletException,
			IOException 
	{
	    return Utils.newFlow();
	}

	@Override
	protected String getRedirectUri(HttpServletRequest req)	throws ServletException, IOException 
	{
		return Utils.getRedirectUri(req);
	}
}