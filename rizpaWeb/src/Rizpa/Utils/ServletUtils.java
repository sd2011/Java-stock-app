package Rizpa.Utils;

import Rizpa.Constants.Constants;
import System.WebApi.ChatManager;
import System.WebApi.UserManager.UserManager;
import System.SystemProxy;

import javax.servlet.ServletContext;

public class ServletUtils {



    private static final Object userManagerLock = new Object();
    private static final Object systemProxLock = new Object();
    private static final Object ChatLock = new Object();


    public static UserManager getUserManager(ServletContext servletContext){

        synchronized (userManagerLock){
            if(servletContext.getAttribute(Constants.USER_MANAGER) == null){
                servletContext.setAttribute(Constants.USER_MANAGER , new UserManager(ServletUtils.getProx(servletContext)));

            }
        }
        return (UserManager) servletContext.getAttribute(Constants.USER_MANAGER);

    }

    public static SystemProxy getProx(ServletContext servletContext){

        synchronized (systemProxLock){
            if(servletContext.getAttribute(Constants.SYSTEM_PROX) == null){
                servletContext.setAttribute(Constants.SYSTEM_PROX,new SystemProxy() );
            }
            return (SystemProxy)  servletContext.getAttribute(Constants.SYSTEM_PROX);
        }
    }

    public static ChatManager getChat(ServletContext servletContext){

        synchronized (ChatLock){
            if(servletContext.getAttribute(Constants.CHAT_MANAGER) == null){
                servletContext.setAttribute(Constants.CHAT_MANAGER,new ChatManager() );
            }
            return (ChatManager)  servletContext.getAttribute(Constants.CHAT_MANAGER);
        }
    }

    public static boolean isNumber(String num){
        try {
            Double.parseDouble(num);
            return true;
        }catch(NumberFormatException e){
        return false;
            }
    }

}
