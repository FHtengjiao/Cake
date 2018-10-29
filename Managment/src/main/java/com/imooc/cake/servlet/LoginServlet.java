package com.imooc.cake.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login.do", "/loginPrompt.do"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if ("/login.do".equals(request.getServletPath())) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            if (username != null && !"".equals(username) && username.equals(password)) {
                request.getSession().setAttribute("username", username);
                request.getRequestDispatcher("/cake/list.do").forward(request, response);
            } else {
                request.getRequestDispatcher("/loginPrompt.do").forward(request, response);
            }
        } else if ("/loginPrompt.do".equals(request.getServletPath())){
            request.getRequestDispatcher("/WEB-INF/views/biz/login.jsp").forward(request, response);
        }
    }
}
