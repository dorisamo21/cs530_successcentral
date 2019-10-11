package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/my_mentee_list")
public class MyMenteeListServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String mentorEmail = (String)req.getSession().getAttribute("email");
        if (mentorEmail != null) {
            //if admin, redirect to general mentee list
            if (req.getSession().getAttribute("isAdmin").equals("true")){
                resp.sendRedirect("mentee_list");
                return;
            } else {

                List<Mentee> mentees = crud.getMyMentees(mentorEmail);
//                Map<Integer, String> allMajors = crud.getAllMajors();
                req.setAttribute("menteeCnt",mentees.size());
                req.setAttribute("mentees", mentees);
//                req.setAttribute("allMajors", allMajors);
                req.getRequestDispatcher("my_mentee_list.jsp").forward(req, resp);
            }
        }
        else
        {
            resp.sendRedirect("login");
        }

    }
}
