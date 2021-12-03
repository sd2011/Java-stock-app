package Rizpa.Servlets.HomeServlets.AddFile;

import Rizpa.Constants.Constants;
import Rizpa.StandardURL.StandardURL;
import Rizpa.Utils.ServletUtils;
import Rizpa.Utils.SessionUtils;
import System.SystemProxy;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 *1,
        maxFileSize =  1204 * 1204 * 10,
        maxRequestSize = 204 * 1204 * 100
)

public class addFileServlet extends HttpServlet {
private String getFilePath(String fileName) {
    return "\\Rizpa\\Uploads\\" + fileName;

}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        SystemProxy prox = ServletUtils.getProx(getServletContext());
        String userName = SessionUtils.getUserName(request);

        try {
            synchronized (this) {
                File file = new File("temp");
                for (Part part : request.getParts()) {
                    part.write(file.getAbsolutePath());
                }

                prox.invoke("load",file.getAbsolutePath(),userName);
               file.delete();

                String successMessage = "File uploaded successfully";

                request.setAttribute(Constants.FILE_SUCCESS_MESSAGE,successMessage);
                request.setAttribute(Constants.FILE_SUCCESS , true);
                getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request,response);
            }


        } catch (Throwable e ) {
            request.setAttribute(Constants.FILE_SUCCESS_MESSAGE,e.getMessage());
            request.setAttribute(Constants.FILE_SUCCESS,false);
            getServletContext().getRequestDispatcher(StandardURL.HOME_PAGE_FAST).forward(request,response);
        }
    }

}
