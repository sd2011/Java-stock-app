package Rizpa.Servlets.StockServlets.GetOrders;

import Rizpa.Constants.Constants;
import Rizpa.Utils.ServletUtils;
import System.Stocks.Request;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class getOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String symbol = request.getSession()
                .getAttribute(Constants.CURRENT_STOCK)
                .toString();
        SystemProxy prox = ServletUtils.getProx(request.getServletContext());
        if(symbol == null)
            throw new ServletException("stock dose not exist");

        response.setContentType("application/json");
    synchronized (this) {
        try (PrintWriter out = response.getWriter()) {
            List<Request> orders = prox.invoke("getStockAllRequests", symbol);
            Gson gson = new Gson();
            String json = gson.toJson(orders);
            out.println(json);


        } catch (Throwable e) {
            throw new ServletException("ERROR: " + e.getCause().getMessage());
        }
    }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
