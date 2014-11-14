package com.telegenda.presentation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.http.*;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.ChannelSchedule;
import com.telegenda.integration.ListingDeserializer;

public class TelegendaServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1552183100089568485L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		//String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/schedules/80001/start/1414296000/duration/120?ChannelFields=Name%2CFullName%2CNumber%2CSourceId&ScheduleFields=ProgramId%2CEndTime%2CStartTime%2CTitle%2CAiringAttrib%2CCatId&formattype=json&disableChannels=music%2Cppv%2C24hr";
		
		String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/schedules/361471.16777216/start/1415928600/duration/120?ChannelFields=Name%2CFullName%2CNumber%2CSourceId&ScheduleFields=ProgramId%2CEndTime%2CStartTime%2CTitle%2CAiringAttrib%2CCatId&formattype=json&disableChannels=music%2Cppv%2C24hr";
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		int responseCode = con.getResponseCode();
		
		BufferedReader in = null;
		
		if(responseCode == 200)
		{
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) 
			response.append(inputLine);
		
		in.close();
	    
		resp.setContentType("text/plain");
		
	    Type listType = new TypeToken<List<ChannelSchedule>>(){}.getType();
	    //Gson gson = new GsonBuilder().registerTypeAdapter(listType, new InterfaceAdapter<Animal>());
		GsonBuilder gsonBuilder = new GsonBuilder();
	    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
	    gsonBuilder.registerTypeAdapter(listType, new ListingDeserializer());
		
		
	    List<ChannelSchedule> c = gsonBuilder.create().fromJson(response.toString(), listType);
		
		/*Type listType = new TypeToken<List<ChannelSchedule>>(){}.getType();
		
		List<ChannelSchedule> listings = new Gson().fromJson(response.toString(), listType);*/
		
		//resp.getWriter().println(listings.get(0).getChannel().getFullName());
	    resp.getWriter().println(c.get(0).getProgramSchedules().get(2).getTitle());
	}
}
