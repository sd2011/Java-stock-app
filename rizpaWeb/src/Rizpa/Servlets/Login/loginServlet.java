package Rizpa.Servlets.Login;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;
import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.WebApi.UserManager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String userNameFromSession = SessionUtils.getUserName(request);
        UserManager userManager = ServletUtils.getUserManager(getServletContext());


        if (userNameFromSession == null){
            String userNameFromParmeter =request.getParameter(Constants.USERNAME);


            if(userNameFromParmeter ==null || userNameFromParmeter.isEmpty()){
                if(request.getParameter(Constants.USER_CHECK) == null)
                response.sendRedirect(StandardURL.INDEX_URL);
                else{
                    response.setStatus(200);
                    response.getOutputStream().println(StandardURL.INDEX_URL);
                }
            }
            else{
                userNameFromParmeter = userNameFromParmeter.trim();

                synchronized (this){
                        if(userManager.isUserExists(userNameFromParmeter)){
                            String errorMassage = " Looks like user \""+userNameFromParmeter+"\" is already existing in the system, " +
                                    "\n please enter a different and unique name";

                            request.setAttribute(Constants.USER_ERROR ,errorMassage);
                            getServletContext().getRequestDispatcher(StandardURL.ERROR_LOGIN).forward(request,response);
                        }
                        else{
                            String userType = request.getParameter(Constants.USER_TYPE);
                            try {
                                userManager.addUser(userNameFromParmeter,userType );
                            } catch (Throwable e) {

                                request.setAttribute(Constants.USER_ERROR ,e.getCause().getMessage());
                                getServletContext().getRequestDispatcher(StandardURL.ERROR_LOGIN).forward(request,response);

                            }


                            request.getSession(true).setAttribute(Constants.USERNAME, userNameFromParmeter);
                            System.out.println("On login ,request URI is: "+ request.getRequestURI());
                            response.sendRedirect(StandardURL.HOME_PAGE);
                        }
                }
            }

        }
        else{
            if(request.getParameter(Constants.USER_CHECK) == null)
                response.sendRedirect(StandardURL.HOME_PAGE);
             else{
                response.setStatus(200);
                response.getOutputStream().println(StandardURL.HOME_PAGE);
            }
        }

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
