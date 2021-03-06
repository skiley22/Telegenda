package com.example.appengine.users;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserServiceFactory;

@WebServlet(
		name = "Username",
		description = "Returns username",
		urlPatterns = "/Username"
)
public class UsernameServlet extends HttpServlet
{
	private static final long serialVersionUID = 8877081988617263460L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.getWriter().println(UserServiceFactory.getUserService().getCurrentUser().getNickname());
	}
}