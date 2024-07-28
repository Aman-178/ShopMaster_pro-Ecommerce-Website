package com.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query; 

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/orderReceive")
public class OrderReceiveServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println("username");
        StringBuilder jsonBuffer = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                jsonBuffer.append(line);
            }
        }

        // Convert JSON string to a Map
        String jsonString = jsonBuffer.toString();
        Gson gson = new Gson();
        Map<String, List<String>> dataMap = gson.fromJson(jsonString, new TypeToken<Map<String, List<String>>>() {}.getType());

        List<String> itemNameArray = dataMap.get("names");
        List<String> itemPriceArray = dataMap.get("prices");

        // Use Hibernate to save data
        saveOrders(itemNameArray, itemPriceArray, username);
    }

    private void saveOrders(List<String> itemNameArray, List<String> itemPriceArray, String username) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            for (int i = 0; i < itemNameArray.size(); i++) {
                String productName = itemNameArray.get(i);
                String productPrice = itemPriceArray.get(i);
                Orders order = new Orders(productName, productPrice, username);
                session.save(order);
            }

            transaction.commit();
            System.out.println("Data Inserted");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            Logger.getLogger(OrderReceiveServlet.class.getName()).log(Level.SEVERE, "Error saving orders", e);
        }
    }
}
