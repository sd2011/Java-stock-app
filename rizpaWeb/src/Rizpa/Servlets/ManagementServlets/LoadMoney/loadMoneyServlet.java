package Rizpa.Servlets.ManagementServlets.LoadMoney;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;
import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.SystemProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class loadMoneyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        synchronized (this){
            try{
                String moneyStr =request.getParameter(Constants.MONEY_AMOUNT);
                if(moneyStr == null || moneyStr.trim().isEmpty())
                    throw new ServletException("Error, please make sure to fill every field before submitting");
                if(!ServletUtils.isNumber(moneyStr))
                    throw new ServletException("Error, amount need to be a number");
               float money = Float.parseFloat(moneyStr);
               if(money <=0)
                   throw new ServletException("Error, amount need to be a positive amount");
               String userNameFromSession = SessionUtils.getUserName(request);
               SystemProxy prox = ServletUtils.getProx(getServletContext());


              prox.invoke("addToUserBalance",userNameFromSession,money);
              String success = "Money has been loaded successfully!";
              request.setAttribute(Constants.POP_SUCCESS_MASSAGE,success);
              getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request,response);
           } catch (Throwable e) {
              request.setAttribute(Constants.POP_SUCCESS_MASSAGE, e.getMessage());
              getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request,response);
           }
       }
    }
}
