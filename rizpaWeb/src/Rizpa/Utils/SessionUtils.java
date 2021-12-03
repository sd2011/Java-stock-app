package Rizpa.Utils;

import Rizpa.Constants.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {


    public static String getUserName(HttpServletRequest request){
        HttpSession session = request.getSession(false  );
        Object sessionAtribute = session != null ? session.getAttribute(Constants.USERNAME) : null;
        return sessionAtribute != null ? sessionAtribute.toString() : null;
    }


}
