package Rizpa.Servlets.StockServlets.GetStock;

import Rizpa.Constants.Constants;
import Rizpa.Utils.ServletUtils;
import System.Stocks.Stock;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
public class getStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        try(PrintWriter out = response.getWriter()) {
            SystemProxy prox = ServletUtils.getProx(request.getServletContext());
            String symbol = request.getSession().getAttribute(Constants.CURRENT_STOCK).toString();

            Stock stock = prox.invoke("getStock",symbol);
            Gson gson = new Gson();
            String json = gson.toJson(stock);
            out.println(json);
            out.flush();
        } catch (Throwable e) {
            throw new ServletException("error: " + e.getCause().getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
