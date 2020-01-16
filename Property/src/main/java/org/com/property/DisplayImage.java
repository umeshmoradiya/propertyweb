package org.com.property;

import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
 
public class DisplayImage extends HttpServlet {
    private static final long serialVersionUID = 4593558495041379082L;
 
    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        try {
 
        	
            String id = request.getParameter("Image_id");
           
            byte[] imageBytes = PropertyDAO.getPropertyImage(id);
                response.reset();
                response.setContentType("image/jpeg");
                
                response.getOutputStream().write(imageBytes);
                response.getOutputStream().close();
 
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
            response.getWriter().close();
        }
    }
}