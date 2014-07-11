package com.ehsunbehravesh.capturehome.servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Ehsun Behravesh
 */
@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");

        if ("iran777".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("loggedIn", "true");
            resp.sendRedirect("/");
        } else {
            RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/jsp/login.jsp");
            rd.forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("WEB-INF/jsp/login.jsp");
        rd.forward(req, resp);
    }

}
