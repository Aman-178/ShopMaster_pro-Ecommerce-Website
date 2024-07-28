package com.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebServlet("/FetchData")
public class JsonData extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void init() throws ServletException {
        try {
            // Verify Hibernate session factory initialization
            if (sessionFactory == null) {
                throw new ServletException("Hibernate SessionFactory is not initialized");
            }
            System.out.println("Hibernate SessionFactory Successfully Initialized");
        } catch (Exception e) {
            throw new ServletException("Error initializing Hibernate SessionFactory", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        // Get filter parameters
        String[] selectedLaptops = request.getParameterValues("Laptops");
        String[] selectedMobile = request.getParameterValues("Mobiles");
        String selectedPrice = request.getParameter("Price");
        String searchTerm = request.getParameter("searchTerm");

        ArrayList<DataList> list = new ArrayList<>();

        // Create a Hibernate session
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Product WHERE 1=1";

            if (searchTerm != null && !searchTerm.isEmpty()) {
                hql += " AND (name LIKE :searchTerm OR category LIKE :searchTerm OR company LIKE :searchTerm)";
            }
            if (selectedLaptops != null && selectedLaptops.length > 0) {
                hql += " AND company IN (:laptops)";
            }
            if (selectedMobile != null && selectedMobile.length > 0) {
                hql += " AND company IN (:mobiles)";
            }
            if (selectedPrice != null && !selectedPrice.isEmpty()) {
                hql += " AND price <= :price";
            }

            Query<Product> query = session.createQuery(hql, Product.class);

            // Set parameters for query
            if (searchTerm != null && !searchTerm.isEmpty()) {
                query.setParameter("searchTerm", "%" + searchTerm + "%");
            }
            if (selectedLaptops != null && selectedLaptops.length > 0) {
                query.setParameterList("laptops", List.of(selectedLaptops));
            }
            if (selectedMobile != null && selectedMobile.length > 0) {
                query.setParameterList("mobiles", List.of(selectedMobile));
            }
            if (selectedPrice != null && !selectedPrice.isEmpty()) {
                try {
                    double price = Double.parseDouble(selectedPrice);
                    query.setParameter("price", price);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.println("{\"error\": \"Invalid price format\"}");
                    return;
                }
            }

            // Execute query
            List<Product> products = query.list();
            System.out.println("Query executed, products found: " + products.size());

            for (Product product : products) {
                DataList obj = new DataList();
                obj.SetProductName(product.getName());
                obj.SetProductPrice(String.valueOf(product.getPrice()));
                obj.SetProductCategory(product.getCategory());
                obj.SetProductDescription(product.getDescription());

                // Convert byte[] to Base64 encoded string
                if (product.getImage() != null) {
                    String base64Image = Base64.getEncoder().encodeToString(product.getImage());
                    obj.SetProductImage(base64Image);
                } else {
                    obj.SetProductImage(null); // Or set a default image or placeholder
                }

                list.add(obj);
            }

            // Convert list to JSON using Gson
            Gson gson = new GsonBuilder().create();
            String json = gson.toJson(list);

            // Send JSON response
            out.println(json);
        } catch (Exception e) {
            // Handle database errors
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("{\"error\": \"Error fetching data from database\"}");
        }
    }
}
