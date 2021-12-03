package Rizpa.Servlets.CommonServlets.Notifications;

import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.SystemProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class deleteNotifications extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SystemProxy prox = ServletUtils.getProx(request.getServletContext());
        String userNameFromSession = SessionUtils.getUserName(request);

        synchronized (this){
            try {
                prox.invoke("deleteUserNotifications",userNameFromSession);
            } catch (Throwable e) {
                throw new ServletException(e.getMessage());
            }

        }

    }
}
