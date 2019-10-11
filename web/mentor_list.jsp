<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Mentor List"/>
    </jsp:include>
    <body>
        <jsp:include page="includes/navbar.jsp"/>
        <!-- Display flash message -->
        <% if (request.getSession().getAttribute("message") != null) { %>
        <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
        <% request.getSession().removeAttribute("message");} %>

        <!-- Heading -->
        <div class="col text-center">
            <h2>Mentors</h2>
        </div>

        <div class="container-liquid m-4" >
            <div class="m-2 row">
                <div class="col align-items-center">
                    <form class="form-inline" action="mentor_list" method="get">
                        <div class="form-row align-items-center">
                            <div class="col-auto">
                                <div class="form-group">
                                    <label class="col-form-label text-right">Search for a Mentor by Name</label>
                                </div>
                            </div>
                            <%String search, sortBy;
                                if(request.getAttribute("search") != null){
                                    search = (String)request.getAttribute("search");
                                } else search = "";

                                if(request.getAttribute("sortBy")!= null) sortBy = (String)request.getAttribute("sortBy");
                                else sortBy = "";%>
                            <div class="col-auto">
                                <input type="text"
                                       autocomplete="off"
                                       spellcheck="false"
                                       name="search"
                                       value="<%=search%>"
                                />
                                <button type="submit" class="btn btn-sm btn-primary"> Search </button>
                            </div>
                        </div>
                    </form>
                </div>
                <div>
                    <div class="col" align="right">
                        <form class="form-inline" action="mentor_list" method="get">
                            <div class="form-row alight-items-center">
                                <div class="col-auto" >
                                    <div class="form-group ">
                                        <label class="col-form-label text-right">Sort mentors by</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-auto">
                                <select name="sortBy" onchange="this.form.submit()">
                                    <option value=""  <%if(sortBy.equals("")) {%>selected<%}%>></option>
                                    <option value="last_name" <%if(sortBy.equals("last_name")) {%>selected<%}%>>Last Name</option>
                                    <option value="first_name" <%if(sortBy.equals("first_name")) {%>selected<%}%>>First Name</option>
                                    <option value="email" <%if(sortBy.equals("email")) {%>selected<%}%>>Email Address</option>
                                    <option value="major" <%if(sortBy.equals("major")) {%>selected<%}%>>Major (Alphabetic order)</option>
                                    <option value="mentee_cnt_desc" <%if(sortBy.equals("mentee_cnt_desc")) {%>selected<%}%>>Mentee Count (Descending)</option>
                                    <option value="mentee_cnt_asc" <%if(sortBy.equals("mentee_cnt_asc")) {%>selected<%}%>>Mentee Count (Ascending)</option>
                                    <option value="is_approved_1"  <%if(sortBy.equals("is_approved_1")) {%>selected<%}%>>Accepted Mentors First</option>
                                    <option value="is_approved_0"  <%if(sortBy.equals("is_approved_0")) {%>selected<%}%>>Not Accepted Mentors First</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- Mentor Table -->
                <table class="table table-sm table-striped table-bordered table-responsive-sm whitetable" >
                    <thead class="table-secondary">
                        <tr class="text-center">
                            <th scope="col">Email</th>
                            <th scope="col">Name</th>
                            <th scope="col">Student ID</th>
                            <th scope="col" width="300">Major</th>
                            <th scope="col">Number of Mentees</th>
                            <th scope="col">Accept as Mentor</th>
                            <th scope="col">Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <form name="delete_mentor" action="mentor_update" method="post">
                            <input type="hidden" name="work" value="">
                            <input type="hidden" name="mentorEmail" value="">
                            <input type="hidden" name="returnPage" value="mentor_list">

                            <% for (Mentor mentor : (List<Mentor>) request.getAttribute("mentors")) {%>
                                <tr class="text-center">
                                    <th scope="row"><a href="mentor_info?mentorEmail=<%=mentor.getEmail()%>"><%= mentor.getEmail() %></a></th>
                                    <td>
                                        <%= mentor.getFirstName() + " " + mentor.getLastName()%>
                                        <%if(mentor.getSeniorMentor() == true) out.println("<img src=\"/scapp/assests/senior_mentor.png\" width=20>");%>
                                    </td>
                                    <td><%= mentor.getStudentId() %></td>
                                    <td><%= mentor.getMajor()%></td>
                                    <td><%= mentor.getMenteeCount()%></td>
                                    <td>
                                        <%if(!mentor.getApproved()){%>
                                            <button class="btn btn-sm btn-primary mb-2" type="accept" onClick="return confirm_accept('<%= mentor.getEmail() %>')">Accept</button>
                                         <%} else { out.println("Accepted"); }%>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm btn-primary mb-2" type="delete" onClick="return confirm_del('<%= mentor.getEmail() %>')">Delete</button>
                                    </td>
                                </tr>
                            <% } %>
                        </form>
                    </tbody>
                </table>

                <!-- Paginator -->
                <div class="ml-2 text-center">
                    <%
                        int pageCount = (int)request.getAttribute("pageCount");
                        int perBlockNum = (int)request.getAttribute("perBlockNum");
                        int startPage = (int)request.getAttribute("startPage");
                        int endPage = (int)request.getAttribute("endPage");
                        if(startPage > perBlockNum){
                    %>
                    <a href="mentor_list?page=<%= startPage - perBlockNum %>&search=<%=search%>&sortBy=<%=sortBy%>">[Prev]</a>
                    <%} for (int i = startPage; i <= endPage; i++) {
                            if(i == (int)request.getAttribute("page")){%>
                                [<%=i %>]
                            <%} else {%>
                                <a href="mentor_list?page=<%= i %>&search=<%=search%>&sortBy=<%=sortBy%>">[<%= i %>]</a>
                            <%}
                        }
                        if(endPage < pageCount){%>
                            <a href="mentor_list?page=<%= startPage + perBlockNum %>&search=<%=search%>&sortBy=<%=sortBy%>">[Next]</a>
                    <%}%>
                </div>
        </div>

        <script>
            function confirm_del(deleteId){
                var result = confirm('Do you really want to delete this mentor '+deleteId+'?');
                if(result == true) {
                    document.delete_mentor.elements['mentorEmail'].value = deleteId;
                    document.delete_mentor.elements['work'].value = 'delete';
                } else {
                    return false;
                }
            }

            function confirm_accept(acceptId){
                var result = confirm('Do you want to accept this mentor '+acceptId+' as a mentor?');
                if(result == true) {
                    document.delete_mentor.elements['mentorEmail'].value = acceptId;
                    document.delete_mentor.elements['work'].value = 'accept';
                } else {
                    return false;
                }
            }
        </script>


        <jsp:include page="includes/footer.jsp"/>
    </body>
</html>
