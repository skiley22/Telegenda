package com.telegenda.integration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;

import org.apache.commons.io.IOUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StandingsDao 
{
	public static HashMap<String, Double> getStandings() throws MalformedURLException, IOException, ParseException
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
