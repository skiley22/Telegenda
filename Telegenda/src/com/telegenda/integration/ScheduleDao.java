package com.telegenda.integration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.telegenda.business.Game;

public class ScheduleDao 
{
	public static List<Game> getSchedule() throws IOException, ParseException
	{	
		ArrayList<Game> games = new ArrayList<>();
	    BufferedReader br = new BufferedReader(new FileReader("schedule.csv"));
	    String line = "";
	    
	    while ((line = br.readLine()) != null) 
	    {
			String[] gameLines = line.split(",");
			
			DateTime dateTime = new DateTime(new SimpleDateFormat("EEE MMM dd yyyy h:mm a").parse(gameLines[0] + " " + gameLines[1]));
			String home = gameLines[5];
			String away = gameLines[3];
			
			Game g = new Game(dateTime.toDate(), home, away);
			
			games.add(g);
	    }
	    
	    br.close();
	    
	    return games;
	}
}