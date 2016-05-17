package com.telegenda.integration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.calendar.Calendar;
import com.telegenda.business.SavedKeyword;

public class TelegendaCronDao 
{
	private static Connection getConnection() throws SQLException
	{
		String url = "";
		try 
		{
			Class.forName("com.mysql.jdbc.GoogleDriver");
			url = "jdbc:google:mysql://telegenda-webservice:crons/telegenda?user=root";
		} 
		catch (Exception e)
		{
		      e.printStackTrace();
		}		
		
		return DriverManager.getConnection(url);
	}	
	
	public static int createCron(SavedKeyword o) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO Keywords (calendarId, keywordName) VALUES (?,?);");
		ps.setString(1, o.getCalendarId());
		ps.setString(2, o.getKeywordName());
		
		return ps.executeUpdate();
	}
	public static List<SavedKeyword> getCrons(Calendar service) throws SQLException, IOException
	{
		List<String> calendars = GoogleCalendar.getCalendarIdList(service);
		StringBuilder sb = new StringBuilder();
		
		sb.append(" WHERE calendarId IN (");
		
		for(int i = 0; i < calendars.size(); i++)
		{
			sb.append("'" + calendars.get(i) + "'");
			if(i != calendars.size()-1)
				sb.append(",");	
		}
		
		sb.append(")");
		
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM Keywords" + sb.toString());

		ResultSet rs = ps.executeQuery();
		ArrayList<SavedKeyword> orders = new ArrayList<SavedKeyword>();
		
		while (rs.next() == true)
			orders.add(new SavedKeyword(rs.getInt("keywordId"),rs.getString("calendarId"),rs.getString("keywordName")));

		return orders;
	}
	public static List<SavedKeyword> getCrons() throws SQLException, IOException
	{
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM Keywords");

		ResultSet rs = ps.executeQuery();
		ArrayList<SavedKeyword> orders = new ArrayList<SavedKeyword>();
		
		while (rs.next() == true)
			orders.add(new SavedKeyword(rs.getInt("keywordId"),rs.getString("calendarId"),rs.getString("keywordName")));

		return orders;
	}
	public static int deleteCron(int keywordId) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM Keywords WHERE keywordId = ?");
		ps.setInt(1, keywordId);
		
		return ps.executeUpdate();
	}
}