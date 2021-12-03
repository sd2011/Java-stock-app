package Rizpa.Servlets.CommonServlets.ReturnHome;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ReturnHome extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getSession().setAttribute(Constants.CURRENT_STOCK,null);
            response.sendRedirect(StandardURL.HOME_PAGE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
