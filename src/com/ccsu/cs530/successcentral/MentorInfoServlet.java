package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Match;
import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.service.CrudService;
import com.ccsu.cs530.successcentral.service.MatchingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/mentor_info")
public class MentorInfoServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private MatchingService matchingService = new MatchingService();
    private int page;
    private final int perPageNum = 10;


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            //if not admin, bounce to mentor dashboard page
            if (!req.getSession().getAttribute("isAdmin").equals("true")){
                resp.sendRedirect("my_mentee_list");
                return;
            }
            String mentorEmail = null;
            if(req.getParameter("mentorEmail") != null){
                mentorEmail = req.getParameter("mentorEmail");
            }

            PrintWriter writer = resp.getWriter();
            if(mentorEmail == null) {
                writer.println("<script>");
                writer.println("alert('Invalid mentorID')");
                writer.println("location.href='mentor_list'");
                writer.println("</script>");
            }

            Mentor mentor = crud.getMentor(mentorEmail);
            List<Match> mentorMatches = matchingService.getMyMatches(mentor);

            // populate the request object to send to the jsp
            req.setAttribute("mentor", mentor);
            req.setAttribute("mentorEmail",mentorEmail);
            req.setAttribute("mentorMatches",mentorMatches);
            req.getRequestDispatcher("mentor_info.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
