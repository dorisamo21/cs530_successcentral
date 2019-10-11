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


@WebServlet("/mentee_list")
public class MenteeListServlet extends HttpServlet {
    private CrudService crud = new CrudService();
    private int page;
    private final int perPageNum = 15;
    private final int perBlockNum = 10;
    int startPage =0;
    int endPage = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getSession().getAttribute("email") != null) {
            //if not admin, bounce to mentor dashboard page
            if (!req.getSession().getAttribute("isAdmin").equals("true")){
                resp.sendRedirect("my_mentee_list");
                return;
            }
            // paging
            String pageNum = req.getParameter("page");
            if(pageNum == null) page=1;
            else page = Integer.parseInt(pageNum);

            int firstRecord = (page -1) * perPageNum;
            int menteeCount = crud.countMentees();
            int pageCount = 1;

            if(menteeCount != 0){
                pageCount = menteeCount / perPageNum + (menteeCount % perPageNum != 0 ? 1 : 0);

                startPage = ((page-1)/perBlockNum)*perBlockNum+1;
                endPage   = startPage + perBlockNum -1;

                if(endPage >pageCount){
                    endPage = pageCount;
                }
            }

            // mentee
            String search = (String)req.getParameter("search");
            String sortBy = (String)req.getParameter("sortBy");
            List<Mentee> mentees = crud.getMentees(firstRecord, perPageNum, search,sortBy);
            Map<Integer,String> allMajors = crud.getAllMajors();


            // populate the request object to send to the jsp
            req.setAttribute("pageCount", pageCount);
            req.setAttribute("mentees", mentees);
            req.setAttribute("allMajors",allMajors);
            req.setAttribute("menteeCount",menteeCount);
            req.setAttribute("perBlockNum", perBlockNum);
            req.setAttribute("startPage",startPage);
            req.setAttribute("endPage",endPage);
            req.setAttribute("page",page);
            req.setAttribute("search",search);
            req.setAttribute("sortBy",sortBy);
            req.getRequestDispatcher("mentee_list.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login");
        }
    }
}
