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


@WebServlet("/mentee_info")
public class MenteeInfoServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private MatchingService matchingService = new MatchingService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null) {
            String menteeEmail = req.getParameter("menteeID");

            PrintWriter writer = resp.getWriter();
            if(menteeEmail == null){
                writer.println("<script>");
                writer.println("alert('Invalid menteeID')");
                writer.println("location.href='mentee_list'");
                writer.println("</script>");
            }

            Mentee mentee = crud.getMentee(menteeEmail);
            if(mentee.getMentor()!=null) {
                Mentor mentor = mentee.getMentor();
                Match match = new Match(mentor, mentee);
                match.setCompatibility(matchingService.evaluateMatch(mentor, mentee));
                req.setAttribute("match", match);
            }

            // populate the request object to send to the jsp
            req.setAttribute("mentee", mentee);
            req.setAttribute("menteeID", menteeEmail);

            req.getRequestDispatcher("mentee_info.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
