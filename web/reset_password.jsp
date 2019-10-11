<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Reset Password"/>
</jsp:include>

<body class="text-center">
<jsp:include page="includes/navbar.jsp"/>

<div class="container">
    <!-- Display flash message -->
    <% if (request.getSession().getAttribute("message") != null) { %>
    <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
    <% request.getSession().removeAttribute("message");} %>

    <form action="reset_password?token=<%= request.getAttribute("token") %>" method="post" class="form-signin">
        <img class="mb-4"
             src="/scapp/assests/ccsulogo.png"
             alt="ccsulogo"
             width="144"
             height="144"/>
        <div class="mb-4">Change Password for <%= request.getAttribute("email") %></div>
        <div class="form-group">
            <input type="password"
                   class="form-control"
                   name="password"
                   placeholder="Password"
                   required=""
                   autofocus=""/>
            <input type="password"
                   class="form-control"
                   name="retypepassword"
                   placeholder="Retype Password"
                   required=""
                   autofocus=""/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Submit
        </button>
    </form>
</div>
<jsp:include page="includes/footer.jsp"/>
</body>
</html>

