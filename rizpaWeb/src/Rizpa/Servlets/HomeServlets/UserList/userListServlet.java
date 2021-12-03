package Rizpa.Servlets.HomeServlets.UserList;

import Rizpa.Utils.ServletUtils;
import System.SystemProxy;
import System.Users.Users;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class userListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        try(PrintWriter out = response.getWriter()){
        Gson gson = new Gson();
       SystemProxy prox = ServletUtils.getProx(getServletContext());
       Users usersList = prox.invoke("getUsers");
       String json = gson.toJson(usersList);
       out.println(json);
       out.flush();
        } catch (Throwable e) {
            throw new ServletException("Error: " +  e.getCause().getMessage());
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
