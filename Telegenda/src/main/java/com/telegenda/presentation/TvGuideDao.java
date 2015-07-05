/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.telegenda.presentation;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.Program;
import com.telegenda.integration.SearchResultDeserializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author Steven@google.com (Your Name Here)
 *
 */
public class TvGuideDao 
{
  public ArrayList<Program> getListings(String keyword) throws IOException
  {
    keyword = keyword.toLowerCase();

    long d = Calendar.getInstance().getTime().getTime() / 1000;
    
    String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/airings/80001/start/"+d+"/duration/20160/search?search="+URLEncoder.encode(keyword, "UTF-8").replace("+", "%20")+"&formattype=json";
    
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
    ArrayList<Program> matchedListings = new ArrayList<Program>();
        
    //String[] keywords = {"Football","Celtics","Gotham","Grantland"};
    
    for(Program prog : listings)
        if(prog.getTitle().toLowerCase().contains(keyword) || prog.getDescription().toLowerCase().contains(keyword) || prog.getEpisodeTitle().toLowerCase().contains(keyword))
            matchedListings.add(prog);
  
    return matchedListings;
  }
}
