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

    <div class="container-liquid m-4 col-8">
        <!-- Mentee Table -->
        <table class="table table-sm table-striped table-bordered table-responsive-sm whitetable">
            <thead class="table-secondary">
                <tr class="text-center">
                    <th scope="col">Email</th>
                    <th scope="col">Name</th>
                    <th scope="col">Student ID</th>
                    <th scope="col" width="300">Major</th>
                </tr>
            </thead>
            <form name="delete_mentee" action="mentee_update" method="post">
                <input type="hidden" name="work" value="">
                <input type="hidden" name="menteeEmail" value="">
                <input type="hidden" name="returnPage" value="mentee_list">
                <tbody>
                    <% if((int)request.getAttribute("menteeCnt") == 0){%>
                         <tr class="text-center">
                             <td colspan="4" height="30"> No mentee is assigned yet. </td>
                         </tr>
                    <%}
                    for (Mentee mentee : (List<Mentee>) request.getAttribute("mentees")) {%>
                        <tr class="text-center">
                            <th scope="row"><a href="mentee_info?menteeID=<%=mentee.getEmail()%>"><%= mentee.getEmail() %></a></th>
                            <td><%= mentee.getFirstName() + " " + mentee.getLastName()%></td>
                            <td><%= mentee.getStudentId() %></td>
                            <td><%= mentee.getMajor() %></td>
                        </tr>
                    <% } %>
                </tbody>
            </form>
        </table>
    </div>
    <jsp:include page="includes/footer.jsp"/>
</body>
</html>
