package Rizpa.Servlets.CommonServlets.Notifications;

import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.Log.LogTransaction;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getNotifications extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SystemProxy prox = ServletUtils.getProx(request.getServletContext());
        String userNameFromSession = SessionUtils.getUserName(request);

        response.setContentType("application/json");

        try(PrintWriter out = response.getWriter()) {
            synchronized (this) {


                List<LogTransaction> notifications = prox.invoke("getUserLogs",userNameFromSession);

                Gson gson = new Gson();
                String json = gson.toJson(notifications);
                out.println(json);
                out.flush();
            }

        } catch (Throwable throwable) {
                throw new ServletException("Error "+throwable.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
