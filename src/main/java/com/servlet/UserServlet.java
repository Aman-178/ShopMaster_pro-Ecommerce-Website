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
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/UserData")
public class UserServlet extends HttpServlet {

    private static final SessionFactory sessionFactory = DAO.getSessionFactory();

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");

        if (username == null || password == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.write("{\"error\": \"Missing username or password\"}");
            return;
        }

        System.out.println("Username: " + username);

        ArrayList<UserList> userList = new ArrayList<>();

        try (Session hibernateSession = sessionFactory.openSession()) {
            String hql = "FROM Authentication WHERE username = :username AND password = :password";
            Query<Authentication> query = hibernateSession.createQuery(hql, Authentication.class);
            query.setParameter("username", username);
            query.setParameter("password", password);

            Authentication user = query.uniqueResult();
            if (user != null) {
                UserList obj = new UserList();
                obj.setFirstname(user.getFirstname());
                obj.setLastname(user.getLastname());
                obj.setUsername(user.getUsername());
                obj.setMobileno(user.getMobileno());
                obj.setAddress(user.getAddress());

                userList.add(obj);
            }

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, "Error retrieving user", ex);
        }

        // Convert list to JSON using Gson
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(userList);

        // Send JSON response
        out.println(json);
        out.close();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private class UserList {

        private String firstname;
        private String lastname;
        private String username;
        private String mobileno;
        private String address;

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddress() {
            return address;
        }
    }
}
