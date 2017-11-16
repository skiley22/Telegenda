package com.example.appengine;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(
		name = "Listings",
		description = "Listings",
		urlPatterns = "/Listings"
)
public class ListingsServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if(!request.getParameterMap().containsKey("keyword"))
			response.getWriter().println("No keyword provided");
		else
			response.getWriter().println(new Gson().toJson(TVGuideAccessService.getListings(request.getParameter("keyword").toLowerCase())));
	}
}