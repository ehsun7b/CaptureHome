package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.dao.ImageDAO;
import com.ehsunbehravesh.capturehome.entity.Image;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ehsun Behravesh
 */
public class LastImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/plain");        
        Image img = getLastImage();
        resp.getWriter().write(img.getContent());        
    }

    private Image getLastImage() {
        DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        ImageDAO dao = new ImageDAO(ds);
        Image image = dao.last();
        return image;
    }
    
   
    
}
