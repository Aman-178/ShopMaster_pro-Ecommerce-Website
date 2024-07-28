package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

@WebServlet("/SignUpForm")
public class SignupServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String mobileno = request.getParameter("Mobileno");
        String address = request.getParameter("address");
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");

        if (!password.equals(confirmPassword)) {
            response.sendRedirect("SignUp.html?error=Password%20Doesn%27t%20Match");
            return;
        }

        if (userExists(username)) {
            response.sendRedirect("SignUp.html?error=Username%20Already%20Exists");
            return;
        }

        Authentication user = new Authentication(username, password, firstname, lastname, mobileno, address);
        saveUser(user, response);
    }

    private boolean userExists(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<Authentication> query = session.createQuery("FROM Authentication WHERE username = :username", Authentication.class);
            query.setParameter("username", username);
            Authentication user = query.uniqueResult();
            return user != null;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveUser(Authentication user, HttpServletResponse response) throws IOException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            response.sendRedirect("index.html");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            response.sendRedirect("SignUp.html?error=Error%20Registering%20User");
        }
    }
}
