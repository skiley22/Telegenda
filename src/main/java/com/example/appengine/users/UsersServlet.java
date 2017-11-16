/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// [START users_API_example]

package com.example.appengine.users;

import com.example.appengine.utils.Utils;
import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.servlet.auth.oauth2.AbstractAuthorizationCodeServlet;
import com.google.api.client.http.GenericUrl;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.exception.ExceptionUtils;

@WebServlet(
    name = "UserAPI",
    description = "UserAPI: Login / Logout with UserService",
    urlPatterns = "/userapi"
)
public class UsersServlet extends HttpServlet
//public class UsersServlet extends AbstractAuthorizationCodeServlet
//public class UsersServlet extends AbstractAppEngineAuthorizationCodeServlet
{
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
      try
      {
        req.getRequestDispatcher("index.html").forward(req, resp);
      }
      catch (ServletException e)
      {
        resp.getWriter().println("Error: " + ExceptionUtils.getStackTrace(e));
      }
    }
//    @Override
//    protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException
//    {
//    return Utils.newFlow();
//    }
//
//    @Override
//    protected String getRedirectUri(final HttpServletRequest req) throws ServletException, IOException
//    {
//    return Utils.getRedirectUri(req);
//    }
//
//    @Override
//    protected String getUserId(final HttpServletRequest req) throws ServletException, IOException
//    {
//        return UserServiceFactory.getUserService().getCurrentUser().getUserId();
//    }
}
