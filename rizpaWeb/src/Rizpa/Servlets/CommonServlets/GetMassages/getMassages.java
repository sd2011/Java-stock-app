package Rizpa.Servlets.CommonServlets.GetMassages;

import Rizpa.Constants.Constants;
import Rizpa.Utils.ServletUtils;
import System.WebApi.ChatManager;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getMassages extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ChatManager chatManager = ServletUtils.getChat(request.getServletContext());
        int fromIndex = Integer.parseInt(request.getParameter(Constants.CHAT_INDEX));

        response.setContentType("application/json");
        synchronized (this){
            try(PrintWriter out = response.getWriter() ){
              List<?> chat = chatManager.getMassageList(fromIndex);
              int index = chatManager.getLastIndex();
              ChatAndIndex chatAndIndex = new ChatAndIndex(chat , index);
              Gson gson = new Gson();
              String json = gson.toJson(chatAndIndex);
              out.println(json);
              out.flush();
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private class ChatAndIndex{
        List<?> massageList;
        int index;

        ChatAndIndex(List<?> massageList , int index) {
            this.massageList = massageList;
            this.index = index;
        }

    }
}

