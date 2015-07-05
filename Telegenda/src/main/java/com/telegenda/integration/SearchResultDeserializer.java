package com.telegenda.integration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.telegenda.business.Program;

public class SearchResultDeserializer implements JsonDeserializer<List<Program>>
{
	@Override
  public ArrayList<Program> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException 
	{

		ArrayList<Program> listings = new ArrayList<Program>();
	    
	    for(JsonElement j : json.getAsJsonArray())
	    {
	    	JsonObject channelObject = j.getAsJsonObject().getAsJsonArray("Channels").get(0).getAsJsonObject();//.getAsJsonObject("Channel");
	    	JsonObject programScheduleObject = j.getAsJsonObject().getAsJsonObject("ProgramSchedule").getAsJsonObject();

	    	if(programScheduleObject.get("StartTime").isJsonNull() || programScheduleObject.get("EndTime").isJsonNull())
	    		continue;
	    	
	    	String title = "";
	    	String copyText = "";
	    	String episodeTitle = "";
	    	String channel = "";
	    	
	    	if(!programScheduleObject.get("Title").isJsonNull())
	    		title = programScheduleObject.get("Title").getAsString();
	    	if(!programScheduleObject.get("CopyText").isJsonNull())
	    		copyText = programScheduleObject.get("CopyText").getAsString();
	    	if(!programScheduleObject.get("EpisodeTitle").isJsonNull())
	    		episodeTitle = programScheduleObject.get("EpisodeTitle").getAsString();
	    	if(!channelObject.get("Name").isJsonNull())
	    		channel = channelObject.get("Name").getAsString();	    	
	    	
	    	listings.add(new Program(title, copyText, episodeTitle, channel,
					programScheduleObject.get("StartTime").getAsLong(), 
					programScheduleObject.get("EndTime").getAsLong()
					));
	    }
	    return listings;
	}
}
