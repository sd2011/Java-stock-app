package Rizpa.Servlets.ManagementServlets.IssueShare;

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

public class issueShareServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        synchronized (this){
        try{
            SystemProxy prox = ServletUtils.getProx(getServletContext());
            String companyName = request.getParameter(Constants.COMPANY_NAME);
            String symbol = request.getParameter(Constants.SYMBOL);
            String issueAmountStr = request.getParameter(Constants.ISSUE_AMOUNT);
            String estimatedValueStr = request.getParameter(Constants.ESTIMATED_VALUE);

            if(companyName.equals("") || symbol.equals("") || issueAmountStr.equals("") || estimatedValueStr.equals(""))
                throw new ServletException("Error, please fill every field");
            if(!ServletUtils.isNumber(estimatedValueStr))
                throw new ServletException("Error , estimated value must be a number");
            int issueAmount = Integer.parseInt(issueAmountStr);
            float estimatedValue = Float.parseFloat(estimatedValueStr);
            if(estimatedValue <=0)
                throw new ServletException("Error, estimated value must be a positive number");
            float gate = (int)(estimatedValue/issueAmount);
            if(gate <= 0)
                throw new ServletException("Error, make sure estimated value is bigger than issue  amount");

            String userNameFromSession = SessionUtils.getUserName(request);

            prox.invoke("setStock" , companyName,symbol,gate);
            prox.invoke("setItem" ,userNameFromSession, symbol,(int)gate);

            String success = "Stock has been issued successfully";
            request.setAttribute(Constants.POP_SUCCESS_MASSAGE, success);
            getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request, response);

        } catch (Throwable e) {
            String err = e.getMessage();
            request.setAttribute(Constants.POP_SUCCESS_MASSAGE, err);
            getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request, response);
        }
                        }
    }
}
