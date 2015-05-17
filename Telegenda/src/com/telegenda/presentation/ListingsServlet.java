package com.telegenda.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.FieldNamingPolicy; 
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.Program;
import com.telegenda.integration.SearchResultDeserializer;

public class ListingsServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1552183100089568485L;
	//private static final int WEEK_IN_MINUTES = 10080;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		try
		{
			resp.setContentType("text/plain");
			
			if(req.getParameter("keyword") == null)
			{
				resp.getWriter().println("No keyword provided");
				return;
			}
			
			for(String keyword : req.getParameterValues("keyword"))
			{
				keyword = keyword.toLowerCase();

				String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/airings/80001/start/1429205400/duration/20160/search?search="+keyword+"&formattype=json";
				
				URL obj = new URL(url);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				int responseCode = con.getResponseCode();
				
				BufferedReader in = null;
				
				if(responseCode == 200)
					in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
		 
				while ((inputLine = in.readLine()) != null) 
					response.append(inputLine);
				
				in.close();
				
			    Type listType = new TypeToken<List<Program>>(){}.getType();
				GsonBuilder gsonBuilder = new GsonBuilder();
			    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
			    gsonBuilder.registerTypeAdapter(listType, new SearchResultDeserializer());
			    
			    List<Program> listings = gsonBuilder.create().fromJson(response.toString(), listType);
			    ArrayList<Program> matchedListings = new ArrayList<>();
				    
			    String output = "";
			    
		    	for(Program prog : listings)
		    		if(prog.getTitle().toLowerCase().contains(keyword) || prog.getDescription().toLowerCase().contains(keyword) || prog.getEpisodeTitle().toLowerCase().contains(keyword))
		    			matchedListings.add(prog);
		    	
		    	Collections.sort(matchedListings);
			    
			    //GoogleCalendar gc = new GoogleCalendar();
			    
			    for(Program p : matchedListings)
				 //   System.out.println(gc.createEvent(l));
			    	output += "This week on TV we have '" + p.getTitle() + "' on " + p.getChannel() + " at " + new SimpleDateFormat("hh:mm a - MM/dd/yyyy").format(p.getStartTime()) + "\n";
			    
			    resp.getWriter().println(output);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
