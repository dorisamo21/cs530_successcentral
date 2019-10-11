
<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="com.ccsu.cs530.successcentral.model.User" %>
<%@ page import="com.ccsu.cs530.successcentral.service.CrudService" %>


<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Mentor update</title>
</head>
<body>
<%
    Mentor mentor = (Mentor) request.getAttribute("mentor");
    CrudService service = new CrudService();

    String work = request.getParameter("work");
    String mentorEmail = request.getParameter("mentorEmail");
    String reg_email = request.getParameter("reg_email");
    String returnPage = request.getParameter("returnPage");

    if(returnPage.equals("mentor_info")) returnPage +="?mentorEmail="+mentorEmail;

    if(work.equals("delete")){
        service.deleteMentor(mentor);
%>
    <script>
        alert('Deleted');
        location.replace('mentor_list');
    </script>
<%
    } else if(work.equals("change")) {
        service.updateMentorLevel(mentor);
%>
<script>
    alert('Mentor level has been changed');
    location.replace('<%=returnPage %>');
</script>
<%

    } else if(work.equals("accept")){
        service.updateMentorApproval(mentor);
%>
<script>
    alert('Mentor <%= mentor.getFirstName()%> <%= mentor.getLastName()%> has been accepted.');
    location.replace('<%=returnPage %>');
</script>
<%
    } else if(work.equals("updatePassword")){
        String password1 = request.getParameter("password1");
//        User user = (User) request.getAttribute("user");
        User user = service.getUser(reg_email);
        user.setPassword(password1);
        service.updateUserPassword(user);
%>
<script>
    alert('<%= user.getFirstName()%>, you are now registered successfully.');
    location.replace('<%=returnPage %>');
</script>
<%
    }
%>

</body>
</html>
