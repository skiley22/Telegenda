package com.telegenda.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.telegenda.business.Order;

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
	
	public static int createCron(Order o) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("INSERT INTO orders (calendarId, keyword) VALUES (?,?);");
		ps.setString(1, o.getCalendarId());
		ps.setString(2, o.getKeyword());
		
		return ps.executeUpdate();
	}
	public static List<Order> getAllCrons() throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM orders");

		ResultSet rs = ps.executeQuery();
		ArrayList<Order> orders = new ArrayList<Order>();
		
		while (rs.next() == true)
			orders.add(new Order(rs.getInt("orderId"),rs.getString("calendarId"),rs.getString("keyword")));

		return orders;
	}
	/*public static Order getAllCrons(String userId) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM orders WHERE calendarId = '?'");
		ps.setInt(0, o.getOrderId());
		
		return ps.executeUpdate();
	}*/
	public static int deleteCron(Order o) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("DELETE FROM orders WHERE orderId = ?");
		ps.setInt(1, o.getOrderId());
		
		return ps.executeUpdate();
	}
	/*public static int updateCron(Order o) throws SQLException
	{
		PreparedStatement ps = getConnection().prepareStatement("UPDATE orders SET keyword = '?' WHERE ");
		ps.setString(0, o.getCalendarId());
		ps.setString(1, o.getKeyword());
		
		return ps.executeUpdate();
	}*/
}
