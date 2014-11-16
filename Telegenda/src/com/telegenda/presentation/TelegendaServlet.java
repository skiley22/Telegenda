package com.telegenda.presentation;

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
import java.util.List;
import java.util.Locale;

import javax.servlet.http.*;

import com.google.gson.FieldNamingPolicy; 
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.telegenda.business.ChannelSchedule;
import com.telegenda.business.Listing;
import com.telegenda.business.Program;
import com.telegenda.integration.GoogleCalendar;
import com.telegenda.integration.ListingDeserializer;

public class TelegendaServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1552183100089568485L;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException 
	{
		try
		{
			resp.setContentType("text/plain");
			
			String scheduleFields = URLEncoder.encode("ProgramId,EndTime,StartTime,Title,AiringAttrib,CatId", "UTF-8");
			String channelFields = URLEncoder.encode("Name,FullName,Number,SourceId", "UTF-8");
			String disableChannels = URLEncoder.encode("music,ppv,24hr", "UTF-8");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);		
			cal.set(Calendar.MILLISECOND, 0);
			long time = Long.parseLong((""+cal.getTimeInMillis()).substring(0, 10));
			
			String url = "http://mobilelistings.tvguide.com/Listingsweb/ws/rest/schedules/361471.16777216/start/" + time + "/duration/10080?ChannelFields="+ channelFields +"&ScheduleFields=" + scheduleFields + "&formattype=json&disableChannels=" + disableChannels;		
			
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
			
		    Type listType = new TypeToken<List<ChannelSchedule>>(){}.getType();
			GsonBuilder gsonBuilder = new GsonBuilder();
		    gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		    gsonBuilder.registerTypeAdapter(listType, new ListingDeserializer());
			
		    List<ChannelSchedule> c = gsonBuilder.create().fromJson(response.toString(), listType);
		    ArrayList<Listing> listings = new ArrayList<>();
			    
		    String output = "";
		    
		    for(ChannelSchedule cs : c)
		    	for(Program prog : cs.getProgramSchedules())
		    		if(prog.getTitle().contains("Grantland"))
		    			listings.add(new Listing(cs.getChannel(), prog));
		    
		    //GoogleCalendar gc = new GoogleCalendar();
		    
		    for(Listing l : listings)
		    {
			 //   System.out.println(gc.createEvent(l));
		    	output += "This week on TV we have " + l.getProgram().getTitle() + " on Channel " + l.getChannel().getNumber() + ", " + new SimpleDateFormat("E", Locale.US).format(l.getProgram().getStartTime()) + " at " + new SimpleDateFormat("hh:mm a", Locale.US).format(l.getProgram().getStartTime()) + "\n";
		    }
				
		    
		    
		    resp.getWriter().println(output);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
