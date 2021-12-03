package Rizpa.Servlets.StockServlets.GetItemQuantity;

import Rizpa.Constants.Constants;
import Rizpa.Utils.ServletUtils;
import System.SystemProxy;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class getItemQuantityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        response.setContentType("application/json");
        synchronized (this) {
            try (PrintWriter out = response.getWriter()) {
                SystemProxy prox = ServletUtils.getProx(request.getServletContext());
                String user = request.getSession(false).getAttribute(Constants.USERNAME).toString();
                String symbol = request.getSession(false).getAttribute(Constants.CURRENT_STOCK).toString();

                int quantity = prox.invoke("getUserItemQuantity", user, symbol);
                Gson gson = new Gson();
                String json = gson.toJson(quantity);
                out.println(json);
                out.flush();
            } catch (Throwable e) {
                throw new ServletException("error: " + e.getCause().getMessage());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
