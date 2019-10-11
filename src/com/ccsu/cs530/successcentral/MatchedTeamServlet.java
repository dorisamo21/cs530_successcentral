package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@WebServlet("/matched_team")
public class MatchedTeamServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private int page;
    private final int perPage = 5;
    private final int perBlock = 10;
    int startPage =0;
    int endPage = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("email") != null && req.getSession().getAttribute("isAdmin") == "true") {
            // paging
            String pageNum = req.getParameter("page");
            if(pageNum == null) page=1;
            else page = Integer.parseInt(pageNum);

            int firstRecord = (page -1) * perPage;
            int mentorCount = crud.countMatchedMentors();
            int pageCount = 1;

            if(mentorCount != 0){

                pageCount = mentorCount / perPage + (mentorCount % perPage != 0 ? 1 : 0);

                startPage = ((page-1)/perBlock)*perBlock+1;
                endPage   = startPage + perBlock -1;

                if(endPage > pageCount){
                    endPage = pageCount;
                }
            }

            String search = (String)req.getParameter("search");
            String sortBy = (String)req.getParameter("sortBy");
            // mentors
            List<Mentor> mentors = crud.getMatchedMentorsWithMenteeCount(firstRecord, perPage,search,sortBy);
            Map<Integer,String> allMajors = crud.getAllMajors();
            // populate the request object to send to the jsp
            req.setAttribute("pageCount", pageCount);
            req.setAttribute("mentors", mentors);
            req.setAttribute("firstRecord",firstRecord);
            req.setAttribute("allMajors",allMajors);
            req.setAttribute("perBlock", perBlock);
            req.setAttribute("startPage",startPage);
            req.setAttribute("endPage",endPage);
            req.setAttribute("page",page);
            req.setAttribute("search",search);
            req.setAttribute("sortBy",sortBy);
            req.getRequestDispatcher("matched_team.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }


}
