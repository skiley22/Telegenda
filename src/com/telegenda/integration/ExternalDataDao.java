package com.telegenda.integration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.Game;
import com.telegenda.business.Listing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

/**
 * @author skiley22@gmail.com
 */
public class ExternalDataDao 
{
	public static ArrayList<Listing> getListings(String keyword) throws IOException
	{
		//curent time in millis
		long d = Calendar.getInstance().getTime().getTime() / 1000; 
		String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/airings/80001/start/"+d+"/duration/20160/search?search="+URLEncoder.encode(keyword, "UTF-8").replace("+", "%20")+"&formattype=json";
		   
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		
		String response = IOUtils.toString(con.getInputStream());
		   
		Type listType = new TypeToken<List<Listing>>(){}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder()
		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
		.registerTypeAdapter(listType, new SearchResultDeserializer());
		    
		List<Listing> listings = gsonBuilder.create().fromJson(response, listType);
		ArrayList<Listing> matchedListings = new ArrayList<Listing>();
		    
		for(Listing prog : listings)
		if(prog.getTitle().toLowerCase().contains(keyword) || prog.getDescription().toLowerCase().contains(keyword) || prog.getEpisodeTitle().toLowerCase().contains(keyword))
		matchedListings.add(prog);
		  
		return matchedListings;
	}
	public static List<Game> getSchedule() throws IOException, ParseException
	{	
		ArrayList<Game> games = new ArrayList<>();
	    BufferedReader br = new BufferedReader(new FileReader("schedule.csv"));
	    String line = "";
	    
	    while ((line = br.readLine()) != null) 
	    {
			String[] gameLines = line.split(",");
			
			DateTime dateTime = new DateTime(new SimpleDateFormat("EEE MMM dd yyyy h:mm a").parse(gameLines[0] + " " + gameLines[1]));
			String home = gameLines[5];
			String away = gameLines[3];
			
			Game g = new Game(dateTime.toDate(), home, away);
			
			games.add(g);
	    }
	    
	    br.close();
	    
	    return games;
	}
	public static HashMap<String, Double> getWinPercents() throws MalformedURLException, IOException, ParseException
	{
	    String url = "https://erikberg.com/nba/standings.json";
	    
	    HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();

	    String response = IOUtils.toString(con.getInputStream());
	    
	    Type listType = new TypeToken<HashMap<String, Double>>(){}.getType();
	    GsonBuilder gsonBuilder = new GsonBuilder()
	    		.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
	    		.registerTypeAdapter(listType, new StandingDeserializer());
	    
	    HashMap<String, Double> standings = gsonBuilder.create().fromJson(response, listType);
	    
		return standings;
	}
}