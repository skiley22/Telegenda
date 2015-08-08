package com.telegenda.integration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class TelegendaCronDao 
{
	public Connection getConnection() throws SQLException
	{
		String url = "";
		try {
		      if (SystemProperty.environment.value() ==
		          SystemProperty.Environment.Value.Production) {
		        // Load the class that provides the new "jdbc:google:mysql://" prefix.
		        Class.forName("com.mysql.jdbc.GoogleDriver");
		        url = "jdbc:google:mysql://your-project-id:your-instance-name/guestbook?user=root";
		      } else {
		        // Local MySQL instance to use during development.
		        Class.forName("com.mysql.jdbc.Driver");
		        url = "jdbc:mysql://127.0.0.1:3306/guestbook?user=root";

		        // Alternatively, connect to a Google Cloud SQL instance using:
		        // jdbc:mysql://ip-address-of-google-cloud-sql-instance:3306/guestbook?user=root
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }		
		
		return DriverManager.getConnection(url);
	}	
	
	public void createCron() throws SQLException
	{}
	public void getAllCrons() throws SQLException
	{}
	public void deleteCron() throws SQLException
	{}
	public void updateCron() throws SQLException
	{}
}
