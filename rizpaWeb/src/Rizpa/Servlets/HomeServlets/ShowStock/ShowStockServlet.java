package Rizpa.Servlets.HomeServlets.ShowStock;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
public class ShowStockServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                String symbol = request.getParameter(Constants.CURRENT_STOCK);
                request.getSession(false).setAttribute(Constants.CURRENT_STOCK,symbol);
                response.setStatus(200);
                response.getOutputStream().println(StandardURL.SHOW_STOCK);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
