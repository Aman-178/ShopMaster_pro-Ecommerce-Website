package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.io.IOException;
import java.io.PrintWriter;


@WebServlet("/AdminUpdateStatusServlet")
public class AdminUpdateStatusServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String username = req.getParameter("email");
        String status = req.getParameter("status");
        Long orderId = Long.valueOf(req.getParameter("OrderID"));

        // Create a Hibernate session
        try (Session session = sessionFactory.openSession()) {
            // Begin a transaction
            Transaction transaction = session.beginTransaction();

            try {
                // Retrieve the order
                Orders order = session.get(Orders.class, orderId);
                
                if (order != null && order.getUsername().equals(username)) {
                    // Update the status
                    order.setStatus(status);
                    session.update(order);
                    
                    // Commit the transaction
                    transaction.commit();
                    
                    System.out.println("Successfully updated");
                    resp.sendRedirect("Admin.html");
                } else {
                    System.out.println("Order not found or username mismatch");
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    resp.setContentType("application/json");
                    PrintWriter out = resp.getWriter();
                    out.print("{\"error\": \"Order not found or username mismatch\"}");
                }
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                System.out.println("Error updating order: " + e.getMessage());
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.setContentType("application/json");
                PrintWriter out = resp.getWriter();
                out.print("{\"error\": \"Database error occurred\"}");
            }
        }
    }
}
