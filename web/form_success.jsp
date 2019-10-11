<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Mentee Form"/>
</jsp:include>
<body>
<jsp:include page="includes/navbar.jsp"/>

<div class="container">

    <h1>Form Submitted Successfully!</h1>
    <% if (request.getAttribute("mentor").equals("true")) { %>
        <p>You application has been received to be a mentor.</p>
        <p>We will send an email to you at <b><%= request.getAttribute("email") %></b> if your application is approved.</p>
        <p>Thank you for your interest in Success Central!</p>
    <% } else { %>
        <p>You application has been received to be a mentee.</p>
        <p>We will send an email to you at <b><%= request.getAttribute("email") %></b> when you have been matched with a mentor.</p>
        <p>Thank you for your interest in Success Central!</p>
    <% } %>

</div>
</body>
</html>
