package com.example.appengine;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.appengine.utils.Utils;
import com.google.api.services.calendar.Calendar;
import com.google.gson.Gson;

import org.apache.commons.lang3.exception.ExceptionUtils;

@WebServlet(
		name = "SavedKeywords",
		description = "SavedKeywords",
		urlPatterns = "/SavedKeywords"
)
public class SavedKeywordServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;
	private static final Logger log = Logger.getLogger(SavedKeywordServlet.class.getName());

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		try
		{
			Calendar service = Utils.loadCalendarClient();
			response.getWriter().println(new Gson().toJson(TelegendaCronService.getCrons(service)));
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
			response.getWriter().println("Error - please contact Steve for help");
		}
	}
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		if(!request.getParameterMap().containsKey("id"))
		{
			response.getWriter().println("No id provided");
			return;
		}

		try
		{
			String id = request.getParameter("id");

			int resultCode = TelegendaCronService.deleteCron(Integer.parseInt(id));

			if(resultCode > 0)
				response.getWriter().println("Keyword successfully deleted");
			else
				response.getWriter().println("Keyword wasn't deleted");
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, ExceptionUtils.getStackTrace(e));
			response.getWriter().println("Error - please contact Steve for help");
		}
	}
}