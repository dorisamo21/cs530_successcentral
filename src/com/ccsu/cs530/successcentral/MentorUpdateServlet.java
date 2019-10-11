package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/mentor_update")
public class MentorUpdateServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private int page;
    private final int perPageNum = 10;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            String mentorEmail = req.getParameter("mentorEmail");
            String work = req.getParameter("work");
            String returnPage = req.getParameter("returnPage");
            String reg_email = req.getParameter("reg_email");

            Mentor mentor = crud.getMentor(mentorEmail);
            User user = crud.getUser(mentorEmail);

            // populate the request object to send to the jsp
            req.setAttribute("work", work);
            req.setAttribute("mentor", mentor);
            req.setAttribute("returnPage",returnPage);
            req.setAttribute("user",user);
            req.setAttribute("mentorEmail",mentorEmail);
            req.setAttribute("reg_email",reg_email);

            req.getRequestDispatcher("mentor_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
