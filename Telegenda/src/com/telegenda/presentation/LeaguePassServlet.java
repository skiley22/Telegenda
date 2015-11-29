package com.telegenda.presentation;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

public class LeaguePassServlet extends HttpServlet
{
	private static final long serialVersionUID = -6626029855378642630L;
	private static final String SERVICE_ACCOUNT_EMAIL = "769449426385-qhu7vh5u6jif611m3gq6e4bi731c0tuv@developer.gserviceaccount.com";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static HttpTransport httpTransport;
	private static final Logger log = Logger.getLogger(LeaguePassServlet.class.getName());
	//private key: "notasecret"

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		
		
	}
}