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
import com.telegenda.business.Channel;
import com.telegenda.business.ChannelSchedule;
import com.telegenda.business.Program;

public class ListingDeserializer implements JsonDeserializer<List<ChannelSchedule>>
{
	public ArrayList<ChannelSchedule> deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException 
	{

		ArrayList<ChannelSchedule> listings = new ArrayList<>();
		
	    JsonArray listingsJson = (JsonArray) json;
	    
	    for(int i=0; i< listingsJson.size(); i++)
	    {
	    	JsonObject channelObject = listingsJson.get(i).getAsJsonObject().getAsJsonObject("Channel");
	    	System.out.println(channelObject.toString());
	    	Channel c = new Channel(channelObject.get("FullName").getAsString(),channelObject.get("Name").getAsString(),channelObject.get("Number").getAsString(),channelObject.get("SourceId").getAsInt());
	    	ArrayList<Program> programs = new ArrayList<>();
	    	for(JsonElement j : listingsJson.get(i).getAsJsonObject().getAsJsonArray("ProgramSchedules"))
	    	{
	    		programs.add(new Program(j.getAsJsonObject().get("AiringAttrib").getAsInt(),j.getAsJsonObject().get("CatId").getAsInt(),j.getAsJsonObject().get("EndTime").getAsInt(),j.getAsJsonObject().get("ProgramId").getAsInt(),j.getAsJsonObject().get("StartTime").getAsInt(),j.getAsJsonObject().get("Title").getAsString()));
	    	}
	    	listings.add(new ChannelSchedule(c, programs));
	    }

	    return listings;
	}
}
