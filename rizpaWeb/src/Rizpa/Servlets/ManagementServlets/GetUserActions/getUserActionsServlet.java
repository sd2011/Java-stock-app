package Rizpa.Servlets.ManagementServlets.GetUserActions;

import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.SystemProxy;
import System.Users.Actions;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getUserActionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");

        try(PrintWriter out = response.getWriter()){
            String userNameFromSession = SessionUtils.getUserName(request);
            SystemProxy prox = ServletUtils.getProx(getServletContext());
            Gson gson = new Gson();
            Actions actions = prox.invoke("getUserActions",userNameFromSession);
            String json = gson.toJson(actions);
            out.println(json);
            out.flush();
        } catch (Throwable e) {
            throw new ServletException("Error: "+ e.getCause().getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
