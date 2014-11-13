package com.fairfieldcloud.telegenda;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.*;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

public class TelegendaServlet extends HttpServlet {
	
	private static final long serialVersionUID = -1552183100089568485L;
	private NetHttpTransport HTTP_TRANSPORT;
	private JacksonFactory JSON_FACTORY;
	private HttpRequestFactory requestFactory;
	
	public void init()
	{
		requestFactory =
		        HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
		            @Override
		          public void initialize(HttpRequest request) {
		            request.setParser(new JsonObjectParser(JSON_FACTORY));
		          }
		        });		
	}
	

	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    GenericUrl url = new GenericUrl("http://mobilelistings.tvguide.com/Listingsweb/ws/rest/schedules/80001/start/1414296000/duration/120?ChannelFields=Name%2CFullName%2CNumber%2CSourceId&ScheduleFields=ProgramId%2CEndTime%2CStartTime%2CTitle%2CAiringAttrib%2CCatId&formattype=json&disableChannels=music%2Cppv%2C24hr");
	    //url.put("fields", "items(id,url,object(content,plusoners/totalItems))");
	    HttpRequest request = requestFactory.buildGetRequest(url);
	    HttpResponse response = request.execute();
	    BufferedReader sw = new BufferedReader(new InputStreamReader(response.getContent()));
	    
	    String s = "";
	    
	    while((s = sw.readLine()) != null)
	    {
	    	System.out.println(s);
	    }
	    
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
