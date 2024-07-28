package com.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/OrderList")
public class OrderShowServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);

        List<OrderList> listOrder = new ArrayList<>();

        try (Session hibernateSession = sessionFactory.openSession()) {
            String hql = "FROM Orders WHERE username = :Username";
            Query<Orders> query = hibernateSession.createQuery(hql, Orders.class);
            query.setParameter("Username", username);
            List<Orders> orders = query.getResultList();

            for (Orders order : orders) {
                OrderList obj = new OrderList();
                obj.setProductId(order.getId().toString());
                obj.setProductName(order.getProductName());
                obj.setProductPrice(order.getProductPrice());
                obj.setStatus(order.getStatus());

                listOrder.add(obj);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderShowServlet.class.getName()).log(Level.SEVERE, "Error retrieving orders", ex);
        }

        // Convert list to JSON using Gson
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(listOrder);

        // Send JSON response
        out.println(json);
    }

    private class OrderList {
        private String productId;
        private String productName;
        private String productPrice;
        private String status;

        // Getters and Setters
        public void setProductId(String productId) {
            this.productId = productId;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProductId() {
            return productId;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public String getStatus() {
            return status;
        }
    }
}
