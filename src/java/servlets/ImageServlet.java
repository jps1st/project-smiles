/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author John Philip O. Solano johnphilipsolano@gmail.com
 */
@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {

    //public static final String IMAGE_FOLDER = "c:/Users/admin/var/smiles/images/";
     public static final String IMAGE_FOLDER = "/var/smiles/images/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            String filename = request.getPathInfo().substring(1);
            File file = new File(IMAGE_FOLDER, filename);
            if (!file.exists()) {
                String fn = filename.contains("_sub.jpeg") ? "default_sub.png" : "default_main.png";
                file = new File(IMAGE_FOLDER, fn);
            }
            response.setHeader("Content-Type", getServletContext().getMimeType(filename));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            response.setHeader("Content-Disposition", "inline; filename=\"" + filename + "\"");
            Files.copy(file.toPath(), response.getOutputStream());
        } catch (IOException iOException) {
        }
    }

}
