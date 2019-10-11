<%@ page import="com.ccsu.cs530.successcentral.model.Mentor" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Match" %>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Mentor Info"/>
    </jsp:include>
    <body>
        <jsp:include page="includes/navbar.jsp"/>
        <%
            Mentor mentor = (Mentor) request.getAttribute("mentor");
            ArrayList<Match> mentorMatches = (ArrayList<Match>)request.getAttribute("mentorMatches");
            String mentorEmail = request.getParameter("mentorEmail");

            String mentor_title;
            String script_question;
            if(mentor.getSeniorMentor() == true) {
                mentor_title = "<font color='#3399cc'>Senior Mentor</font>";
                script_question = "Do you want to change this senior mentor to mentor?";
            }
            else {
                mentor_title = "Mentor";
                script_question = "Do you want to change this mentor to senior mentor?";
            }
        %>

        <div class="container">
            <div class="mt-3 userdiv" >
                <div class="col-12">
                    <div class="row">
                    <%--Left part of user mentor page--%>
                        <div class="col-4 pt-3">
                            <h3 class="text-center"><%= mentor_title%> </h3>
                        <%--User icons based on gender, add photo details here--%>
                            <div class="text-center align-items-center">
                                <div>
                                    <%if (mentor.getGender().equals("Female")){%>
                                    <img src="/scapp/assests/icon-female.png"
                                         alt="icon-female"
                                         width="150"/>
                                    <%} else if (mentor.getGender().equals("Male")){ %>
                                    <img src="/scapp/assests/icon-male.png"
                                         alt="icon-male"
                                         width="150"/>
                                    <%} else { %>
                                    <img src="/scapp/assests/icon-other.png"
                                         alt="icon-other"
                                         width="150"/>
                                    <%}%>
                                    <br>
                                <%--Popover for Add Photo--%>
                                    <button type="button" class="btn btn-secondary addPhotoButton" data-container="body" data-toggle="popover" data-placement="right" data-content="Feature to be added">
                                        Add photo
                                    </button>
                                </div>
                                <div class="mt-3">
                                    <h3><%=mentor.getFirstName()%>&nbsp;<%=mentor.getLastName()%> </h3>
                                    <b>Status: </b>
                                    <% if (mentor.getSeniorMentor()){%>
                                        <span class="badge badge-warning">Senior Mentor</span>
                                    <%} else if (mentor.getApproved()) { %>
                                        <span class="badge badge-success">Approved Mentor</span>
                                    <%} else {%>
                                        <span class="badge badge-danger">Not Approved</span>
                                    <%}%>
                                    <br>
                                    <br>
                                    <b>Year: </b><%= mentor.getYear().equals("3") ? "Junior" : "Senior"%>
                                    <br>
                                    <b>Email: </b> <%=mentor.getEmail()%>
                                    <br>
                                    <b>Student ID: </b> <%=mentor.getStudentId()%>
                                    <div class="mt-4">
                                <%--If mentor is approved, display mentors--%>
                                        <% if (mentor.getApproved()){ %>
                                            <h5>Mentee List: </h5>
                                            <% if (mentorMatches.isEmpty()){ %>
                                                <span class="badge badge-danger">No Matches</span>
                                            <%} else {%>
                                                <% for (Match match : mentorMatches) { %>
                                                    <a href="mentee_info?menteeID=<%= match.getMentee().getEmail()%>">
                                                        <%= match.getMentee().getFirstName()%>&nbsp;<%= match.getMentee().getLastName() %>
                                                    </a>
                                                    <span style="font-size: .8em">(<%= match.getPercentMatch()%> match)</span>
                                                    <br>
                                                <% }%>
                                            <% }%>
                                        <%} else {%>
                                        <form name="accept_mentor" action="mentor_update" method="post">
                                            <input type="hidden" name="work" value="">
                                            <input type="hidden" name="mentorEmail" value="">
                                            <input type="hidden" name="returnPage" value="mentor_list">
                                            <button class="btn btn-sm btn-success" type="accept" onClick="return confirm_accept('<%= mentor.getEmail() %>')">Accept Mentor</button>
                                        </form>
                                        <%}%>
                                    </div>
                                </div>
                            <%--Display buttons--%>
                                <div class="my-3">
                                    <form action="mentor_update" name="update_mentor" method="post">
                                        <div>
                                            <input type="hidden" name="mentorEmail" value="<%= mentorEmail %>">
                                            <input type="hidden" id="todo" name="work" value="">
                                            <input type="hidden" name="returnPage" value="mentor_info">
                                            <button type="submit" class="btn btn-sm btn-danger smbutton" name="delete" value="" onClick="return confirm_del()">Delete</button>
                                            <%if((!mentor.getSeniorMentor()) && mentor.getApproved()){ %>
                                            <input type="hidden" name="new_is_senior_mentor" value="1">
                                            <button type="submit" class="btn btn-sm btn-warning smbutton" name="changeStatus" value="toSenior" onClick="return confirm_chg()">Set As SeniorMentor</button>
                                            <%} else if ((mentor.getSeniorMentor()) && mentor.getApproved()) {%>
                                            <input type="hidden" name="new_is_senior_mentor" value="0">
                                            <button type="submit" class="btn btn-sm btn-primary smbutton" name="changeStatus" value="toJunior" onClick="return confirm_chg()">Set as (Normal) Mentor</button>
                                            <%}%>
                                            <button type="button" type="button" class="btn btn-sm btn-primary smbutton" onClick="location.href='mentor_list'">To the List</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <%--Display right secotion of page--%>
                        <div class="col-8 mt-3">
                            <table class="table table-sm table-borderless">
                                <tbody>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row"> <h5> Matching Criteria </h5></th>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Gender</th>
                                    <td><%= mentor.getGender()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Ethnicity</th>
                                    <td><% for (String race : mentor.getRaceArray()){%>
                                        <%=race%> <br/>
                                        <%}%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Major</th>
                                    <td><%= mentor.getMajor()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Hometown</th>
                                    <td><%= mentor.getHometown()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Other Languages</th>
                                    <td><% for (String lang : mentor.getLanguageArray()){%>
                                        <%=lang%> <br/>
                                        <%}%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Hobbies</th>
                                    <td><% for (String hobby : mentor.getHobbiesArray()){%>
                                        <%=hobby%> <br/>
                                        <%}%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">Did at least one of your legal guardians graduate from a four year college?</th>
                                    <td><%= (mentor.getParentEducation()) ? "Yes" : "No, First Generation College Student"%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row"> <h4> </h4></th>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row"> <h4> Questionnaire </h4></th>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">Why would you make a good mentor to first years and sophomores?</th>
                                    <td><%= mentor.getAttitudeForDifference()%></td>
                                </tr>
                                <tr class="d-flex ">
                                    <th class="col-5 smtext" scope="row">What are the requirements to being a good mentor to someone who may have a different background than you?</th>
                                    <td><%= mentor.getMentorReq()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">Describe a time when you encountered a challenge in college, and how you dealt with it.</th>
                                    <td><%= mentor.getChallenge()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">What does it take for a first generation college student to be successful?</th>
                                    <td><%= mentor.getForSuccessfulFirstYear()%></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script>
            function confirm_del(){
                var result = confirm('Do you really want to delete this mentor?');
                if(result == true) {
                    document.update_mentor.elements['work'].value = 'delete';
                } else {
                    return false;
                }
            }

            function confirm_chg(){
                var result = confirm('<%= script_question%>');
                if(result == true){
                    document.update_mentor.elements['work'].value = 'change';
                } else {
                    return false;
                }
            }

            function toList(){
                //        location.replace('mentor_list');
                location.href='mentor_list';
            }

            function confirm_accept(acceptId){
                var result = confirm('Do you want to accept this mentor '+acceptId+' as a mentor?');
                if(result == true) {
                    document.accept_mentor.elements['mentorEmail'].value = acceptId;
                    document.accept_mentor.elements['work'].value = 'accept';
                } else {
                    return false;
                }
            }
            $(function () {
                $('[data-toggle="popover"]').popover()
            })
        </script>

        <jsp:include page="includes/footer.jsp"/>
    </body>
</html>