package com.telegenda.integration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.Listing;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * @author skiley22@gmail.com
 */
public class TvGuideDao 
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
}