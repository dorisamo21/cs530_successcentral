<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Forgot Password"/>
</jsp:include>

<body class="text-center">
<jsp:include page="includes/navbar.jsp"/>

<div class="container">
    <!-- Display flash message -->
    <% if (request.getSession().getAttribute("message") != null) { %>
    <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
    <% request.getSession().removeAttribute("message");} %>

    <form action="forgot_password" method="post" class="form-signin">
        <img class="mb-4"
             src="/scapp/assests/ccsulogo.png"
             alt="ccsulogo"
             width="144"
             height="144"/>
        <div class="mb-3">Type in your CCSU email, and we will email you a link to reset your password.</div>
        <div class="form-group">
            <input type="email"
                   class="form-control"
                   name="email"
                   placeholder="CCSU Email"
                   required=""
                   autofocus=""/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Request New Password
        </button>
        <br/>
        <div class="mb-4">
            <a href="login">Back to login</a>
        </div>
    </form>
</div>


<jsp:include page="includes/footer.jsp"/>
</body>
</html>
