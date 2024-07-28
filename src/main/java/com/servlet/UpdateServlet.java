package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/updateproduct")
public class UpdateServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String id = request.getParameter("productid");
        String colname = request.getParameter("productcolumn");
        String value = request.getParameter("Productvalue");

        if (id != null && !id.isEmpty()
                && colname != null && !colname.isEmpty()
                && value != null && !value.isEmpty()) {

            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();
                
                // Construct the HQL query dynamically
                String hql = "UPDATE Product SET " + colname + " = :value WHERE id = :id";
                Query query = session.createQuery(hql);
                query.setParameter("value", value);
                query.setParameter("id", Long.parseLong(id));
                
                int result = query.executeUpdate();
                tx.commit();
                
                if (result > 0) {
                    response.sendRedirect("Admin.html");
                } else {
                    out.println("Error in updating.");
                }

            } catch (Exception ex) {
                Logger.getLogger(UpdateServlet.class.getName()).log(Level.SEVERE, null, ex);
                out.println("Error: " + ex.getMessage());
            }

        } else {
            out.println("Please provide all parameters.");
        }
    }
}
