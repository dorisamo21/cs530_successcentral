package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Match;
import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.service.CrudService;
import com.ccsu.cs530.successcentral.service.MatchingService;
import com.ccsu.cs530.successcentral.util.DatabaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.ServerException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.*;

@WebServlet("/dynamic_matches")
public class DynamicMatchesServlet extends HttpServlet {

    private MatchingService matchingService = new MatchingService();
    private CrudService crud = new CrudService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null && req.getSession().getAttribute("isAdmin") == "true") {

            // if a mentee has been selected, do the match
            if (req.getParameter("email") != null) {
                //if not admin, bounce to mentor dashboard page
                if (!req.getSession().getAttribute("isAdmin").equals("true")){
                    resp.sendRedirect("my_mentee_list");
                    return;
                }
                // Check to make sure the email is legit.
                if (crud.getMentee(req.getParameter("email")).getEmail() != null) {
                    Mentee mentee = crud.getMentee(req.getParameter("email"));
                    String[] menteeHobbies = mentee.getHobbiesArray();
                    if (mentee == null) {
                        req.getSession().setAttribute("message", "Mentee data could not be found.");
                    } else {
                        List<Match> matches = matchingService.getTopNMatches(mentee, 5);
                        List<Match> matchesAll = matchingService.getAllMatches(mentee);

                        req.setAttribute("matches", matches);
                        req.setAttribute("matchesAll", matchesAll);
                        req.setAttribute("menteeHobbies", menteeHobbies);
                        req.setAttribute("mentee", mentee);
                    }
                } else {
                    String message = "<p class=\"text-danger\">The email is not recognized. Please try again.</p>";
                    req.getSession().setAttribute("message", message);
                    resp.sendRedirect("dynamic_matches");
                    return;
                }
            }

            // get orphans
            List<Mentee> orphans = crud.getOrphanMentees();

            req.setAttribute("orphans", orphans);
            req.getRequestDispatcher("dynamic_matches.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (req.getSession().getAttribute("email") != null) {
            String mentorEmail = req.getParameter("mentorEmail");
            String menteeEmail = req.getParameter("menteeEmail");
            if (mentorEmail == null || menteeEmail == null) {
                req.getSession().setAttribute("message", "<p class=\"text-danger\">Match Unsuccessful. Either the mentor or mentee was not selected.</p>");
                resp.sendRedirect("dynamic_matches");
                return;
            }
            crud.updateMenteeWithMentor(menteeEmail, mentorEmail);

            req.getSession().setAttribute("message", "<p class=\"text-success\">Match Successfully Created!</p>");
            resp.sendRedirect("dynamic_matches");
        } else {
            resp.sendRedirect("login");
        }
    }

}

