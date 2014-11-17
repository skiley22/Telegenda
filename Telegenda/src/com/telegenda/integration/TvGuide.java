package com.telegenda.integration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.ChannelSchedule;
import com.telegenda.business.Listing;
import com.telegenda.business.Program;

public class TvGuide
{
	private int DAY = 1440;
	//private int WEEK = 10080;
	
	public List<Listing> getListings() throws IOException
	{
		String scheduleFields = URLEncoder.encode("ProgramId,EndTime,StartTime,Title,AiringAttrib,CatId", "UTF-8");
		String channelFields = URLEncoder.encode("Name,FullName,Number,SourceId", "UTF-8");
		String disableChannels = URLEncoder.encode("music,ppv,24hr", "UTF-8");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);		
		cal.set(Calendar.MILLISECOND, 0);
		long time = Long.parseLong((""+cal.getTimeInMillis()).substring(0, 10));
		
		String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/schedules/361471.16777216/start/" + time + "/duration/"+DAY+"?ChannelFields="+ channelFields +"&ScheduleFields=" + scheduleFields + "&formattype=json&disableChannels=" + disableChannels;		
		
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
		
	    Type listType = new TypeToken<List<ChannelSchedule>>(){}.getType();
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
	    gsonBuilder.registerTypeAdapter(listType, new ListingDeserializer());
		
	    List<ChannelSchedule> c = gsonBuilder.create().fromJson(response.toString(), listType);
	    ArrayList<Listing> listings = new ArrayList<>();
		    
	    String[] keywords = {"Football","Celtics","Gotham","Grantland"};
	    
	    for(ChannelSchedule cs : c)
	    	for(Program prog : cs.getProgramSchedules())
	    		for(String s : keywords)
	    			if(prog.getTitle().contains(s))
	    				listings.add(new Listing(cs.getChannel(), prog));
	    
	    return listings;
	}	
}