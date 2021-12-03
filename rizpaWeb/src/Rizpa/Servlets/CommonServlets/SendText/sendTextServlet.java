package Rizpa.Servlets.CommonServlets.SendText;

import Rizpa.Constants.Constants;
import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.WebApi.ChatManager;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class sendTextServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChatManager chatManager = ServletUtils.getChat(request.getServletContext());
        String nameFromSession = SessionUtils.getUserName(request);
        String text = request.getParameter(Constants.TEXT);
        synchronized (this){
            chatManager.addMassage(nameFromSession,text);
        }
        return;
    }
}
