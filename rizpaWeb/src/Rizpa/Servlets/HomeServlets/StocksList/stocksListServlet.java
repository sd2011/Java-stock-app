package Rizpa.Servlets.HomeServlets.StocksList;

import Rizpa.Utils.ServletUtils;
import System.Stocks.Stocks;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class stocksListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            SystemProxy prox = ServletUtils.getProx(request.getServletContext());

            Stocks stocks = prox.invoke("getStocks");
            Gson gson = new Gson();
            String json = gson.toJson(stocks);
            out.println(json);
            out.flush();
        } catch (Throwable throwable) {
            throw new ServletException("error: " + throwable.getCause().getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
