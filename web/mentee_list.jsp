<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Mentee list"/>
    </jsp:include>
    <body>
        <jsp:include page="includes/navbar.jsp"/>

        <!-- Display flash message -->
        <% if (request.getSession().getAttribute("message") != null) { %>
            <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
        <% request.getSession().removeAttribute("message");} %>

        <div class="col text-center">
            <h2>Mentees</h2>
        </div>

    <!-- Display Mentee table  -->
        <div class="container-liquid m-4">
            <div class="m-2 row">
                <div class="col align-items-center">
                    <form class="form-inline" action="mentee_list" method="get">
                        <div class="form-row align-items-center">
                    <!-- Display Search Bar-->
                            <div class="col-auto">
                                <div class="form-group">
                                    <label class="col-form-label text-right">Search for a Mentee by Name</label>
                                </div>
                            </div>
                            <%String search, sortBy;
                            if(request.getAttribute("search") != null){
                                search = (String)request.getAttribute("search");
                            } else search = "";
                            if(request.getAttribute("sortBy")!= null) sortBy = (String)request.getAttribute("sortBy");
                            else sortBy = "";
                            %>
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
            <!-- Display  Sorting Options -->
                    <div class="col" align="right">
                        <form class="form-inline" action="mentee_list" method="get">
                            <div class="form-row alight-items-center">
                                <div class="col-auto" >
                                    <div class="form-group ">
                                        <label class="col-form-label text-right">Sort mentee by</label>
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
                                    <option value="matched_1"  <%if(sortBy.equals("matched_1")) {%>selected<%}%>>Mentor-matched First</option>
                                    <option value="matched_0"  <%if(sortBy.equals("matched_0")) {%>selected<%}%>>Not-yet-matched First</option>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            <!-- Mentee Table -->
            <table style="background-color: white" class="table table-sm table-striped table-bordered table-responsive-sm">
                    <thead class="table-secondary">
                    <tr class="text-center">
                        <th scope="col">Email</th>
                        <th scope="col">Name</th>
                        <th scope="col">Student ID</th>
                        <th scope="col" width="300">Major</th>
                        <th scope="col">Mentor</th>
                        <th scope="col">Delete</th>
                    </tr>
                    </thead>
                    <form name="delete_mentee" action="mentee_update" method="post">
                        <input type="hidden" name="work" value="">
                        <input type="hidden" name="menteeEmail" value="">
                        <input type="hidden" name="returnPage" value="mentee_list">
                        <tbody>
                        <%for (Mentee mentee : (List<Mentee>) request.getAttribute("mentees")) {
                                String mentorName = "";
                                if(mentee.getMentor().getFirstName()==null) mentorName = "<a href=\"dynamic_matches?email="+mentee.getEmail()+"\"><button type=\"button\" class=\"btn btn-sm btn-success\">Get Match</button></a>";
                                else mentorName =  mentee.getMentor().getFirstName() + " " + mentee.getMentor().getLastName();
                        %>
                        <tr class="text-center">
                            <th scope="row"><a href="mentee_info?menteeID=<%=mentee.getEmail()%>"><%= mentee.getEmail() %></a></th>
                            <td><%= mentee.getFirstName() + " " + mentee.getLastName()%></td>
                            <td><%= mentee.getStudentId() %></td>
                            <td><%= mentee.getMajor() %></td>
                            <td><%= mentorName %></td>
                            <td>
                                <button class="btn btn-primary btn-sm mb-2" type="delete" onClick="return confirm_del('<%= mentee.getEmail() %>')">Delete</button>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </form>
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
                    <a href="mentee_list?page=<%= startPage - perBlockNum %>">[Prev]</a>
                    <%}
                        for (int i = startPage; i <= endPage; i++) {
                            if(i == (int)request.getAttribute("page")){
                    %>
                            [<%=i %>]
                    <%
                            } else {
                    %>
                    <a href="mentee_list?page=<%= i %>">[<%= i %>]</a>
                    <%
                            }
                        }

                        if(endPage < pageCount){
                    %>
                    <a href="mentee_list?page=<%= startPage + perBlockNum %>">[Next]</a>
                    <%
                        }

                    %>
                </div>
        </div>
        <script>
            function confirm_del(deleteId){
                var result = confirm('Do you really want to delete this mentee '+deleteId+'?');
                if(result == true) {
                    document.delete_mentee.elements['menteeEmail'].value = deleteId;
                    document.delete_mentee.elements['work'].value = 'delete';
                } else {
                    return false;
                }
            }
        </script>
        <jsp:include page="includes/footer.jsp"/>
    </body>
</html>