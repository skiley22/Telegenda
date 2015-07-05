/*
 * Copyright (c) 2010 Google Inc.
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

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar.CalendarList.List;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.telegenda.business.Program;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Google Calendar Data API App Engine sample.
 * 
 * @author Yaniv Inbar
 */
public class CalendarAppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {

  static final String APP_NAME = "Google Calendar Data API Sample Web Client";

  static final String GWT_MODULE_NAME = "calendar";

  private static final long serialVersionUID = 1L;
  private static final TimeZone EST = TimeZone.getTimeZone("America/New_York");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("text/html");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = response.getWriter();
    writer.println("<!doctype html><html><head>");
    writer.println("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
    writer.println("<title>" + APP_NAME + "</title>");
    writer.println(
        "<link type=\"text/css\" rel=\"stylesheet\" href=\"" + GWT_MODULE_NAME + ".css\">");
    writer.println("<script type=\"text/javascript\" language=\"javascript\" " + "src=\""
        + GWT_MODULE_NAME + "/" + GWT_MODULE_NAME + ".nocache.js\"></script>");
    writer.println("</head><body>");
    UserService userService = UserServiceFactory.getUserService();
    writer.println("<div class=\"header\"><b>" + request.getUserPrincipal().getName() + "</b> | "
        + "<a href=\"" + userService.createLogoutURL(request.getRequestURL().toString())
        + "\">Log out</a> | "
        + "<a href=\"http://code.google.com/p/google-api-java-client/source/browse"
        + "/calendar-appengine-sample?repo=samples\">See source code for "
        + "this sample</a></div>");
    writer.println("<div id=\"main\"/>");
    
    //get calendar list

      Calendar client = Utils.loadCalendarClient();
      List listRequest = client.calendarList().list();
      listRequest.setFields("items(id,summary)");
      CalendarList feed = listRequest.execute();
      //ArrayList<GwtCalendar> result = new ArrayList<GwtCalendar>();
      if (feed.getItems() != null) {
        for (CalendarListEntry entry : feed.getItems()) {
          //result.add(new GwtCalendar(entry.getId(), entry.getSummary()));
          writer.println(entry.getSummary() + "<br />");
        }
      }
   
    
    writer.println("<br /><br /><br />When's family feud on?");
    
    ArrayList<Program> programs = new TvGuideDao().getListings("Family Feud");
    
    for(Program p : programs)
    	writer.println("This week on TV we have '" + p.getTitle() + "' on " + p.getChannel() + " at " + new SimpleDateFormat("hh:mm a - MM/dd/yyyy").format(p.getStartTime()) + "<br />");
    
    //end get calendar list
    
    Program p = programs.get(0);
    
    ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
	attendees.add(new EventAttendee().setEmail("skiley22@gmail.com"));
	
	Event event = new Event();
	event.setSummary(p.getTitle() + " on " + p.getChannel());
	event.setDescription(p.getTitle() + " - " + p.getEpisodeTitle() + " \n\n" + p.getDescription());
	event.setStart(new EventDateTime().setDateTime(new DateTime(p.getStartTime(), EST)));
	event.setEnd(new EventDateTime().setDateTime(new DateTime(p.getEndTime(), EST)));
	event.setAttendees(attendees);
    
    
    client.events().insert(feed.getItems().get(0).getId(), event).execute();
    
    
    writer.println("</body></html>");
  }

  @Override
  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
    return Utils.getRedirectUri(req);
  }

  @Override
  protected AuthorizationCodeFlow initializeFlow() throws IOException {
    return Utils.newFlow();
  }
}
