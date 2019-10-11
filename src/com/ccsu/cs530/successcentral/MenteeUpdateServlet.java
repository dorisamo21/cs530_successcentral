package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/mentee_update")
public class MenteeUpdateServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private int page;
    private final int perPageNum = 10;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            String menteeEmail = req.getParameter("menteeEmail");
            String work = req.getParameter("work");
            String returnPage = req.getParameter("returnPage");
            Mentee mentee = crud.getMentee(menteeEmail);
            User user = crud.getUser(menteeEmail);

            // populate the request object to send to the jsp
            req.setAttribute("work", work);
            req.setAttribute("mentee", mentee);
            req.setAttribute("user",user);
            req.setAttribute("returnPage",returnPage);
            req.setAttribute("menteeEmail",menteeEmail);
            req.getRequestDispatcher("mentee_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
