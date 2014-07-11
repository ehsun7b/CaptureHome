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
        String strTimestamp = req.getParameter("timestamp");
        long timestamp = 0;
        
        try {
            timestamp = Long.parseLong(strTimestamp);
        } catch (Exception e) {}
        
        if (content != null && timestamp > 0) {
            Image image = new Image(timestamp, content);
            
            DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
            ImageDAO dao = new ImageDAO(ds);
            dao.insert(image);
        }
        
        resp.getWriter().write("success");
    }
    
    
}
