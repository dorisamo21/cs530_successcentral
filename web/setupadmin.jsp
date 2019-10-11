<%@ page import="com.ccsu.cs530.successcentral.service.CrudService" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Admin Setup"/>
</jsp:include>

<body class="text-center">
<jsp:include page="includes/navbar.jsp"/>

<script>
    function validatePassword(){
        var pw = document.adminreg.elements['password'].value;
        if(pw != document.adminreg.elements['retypepassword'].value) {
            alert('Password must be the same in both fields.');
            return false;
        }
        if (pw.length < 8){
            alert('Password must contain at least 8 characters.');
            return false;
        }
        var re = new RegExp("(?=.*[A-Z])");
        if(!re.test(pw)) {
            alert('Password must contain at least one uppercase letter.');
            return false;
        }
        re = new RegExp("(?=.*[a-z])");
        if(!re.test(pw)){
            alert('Password must contain at least one lowercase letter.');
            return false;
        }
        re = new RegExp("(?=.*[0-9])");
        if(!re.test(pw)){
            alert('Password must contain at least one number.');
            return false;
        }
        return true;

    }
</script>

<div class="container">
    <!-- Display flash message -->
    <% if (request.getSession().getAttribute("message") != null) { %>
    <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
    <% request.getSession().removeAttribute("message");} %>

    <% int adminCount;
        if ((adminCount = new CrudService().getAdminCount()) == 0) { %>
    <form name="adminreg" action="setupadmin" method="post" class="form-signin">
        <img class="mb-4"
             src="/scapp/assests/ccsulogo.png"
             alt="ccsulogo"
             width="144"
             height="144"/>
        <h1 class="h1 mb-3 font-weight-normal">Admin Registration</h1>
        <p>NOTE: Password must contain at least:</p>
        <ul>
            <li>8 characters</li>
            <li>One uppercase letter (A-Z)</li>
            <li>One lowercase letter (a-z)</li>
            <li>One number (0-9)</li>
        </ul>
        <div class="form-group">
            <input type="email"
                   class="form-control"
                   name="email"
                   placeholder="CCSU Email"
                   required=""
                   autofocus=""/>
        </div>
        <div class="form-group">
            <input type="password"
                   class="form-control"
                   name="password"
                   placeholder="Password"
                   required=""/>

            <input type="password"
                   class="form-control"
                   name="retypepassword"
                   placeholder="Retype Password"
                   required=""/>
        </div>
        <button class="btn btn-lg btn-primary btn-block" type="submit" onclick="return validatePassword()">
            Register
        </button>
    </form>
    <% } else if (adminCount > 0) { %>
    <div>Admin already set up. Please log in via admin account.</div>
    <% } else { %>
    <div>Error connecting to database. Please check settings or try again later.</div>
    <% } %>
</div>


<jsp:include page="includes/footer.jsp"/>
</body>
</html>
