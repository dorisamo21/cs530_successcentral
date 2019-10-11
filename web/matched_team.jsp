<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="com.ccsu.cs530.successcentral.service.CrudService" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Mentor List"/>
    </jsp:include>
    <script>
        function toggle(source) {
            var checkboxes = document.querySelectorAll('input[type="checkbox"]');
            for (var i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i] != source)
                    checkboxes[i].checked = source.checked;
            }
        }

        $('#submit').click(function() {
            var result = confirm('Do you really want to send email to mentor and mentees?');
            if (result == true) {
                var checkedValues = $('.invid:checked').map(function () {
                    return this.value;
                }).get();

                $.ajax({
                    url: "matched_email",
                    type: "post",
                    data: {invid: checkedValues},
                    success: function (data) {
                        $('#response').html(data);
                    }
                });
            }
        });
    </script>
    <body>
        <jsp:include page="includes/navbar.jsp"/>

        <!-- Display flash message -->
        <% if (request.getSession().getAttribute("message") != null) { %>
            <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
        <% request.getSession().removeAttribute("message");} %>

        <div class="col text-center">
            <h2>Mentors with Matched Mentees </h2>
        </div>

        <div class="container-liquid m-4" >
                <!-- Heading -->
                <!-- Mentor Table -->
                <table class="table table-sm table-striped table-bordered table-responsive-sm whitetable">
                    <thead class="table-secondary">
                        <tr width=800>
                            <th  class="text-left" scope="col">Mentor</th>
                            <th  class="text-left" scope="col">Mentees</th>
                            <th  class="text-center">
                                <input type="checkbox" onclick="toggle(this);" />
                            </th>
                        </tr>
                    </thead>
                    <tbody>
                    <form name="delete_mentor" action="matched_email.jsp" method="get">
                        <input type="hidden" name="pageNum" value="<%=request.getAttribute("page")%>">
                        <%CrudService crud = new CrudService();
                        for (Mentor mentor : (List<Mentor>) request.getAttribute("mentors")) {
                            List<Mentee> myMentee = crud.getMyMentees(mentor.getEmail());%>
                            <tr height=80 class="text-left">
                                <td scope="row"><a href="mentor_info?mentorEmail=<%=mentor.getEmail()%>"><b><%= mentor.getFirstName()%>&nbsp;<%=mentor.getLastName()%></b></a>&nbsp;(<%= mentor.getEmail() %>)</td>
                                <td>
                                    <div>
                                        <% for(int i=0;i<mentor.getMenteeCount();i++){ %>
                                            <a href="mentee_info?menteeID=<%=myMentee.get(i).getEmail()%>"><b><%= myMentee.get(i).getFirstName()%>&nbsp;<%= myMentee.get(i).getLastName()%></b></a>&nbsp;(<%= myMentee.get(i).getEmail()%>)<br>
                                        <%}%>
                                    </div>
                                </td>
                                <td align="center">
                                    <input type="checkbox" name="invid" value="<%=mentor.getEmail()%>">
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
                <div style="text-align:right">
                    <button id="submit" type="submit" class="btn btn-sm btn-primary"> Send Introduction Email </button>
                </div>
            </form>
                <!-- Paginator -->
                <div class="ml-2 text-center">
                    <%
                        int pageCount = (int)request.getAttribute("pageCount");
                        int perBlock = (int)request.getAttribute("perBlock");
                        int startPage = (int)request.getAttribute("startPage");
                        int endPage = (int)request.getAttribute("endPage");
                        if(startPage > perBlock){
                    %>
                    <a href="matched_team?page=<%= startPage - perBlock %>">[Prev]</a>
                    <%}for (int i = startPage; i <= endPage; i++) {
                        if(i == (int)request.getAttribute("page")){%>
                            [<%=i %>]
                        <%} else {%>
                            <a href="matched_team?page=<%= i %>">[<%= i %>]</a>
                        <%}
                        }
                    if(endPage < pageCount){ %>
                        <a href="matched_team?page=<%= startPage + perBlock %>">[Next]</a>
                    <%}%>
                </div>
        </div>


        <jsp:include page="includes/footer.jsp"/>
    </body>
</html>
