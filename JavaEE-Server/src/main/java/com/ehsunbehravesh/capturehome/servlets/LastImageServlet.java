package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.dao.ImageDAO;
import com.ehsunbehravesh.capturehome.entity.Image;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ehsun Behravesh
 */
@WebServlet(urlPatterns = {"/last"})
public class LastImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");        
        Image img = getLastImage();
        resp.getWriter().write(img.getContent());        
    }

    private Image getLastImage() {        
        ImageDAO dao = new ImageDAO();
        Image image = dao.last();
        return image;
    }
    
   
    
}
