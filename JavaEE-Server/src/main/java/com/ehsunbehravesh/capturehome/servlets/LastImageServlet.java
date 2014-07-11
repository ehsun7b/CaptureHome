package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.dao.ImageDAO;
import com.ehsunbehravesh.capturehome.entity.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
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
        resp.setContentType("image/png");        
        BufferedImage lastImage = getLastImage();
        ServletOutputStream os = resp.getOutputStream();
        
        ImageIO.write(lastImage, "PNG", os);
        
        os.flush();
        os.close();
    }

    private BufferedImage getLastImage() {        
        ImageDAO dao = new ImageDAO();
        return dao.last();
    }
    
   
    
}
