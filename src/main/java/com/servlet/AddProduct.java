package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;



@WebServlet("/addproduct")
@MultipartConfig(maxFileSize = 16177215)  
public class AddProduct extends HttpServlet {
    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String productName = request.getParameter("productname");
        String productPrice = request.getParameter("productprice");
        String productCategory = request.getParameter("category");
        String productDescription = request.getParameter("description");
        Part productImagePart = request.getPart("productimage"); // Handle file upload
        String companyName = request.getParameter("companyname");

        double productPriceValue = 0.0;
        try {
            if (productPrice != null && !productPrice.isEmpty()) {
                productPriceValue = Double.parseDouble(productPrice);
            }
        } catch (NumberFormatException ex) {
            out.println("Invalid price format. Please enter a valid number.");
            return;
        }

        // Convert image part to byte array if needed
        byte[] imageBytes = null;
        if (productImagePart != null) {
            imageBytes = productImagePart.getInputStream().readAllBytes();
        }

        // Use Hibernate to save the product
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            Product product = new Product(productName,productPriceValue,productCategory,productDescription,
            imageBytes,companyName);
            session.save(product);
            transaction.commit();

            response.sendRedirect("Admin.html?message=Successfully Added!");
        } catch (Exception ex) {
            if (transaction != null) transaction.rollback();
            ex.printStackTrace();
            out.println("An error occurred while adding the product.");
        } finally {
            if (session != null) session.close();
            out.close();
        }
    }
}
