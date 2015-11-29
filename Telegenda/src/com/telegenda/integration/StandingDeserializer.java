package com.telegenda.integration;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StandingDeserializer implements JsonDeserializer<HashMap<String, Double>>
{
	@Override
	public HashMap<String, Double> deserialize(JsonElement json, Type type, JsonDeserializationContext context) 
			throws JsonParseException 
	{
		HashMap<String, Double> standings = new HashMap<>();
		
	    for(JsonElement j : json.getAsJsonObject().get("standing").getAsJsonArray())
	    {
	    	String firstName = j.getAsJsonObject().get("first_name").getAsString();
	    	String lastName = j.getAsJsonObject().get("last_name").getAsString();
	    	String winPercent = j.getAsJsonObject().get("win_percentage").getAsString();
	    	
	    	standings.put(firstName + " " + lastName, Double.parseDouble(winPercent));
	    }
	    return standings;
	}
}
