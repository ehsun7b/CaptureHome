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
public class UploadImageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String content = req.getParameter("content");
        String timestamp = req.getParameter("timestamp");
        
        if (content != null && timestamp != null) {
            Image image = new Image(timestamp, content);
            
            DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            ImageDAO dao = new ImageDAO(ds);
            dao.insert(image);
        }
        
        resp.getWriter().write("success");
    }
    
    
}
