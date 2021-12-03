package Rizpa.Servlets.StockServlets.MakeOrder;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;
import Rizpa.Utils.ServletUtils;
import System.SystemProxy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class makeOrderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        synchronized (this) {
            try {
                SystemProxy prox = ServletUtils.getProx(request.getServletContext());
                String user = request.getSession(false).getAttribute(Constants.USERNAME).toString();
                String symbol = request.getSession(false).getAttribute(Constants.CURRENT_STOCK).toString();
                String orderType = request.getParameter(Constants.ORDER_TYPE).toString();
                String limitStr = orderType.equals("MKT") ? "0" : request.getParameter(Constants.LIMIT);
                String amountStr = request.getParameter(Constants.AMOUNT);
                boolean buyOrSell = Boolean.parseBoolean(request.getParameter(Constants.BUY_OR_SELL));



                if(limitStr.isEmpty()|| amountStr.isEmpty())
                    throw new ServletException("Error, not all fields have been filled");
                if(!ServletUtils.isNumber(limitStr)){
                    throw new ServletException("Error,limit is not a number");
                }
                float limit = Float.parseFloat(limitStr);
                int amount = Integer.parseInt(amountStr);
                boolean fullSuccess = prox.invoke("enterRequest", buyOrSell, symbol, amount, limit, orderType, user);
                String success = fullSuccess ? "Order found matches and sold completely" : "Order uploaded  but did not sold completely ";
                request.setAttribute(Constants.ORDER_SUCCESS_MESSAGE, success);
                request.setAttribute(Constants.ORDER_SUCCESS, true);
                getServletContext().getRequestDispatcher(StandardURL.SHOW_STOCK_FAST).forward(request, response);

            } catch (Throwable e) {
                request.setAttribute(Constants.ORDER_SUCCESS_MESSAGE, e.getMessage());
                request.setAttribute(Constants.ORDER_SUCCESS, false);
                getServletContext().getRequestDispatcher(StandardURL.SHOW_STOCK_FAST).forward(request, response);
            }
        }
    }
}

