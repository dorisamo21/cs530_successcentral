<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Register"/>
</jsp:include>

<body class="text-center">
<jsp:include page="includes/navbar.jsp"/>

<script>
    function validatePassword(){
        var pw = document.devreg.elements['password'].value;
        if(pw != document.devreg.elements['retypepassword'].value) {
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

    <form name="devreg" action="register" method="post" class="form-signin">
        <img class="mb-4"
             src="/scapp/assests/ccsulogo.png"
             alt="ccsulogo"
             width="144"
             height="144"/>
        <h1 class="h1 mb-3 font-weight-normal">User Registration</h1>
        <p>NOTE: Password must contain at least:</p>
        <ul>
            <li>8 characters</li>
            <li>One uppercase letter (A-Z)</li>
            <li>One lowercase letter (a-z)</li>
            <li>One number (0-9)</li>
        </ul>
        <div class="form-group">
            <% if (request.getParameter("email") != null) { %>
            <input type="hidden" name="reg_email" value="<%= request.getParameter("email") %>">
            <% } %>
            <input type="email"
                   class="form-control"
                   name="email"
                   placeholder="CCSU Email"
                   <% if (request.getParameter("email") != null) { %>
                   value="<%= request.getParameter("email") %>"
                   disabled
                   <% } else { %>
                   required=""
                   autofocus=""
                   <% } %>
            />

            <!--<input type="email"
                   class="form-control"
                   name="retypeemail"
                   placeholder="Retype Email"
                   required=""/>-->
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
</div>


<jsp:include page="includes/footer.jsp"/>
</body>
</html>