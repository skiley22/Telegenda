package com.telegenda.presentation;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;


public class ListingsServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException 
	{
		if(!request.getParameterMap().containsKey("keyword"))
			//response.getWriter().println("No keyword provided");
			response.getWriter().println(new Gson().toJson(new TvGuideDao().getListings("Family Feud")));
		else
			response.getWriter().println(new Gson().toJson(new TvGuideDao().getListings(request.getParameter("keyword"))));
	}
}