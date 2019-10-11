package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register_mentor")
public class RegisterMentorServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getSession().getAttribute("email") != null) {
            String mentorEmail  = req.getParameter("mentorEmail");
            User user = crud.getUser(mentorEmail);
            req.setAttribute("mentorEmail", user.getEmail());
            req.setAttribute("user",user);
            req.getRequestDispatcher("register_mentor.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
