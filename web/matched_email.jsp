
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
    CrudService crud = new CrudService();
    String pageNum = request.getParameter("pageNum");
    String[] ids=request.getParameterValues("invid");
    // this will get array of values of all checked checkboxes
    for(String id:ids){
        crud.noticeMatched(id);
    }

%>
<script>
    alert('Matching notice has successfully sent');
    location.replace('matched_team?page=<%=pageNum%>');
</script>

</body>
</html>
