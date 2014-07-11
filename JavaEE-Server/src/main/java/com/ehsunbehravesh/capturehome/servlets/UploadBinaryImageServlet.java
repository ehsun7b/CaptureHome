package com.ehsunbehravesh.capturehome.servlets;

import com.ehsunbehravesh.capturehome.dao.ImageDAO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ehsun Behravesh
 */
@WebServlet(urlPatterns = {"/uploadbinary"})
public class UploadBinaryImageServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletInputStream is = req.getInputStream();        
        BufferedImage image = ImageIO.read(is);
        
        ImageDAO dao = new ImageDAO();
        dao.insert(image);
    }
    
    
}
