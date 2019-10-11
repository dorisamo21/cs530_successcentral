<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Match" %>

<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Mentee Info"/>
    </jsp:include>
    <body>
        <jsp:include page="includes/navbar.jsp"/>

    <!-- Pulling mentee and their match from servlet -->
        <%Mentee mentee = (Mentee) request.getAttribute("mentee");%>
        <%Match match = (Match) request.getAttribute("match");%>

        <div class="container">
            <div class="mt-3 userdiv">
                <div class="col-12">
                    <div class="row">
                        <div class="col-4 pt-3" >
                    <%--Left part of user mentee page--%>
                            <h3 class="text-center">Mentee</h3>
                            <div class="text-center align-items-center">
                            <%--User icons based on gender, add photo details here--%>
                                <div>
                                    <%if (mentee.getGender().equals("Female")){%>
                                    <img src="/scapp/assests/icon-female.png"
                                         alt="icon-female"
                                         width="150"/>
                                    <%} else if (mentee.getGender().equals("Male")){ %>
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
                                    <h3><%=mentee.getFirstName()%>&nbsp;<%=mentee.getLastName()%> </h3>
                                    <b>Mentor Status: </b>
                                    <% if (mentee.getMentor()!= null){%>
                                        <span class="badge badge-success">Active</span>
                                    <%} else { %>
                                        <span class="badge badge-danger">Inactive</span>
                                    <%}%>
                                    <br>
                                    <br>
                                    <b>Year: </b><%= mentee.getYear().equals("1") ? "First Year" : "Sophomore"%>
                                    <br>
                                    <b>Email: </b> <%=mentee.getEmail()%>
                                    <br>
                                    <b>Student ID: </b> <%=mentee.getStudentId()%>
                                    <br>
                                <%--If mentee has a mentor, display the mentors name and the percentage match--%>
                                    <%if(mentee.getMentor() != null ){%>
                                        <div class="mt-2">
                                            <label><b>Mentor :</b></label>
                                            <a href="mentor_info?mentorEmail=<%=mentee.getMentor().getEmail()%>"><%=mentee.getMentor().getFirstName()%>&nbsp;<%=mentee.getMentor().getLastName()%></a>
                                            <span style="font-size: .8em">(<%= match.getPercentMatch()%> match)</span>

                                            <br>
                                        </div>
                                    <%}%>
                                <%--Display buttons--%>
                                    <div class="my-3 ">
                                        <form action="mentee_update" name="update_mentee" method="post">
                                            <input type="hidden" name="menteeEmail" value="<%= mentee.getEmail() %>">
                                            <input type="hidden" id="todo" name="work" value="">
                                            <input type="hidden" name="returnPage" value="mentor_info">
                                            <%if((mentee.getMentor() != null) && (request.getSession().getAttribute("isAdmin").equals("true"))){%>
                                                <a href="dynamic_matches?email=<%=mentee.getEmail()%>"><button type="button" class="btn btn-sm btn-success smbutton">Change Mentor</button></a>
                                                <button type="submit" class="btn btn-sm btn-danger smbutton" name="delete" value="" onClick="return confirm_del()">Delete Mentee</button>
                                                <button type="button" type="button" class="btn btn-sm btn-primary smbutton" onClick="location.href='mentee_list'">To the List</button>

                                            <%} else if ((mentee.getMentor() == null) && (request.getSession().getAttribute("isAdmin").equals("true"))){ %>
                                                <a href="dynamic_matches?email=<%=mentee.getEmail()%>"><button type="button" class="btn btn-sm btn-success smbutton">Match Mentee</button></a>
                                                <button type="submit" class="btn btn-sm btn-danger smbutton" name="delete" value="" onClick="return confirm_del()">Delete Mentee</button>
                                                <button type="button" type="button" class="btn btn-sm btn-primary smbutton" onClick="location.href='mentee_list'">To the List</button>

                                            <% } else if (mentee.getMentor() != null) {%>
                                                <button type="button" type="button" class="btn btn-sm btn-primary smbutton" onClick="location.href='my_mentee_list'">To the List</button>
                                            <%}%>
                                        </form>
                                    </div>
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
                                    <td><%= mentee.getGender()%>
                                        <% if(mentee.getMentor()!=null && match.getGenderMatch()) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Ethnicity</th>
                                    <td><% for (String race : mentee.getRaceArray()){%>
                                        <%=race%>
                                        <% if(mentee.getMentor()!=null && match.checkRace(race, match.getMentor())) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                         <br/>
                                        <%}%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Major</th>
                                    <td><%= mentee.getMajor()%>
                                        <% if(mentee.getMentor()!=null && match.getMajorMatch()) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Hometown</th>
                                    <td><%= mentee.getHometown()%>
                                        <% if(mentee.getMentor()!=null && match.getHometownMatch()) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Other Languages</th>
                                    <td><% for (String lang : mentee.getLanguageArray()){%>
                                        <%=lang%>
                                        <% if(mentee.getMentor()!=null && match.checkLanguage(lang, match.getMentor())) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <br/>
                                        <%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row">Hobbies</th>
                                    <td><% for (String hobby : mentee.getHobbiesArray()){%>
                                            <%=hobby%>
                                            <% if(mentee.getMentor()!=null && match.checkHobby(hobby, match.getMentor())) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <br/>
                                        <%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">Did at least one of your legal guardians graduate from a four year college?</th>
                                    <td><%= (mentee.getParentEducation()) ? "Yes" : "No, First Generation College Student"%>
                                        <% if(mentee.getMentor()!=null && match.getParentalEdMatch()) {%> <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                    </td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row"> <h4> </h4></th>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5" scope="row"> <h4> Questionnaire </h4></th>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">What are you most looking forward to experiencing in college?</th>
                                    <td><%= mentee.getLookingForward()%></td>
                                </tr>
                                <tr class="d-flex">
                                    <th class="col-5 smtext" scope="row">Why do you want a peer mentor?</th>
                                    <td><%= mentee.getWhyMentor()%></td>
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
                var result = confirm('Do you really want to delete this mentee?');
                if(result == true) {
                    document.update_mentee.elements['work'].value = 'delete';
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