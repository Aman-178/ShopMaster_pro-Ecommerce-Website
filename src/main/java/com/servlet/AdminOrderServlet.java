package com.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/Adminorderlist")
public class AdminOrderServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        List<OrderData> orderList = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM Orders";
            Query<Orders> query = session.createQuery(hql, Orders.class);
            List<Orders> orders = query.list();

            for (Orders order : orders) {
                OrderData obj = new OrderData();
                obj.setID(order.getId());
                obj.setUserName(order.getUsername());
                obj.setStatus(order.getStatus());
                obj.setProductName(order.getProductName());
                obj.setProductPrice(order.getProductPrice());

                orderList.add(obj);
            }
        } catch (Exception ex) {
            Logger.getLogger(AdminOrderServlet.class.getName()).log(Level.SEVERE, "Error retrieving orders", ex);
        }

        // Convert list to JSON using Gson
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(orderList);

        // Send JSON response
        out.println(json);
        out.close();
    }

    private class OrderData {
        private Long ID;
        private String UserName;
        private String Status;
        private String ProductName;
        private String ProductPrice;

        public void setID(Long ID) {
            this.ID = ID;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public void setStatus(String Status) {
            this.Status = Status;
        }

        public void setProductName(String ProductName) {
            this.ProductName = ProductName;
        }

        public void setProductPrice(String ProductPrice) {
            this.ProductPrice = ProductPrice;
        }

        public Long getID() {
            return ID;
        }

        public String getUserName() {
            return UserName;
        }

        public String getStatus() {
            return Status;
        }

        public String getProductName() {
            return ProductName;
        }

        public String getProductPrice() {
            return ProductPrice;
        }
    }
}
