package Rizpa.Servlets.ManagementServlets.GetBalance;

import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getBalanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        try(PrintWriter out = response.getWriter()){
            String userNameFromSession =SessionUtils.getUserName(request);
            SystemProxy prox = ServletUtils.getProx(getServletContext());
            Gson gson = new Gson();
            float balance = prox.invoke("getUserBalance",userNameFromSession);
            String balanceStr = gson.toJson(balance);
            out.println(balanceStr);
            out.flush();
        } catch (Throwable e) {
            throw new ServletException("Error: "+ e.getCause().getMessage());
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
