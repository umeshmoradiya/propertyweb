package org.com.property;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.com.property.dao.PropertyDAO;
 
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