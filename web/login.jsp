<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Login"/>
</jsp:include>

<body class="text-center">
<jsp:include page="includes/navbar.jsp"/>

<div class="container">
    <!-- Display flash message -->
    <% if (request.getSession().getAttribute("message") != null) { %>
    <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
    <% request.getSession().removeAttribute("message");} %>

<form action="login" method="post" class="form-signin">
    <img class="mb-4"
         src="/scapp/assests/ccsulogo.png"
         alt="ccsulogo"
         width="150"/>
    <div class="mb-3">
        <img src="/scapp/assests/sc_logo-type-10x3.png" alt="sclogo" width="200">
    </div>
    <h3 class="h4 mb-3 font-weight-normal">Please Sign In</h3>
    <label for="inputEmail" class="sr-only">Email Address</label>
    <input type="email"
           id="inputEmail"
           class="form-control"
           name="email"
           placeholder="Email Address"
           required=""
           autofocus=""/>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password"
           id="inputPassword"
           class="form-control"
           name="password"
           placeholder="Password"
           required=""/>
    <button class="btn btn-lg btn-primary btn-block" type="submit">
        Sign In
    </button>
    <div class="mt-4">
        <a href="register">Register Here</a>
    </div>
    <div class="mt-4">
       <a href="forgot_password">Forgot Password?</a>
    </div>
</form>
</div>


<jsp:include page="includes/footer.jsp"/>
</body>
</html>