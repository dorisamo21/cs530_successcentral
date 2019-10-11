
<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ccsu.cs530.successcentral.service.CrudService" %>

<%@ page contentType="text/html; charset=UTF-8"%>

<html>
<head>
    <title>Mentee update</title>
</head>
<body>
<%
    Mentee mentee = (Mentee) request.getAttribute("mentee");
    CrudService service = new CrudService();

    String work = request.getParameter("work");
    String menteeEmail = request.getParameter("menteeEmail");
    String returnPage = request.getParameter("returnPage");

    if(returnPage.equals("mentee_info")) returnPage +="?mentorEmail="+menteeEmail;

    if(work.equals("delete")){
        service.deleteMentee(menteeEmail);
%>
    <script>
        alert('Deleted');
        location.replace('mentee_list');
    </script>
<%
    }
%>
</body>
</html>
