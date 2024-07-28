/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author ACCESS
 */
@WebServlet("/loginpage")
public class Login extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest request ,HttpServletResponse response)throws IOException ,ServletException{
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        
        RequestDispatcher req=request.getRequestDispatcher("Login.html");
        req.forward(request,response);
        out.println(req);
    }
    
}
