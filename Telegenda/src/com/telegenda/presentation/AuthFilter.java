package com.telegenda.presentation;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class AuthFilter implements Filter 
{
	@Override
	public void destroy() {return;}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException 
	{
        String url = ((HttpServletRequest) request).getRequestURI();
        if (url.endsWith(".js") || url.endsWith(".css") || url.endsWith(".html") || url.endsWith(".htm")) {
            chain.doFilter(request, response);
        }
	}

	@Override
	public void init(FilterConfig config) throws ServletException {return;}
}
