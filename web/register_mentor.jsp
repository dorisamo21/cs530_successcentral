<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="com.ccsu.cs530.successcentral.model.User" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Register Mentor Form"/>
</jsp:include>
<body>
<jsp:include page="includes/navbar.jsp"/>
    <%String mentorEmail = request.getParameter("mentorEmail");
    User user = (User)request.getAttribute("user");
    if(user.getEmail() == "" || user.getEmail()== null){%>
        <script>
            alert('Wrong approach');
            location.replace('home');
        </script>
    <%}%>



    <div align="center" class="h4 mb-3 font-weight-normal">
        <table border=0>
            <tr>
                <td align="center">
                <img class="mb-4"
                     src="/scapp/assests/ccsulogo.png"
                     alt="ccsulogo"
                     width="144"
                     height="144"/>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <img src="/scapp/assests/sc_logo-type-10x3.png" alt="sclogo" width="200">
                </td>
            </tr>
            <tr>
                <td align="center" style="font-size:16px">
                    <br>
                    <b>Welcome to the mentor registration page! </b>
                    <br>
                    <p>Please confirm your email is correct and select a password to be registered as a mentor.</p>
                    <p>NOTE: Password must contain at least:</p>
                    <ul>
                        <li>8 characters</li>
                        <li>One uppercase letter (A-Z)</li>
                        <li>One lowercase letter (a-z)</li>
                        <li>One number (0-9)</li>
                    </ul>
                </td>
            </tr>
            <form name="mentor_reg" action="mentor_update" method="post">
                <input type="hidden" name="work" value="updatePassword">
                <input type="hidden" name="returnPage" value="home">
                <input type="hidden" name="user" value="<%=user%>">
                <input type="hidden" name="reg_email" value="<%= mentorEmail%>">

                <tr><td height="10">&nbsp;</td></tr>
                <tr><td align="center">

                <table width="300">
                    <tr height="30">
                        <td>
                            <label for="inputEmail" class="sr-only">Email Address</label>
                            <input type="email"
                                   id="inputEmail"
                                   name="email"
                                   class="form-control"
                                   placeholder="Email Address"
                                   value="<%=mentorEmail%>"
                                   disabled/>
                        </td>
                    </tr>
                    <tr height="30">
                        <td>

                            <label for="inputPassword" class="sr-only">Type Password</label>
                            <input type="password"
                                   id="inputPassword"
                                   class="form-control"
                                   placeholder="Type Password"
                                   name="password1"
                                   required="" autofocus=""/>
                        </td>
                    </tr>
                    <tr height="30">
                        <td>
                            <label for="inputPassword2" class="sr-only">Retype Password</label>
                            <input type="password"
                                   id="inputPassword2"
                                   class="form-control"
                                   placeholder="Retype Password"
                                   name="password2"
                                   required=""/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                        <button class="btn btn-lg btn-primary btn-block" type="submit" onClick="return validatePassword()">
                            Register
                        </button>
                        </td>
                    </tr>
                </table>
            </form>
        </table>
    </div>


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
<jsp:include page="includes/footer.jsp"/>
</div>
</body>
</html>