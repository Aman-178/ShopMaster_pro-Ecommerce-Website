package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/submit-form")
public class ReadProduct extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hql = "FROM Product";

        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery(hql, Product.class);
            List<Product> productList = query.list();

            StringBuilder htmlContent = new StringBuilder();
            htmlContent.append("<!DOCTYPE html>");
            htmlContent.append("<html lang=\"en\">");
            htmlContent.append("<head><meta charset=\"UTF-8\"><title>Product List</title></head>");
            htmlContent.append("<body style=\"background-color: gray; display:flex; justify-content:center\">");
            htmlContent.append("<div style=\"height:500px;width:500px;color:whitesmoke\">");
            htmlContent.append("<h1 style=\"margin-left:40px;color:green;\">Product List</h1>");
            htmlContent.append("<ul>");

            for (Product product : productList) {
                htmlContent.append("<li style=\"padding:10px\">")
                           .append("Name: ").append(product.getName()).append("<br/>")
                           .append("Price: ").append(product.getPrice()).append("<br/>")
                           .append("Category: ").append(product.getCategory()).append("<br/>")
                           .append("Image: ").append("<img src=\"data:image/jpeg;base64,").append(java.util.Base64.getEncoder().encodeToString(product.getImage())).append("\" alt=\"Product Image\" width=\"100\" height=\"100\"><br/>")
                           .append("Description: ").append(product.getDescription())
                           .append("</li>");
            }

            htmlContent.append("</ul>");
            htmlContent.append("</div>");
            htmlContent.append("</body></html>");

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println(htmlContent.toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error accessing the database", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward POST requests to doGet for processing
        doGet(request, response);
    }
}
