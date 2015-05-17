package com.telegenda.integration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.telegenda.business.Program;

public class SearchResultDeserializer implements JsonDeserializer<List<Program>>
{
	public ArrayList<Program> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException 
	{

		ArrayList<Program> listings = new ArrayList<>();
		
	    JsonArray listingsJson = (JsonArray) json;
	    
	    for(int i=0; i< listingsJson.size(); i++)
	    {
	    	JsonObject channelObject = (JsonObject) listingsJson.get(i).getAsJsonObject().getAsJsonArray("Channels").get(0);//.getAsJsonObject("Channel");
	    	JsonObject programScheduleObject = (JsonObject) listingsJson.get(i).getAsJsonObject().getAsJsonObject("ProgramSchedule");
	    	
	    	Program p = new Program(programScheduleObject.get("Title").getAsString(), //title
	    							programScheduleObject.get("CopyText").getAsString(), //description
	    							programScheduleObject.get("EpisodeTitle").getAsString(),//episodeTitle
	    							channelObject.get("Name").getAsString(), //channel
	    							programScheduleObject.get("StartTime").getAsLong(), //start time
	    							programScheduleObject.get("EndTime").getAsLong() //end time
	    							);
	    	
	    	listings.add(p);
	    }
	    return listings;
	}
}
