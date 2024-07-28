package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

@WebServlet("/LoginSuccess")
public class LoginSuccess extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();
    private static final String SECRET_KEY = GenrateSecretKey.SecretKey(); // Use a secure method to handle this key

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username);

        if (isValid(username, password)) {
            String token = JWTUtil.generateToken(username, SECRET_KEY);
            response.setContentType("application/json");
            response.getWriter().write("{\"accessToken\": \"" + token + "\"}");

            // Create or get an existing session
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("password", password);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isValid(String username, String password) {
        try (Session session = sessionFactory.openSession()) {
            // Use Hibernate to query the user
            Query<Authentication> query = session.createQuery("FROM Authentication WHERE username = :username AND password = :password", Authentication.class);
            query.setParameter("username", username);
            query.setParameter("password", password); // Ensure passwords are hashed
            Authentication user = query.uniqueResult();
            return user != null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
