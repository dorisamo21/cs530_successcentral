<%@ page import="com.ccsu.cs530.successcentral.model.Mentee" %>
<%@ page import="java.util.List" %>
<%@ page import="com.ccsu.cs530.successcentral.model.Match" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="includes/header.jsp">
        <jsp:param name="title" value="Dynamic Matching"/>
    </jsp:include>

    <body>
        <jsp:include page="includes/navbar.jsp"/>

        <!-- List of all matches for a specific mentee from servlet-->
        <%
            Mentee mentee = (Mentee) request.getAttribute("mentee");
            List<Match> matches = (List<Match>) request.getAttribute("matches");
            List<Match> matchesAll = (List<Match>) request.getAttribute("matchesAll");
        %>

        <!-- Display flash message -->
        <% if (request.getSession().getAttribute("message") != null) { %>
        <div class="text-center"><%= request.getSession().getAttribute("message") %></div>
        <% request.getSession().removeAttribute("message");} %>

        <!-- Page header -->
        <% if (mentee != null) {%>
            <div class="col text-center">
                <h2>Matching Page</h2>
            </div>
            <% if (matches.size() == 0){%>
                <div class="col text-center redtext">
                    <h5>No Approved Mentors Yet</h5>
                </div>
            <%}%>
            <% if (matches.size() > 0 && matches.size() < 5 ){%>
                <div class="col text-center redtext">
                    <h5>Approve More Mentors For More Matching Options</h5>
                </div>
            <%}%>
        <%} else {%>
            <div class="col text-center">
                <h2 class="text-center">Choose a Mentee to Match</h2>
            </div>
        <%}%>


    <div class="container-liquid mx-4">
            <!-- Dropdown list of all orphans -->
            <div class="row align-items-center mx-1">
                <% if (request.getParameter("alternate_view") != null) { %>
                    <!-- Option 1 - original dropdown of unmatched mentees-->
                    <div class="col m-2">
                        <form class="form-inline" action="dynamic_matches" method="get">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="form-group">
                                        <label class="col-form-label text-right">Select a Mentee</label>
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <select class="form-control" name="email">
                                        <% for (Mentee menteeOrphans : (List<Mentee>) request.getAttribute("orphans")) { %>
                                        <option value="<%= menteeOrphans.getEmail() %>" <%= mentee != null && menteeOrphans.getEmail().equals(mentee.getEmail()) ? "selected" : ""%>>
                                            <%= menteeOrphans.getLastName() + ", " + menteeOrphans.getFirstName() %>
                                        </option>
                                        <% } %>
                                    </select>
                                    <button type="submit" style="padding-top:12px" class="btn btn-sm btn-primary"> Select </button>
                                </div>
                            </div>
                        </form>
                    </div>
                <% } else { %>
                    <!-- Option 2 - type ahead search bar (default option)-->
                    <div class="col m-2 align-items-center">
                        <form class="form-inline" action="dynamic_matches" method="get">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="form-group">
                                        <label class="col-form-label text-right">Search for a Mentee</label>
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <input class="typeahead tt-query"
                                           type="text"
                                           autocomplete="off"
                                           spellcheck="false"
                                           name="email"
                                           value="<%= mentee != null ? mentee.getEmail() : ""%>"
                                    />
                                    <button type="submit" class="btn btn-sm btn-primary"> Select </button>
                                </div>
                            </div>
                        </form>
                    </div>
                <% } %>

                <!-- When mentee is selected show a dropdown list of all top mentors to choose from -->
                <% if (mentee != null) {%>
                <div>
                    <div class="col" align="right">
                        <form class="form-inline" action="dynamic_matches" method="post">
                            <div class="form-row align-items-center">
                                <div class="col-auto">
                                    <div class="form-group">
                                        <label class="col-form-label text-right">Mentors</label>
                                    </div>
                                </div>
                            </div>
                            <div class="col-auto">
                                <select class="form-control" name="mentorEmail">
                                    <% for (Match match : matchesAll) {
                                        if (match.getMentor().getApproved()) { %>
                                    <option value=<%=match.getMentor().getEmail()%>>
                                        <%= match.getMentor().getLastName() + ", " + match.getMentor().getFirstName() + " - " + match.getPercentMatch() %>
                                    </option>
                                            <% } %>
                                    <% } %>
                                    <input type="hidden" name="menteeEmail" value=<%=mentee.getEmail()%>>
                                </select>
                                <Button type="submit" class="btn btn-sm btn-primary"> Select </Button>
                            </div>
                        </form>
                    </div>
                <% } %>
                </div>
            </div>

        <!-- If no mentee is selected, display a table of all orphan mentees -->
            <% if (mentee == null) {%>
                <div class="col-12 mx-2">
                    <table class="table table-sm table-striped table-bordered table-responsive-sm whitetable">
                        <thead class="table-secondary">
                            <tr class="text-center">
                                <th scope="col">Name</th>
                                <th scope="col">Email</th>
                                <th scope="col">Student ID</th>
                                <th scope="col">Year</th>
                                <th scope="col">Major</th>
                                <th scope="col">Select Mentee</th>

                            </tr>
                        </thead>
                        <tbody>
                        <% for (Mentee menteeOrphans : (List<Mentee>) request.getAttribute("orphans")) {%>
                        <form action="dynamic_matches" method="get">
                            <input type="hidden" name="email" value=<%=menteeOrphans.getEmail()%>>
                            <tr class="text-center">
                                <td><%= menteeOrphans.getFirstName() + " " + menteeOrphans.getLastName()%></td>
                                <td scope="row"><a href="mentee_info?menteeID=<%=menteeOrphans.getEmail()%>"><%= menteeOrphans.getEmail() %></a></td>
                                <td><%= menteeOrphans.getStudentId() %></td>
                                <td><%= menteeOrphans.getYear().equals("1") ? "First Year" : "Sophomore" %></td>
                                <td><%= menteeOrphans.getMajor()%></td>
                                <td> <Button type="submit" class="btn btn-sm btn-primary"> Select </Button> </td>
                            </tr>
                        </form>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            <%}%>

            <!-- If a mentee is choosen, display the matching page -->
            <% if (mentee != null) {%>
            <div class="container-liquid">
                <div>
                    <table class="table table-sm table-striped table-responsive-sm whitetable" >

                        <!-- Display Table Header -->
                        <thead class="table-secondary">
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row"></th>
                                <th class="table-names col-1"> <a class="matching-names" href="mentee_info?menteeID=<%=mentee.getEmail()%>"><%= mentee.getFirstName() + " " + mentee.getLastName()%></a></th>
                                <% for (Match match: matches) { %>
                                    <th class= "table-names col-2"><a class="matching-names" href="mentor_info?mentorEmail=<%=match.getMentor().getEmail()%>"> <%= match.getMentor().getFirstName() + " " + match.getMentor().getLastName()%> </td>
                                <%}%>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Display Mentee Count -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Mentee Count</th>
                                <th class="table-mentor col-1"> </th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2"><%= match.getMentor().getMenteeCount() %></td>
                                <%}%>
                            </tr>

                            <!-- Display Match Percentage -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Match %</th>
                                <th class="table-mentor col-1"> </th>
                                <% for (Match match : matches) { %>
                                    <th class="col-2"> <%= match.getPercentMatch() %> </th>
                                <% } %>
                            </tr>

                            <!-- Display Major -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Major</th>
                                <th class="table-mentor col-1"> <%= mentee.getMajor()%></th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2">
                                        <%if (match.getMajorMatch()) {%><span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <%= match.getMentor().getMajor() %></td> <%}%>
                            </tr>

                            <!-- Display Hometown -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Home Town</th>
                                <th class="table-mentor col-1"> <%= mentee.getHometown()%></th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2"><%if (match.getHometownMatch()) {%><span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <%= match.getMentor().getHometown() %></td> <%}%>
                            </tr>

                            <!-- Display Gender -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Gender</th>
                                <th class="table-mentor col-1"> <%= mentee.getGender()%></th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2"><%if (match.getGenderMatch()) {%><span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <%= match.getMentor().getGender() %></td> <%}%>
                            </tr>

                            <!-- Display Languages -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Languages</th>
                                <th class="table-mentor col-1">
                                    <% if (mentee.getLanguage() == null || mentee.getLanguage().equals("")) { %>
                                        English Only
                                    <%} else { %>
                                        <%for (String languageMentee : mentee.getLanguageArray()) { %>
                                            <%= languageMentee %> <br />
                                        <%}%>
                                    <%}%>
                                </th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2">
                                        <% for (String languageMentor : match.getMentor().getLanguageArray()) { %>
                                            <% if (match.checkLanguage(languageMentor, match.getMentee())) {%>
                                                <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span>
                                            <%}%>
                                            <%= (languageMentor.equals("")) ? "English Only" : languageMentor %><br />
                                        <%}%>
                                    </td>
                                <%}%>
                            </tr>

                            <!-- Display Parental Education -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Parental Education</th>
                                <th class="table-mentor col-1"> <%= mentee.getParentEducation() ? "Yes" : "No"%></th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2"><%if (match.getParentalEdMatch()) {%><span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                        <%= match.getMentor().getParentEducation() ? "Yes" : "No"%></td> <%}%>
                            </tr>

                            <!-- Display Race -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Race / Ethnicity</th>
                                <th class="table-mentor col-1">
                                    <%for (String raceMentee : mentee.getRaceArray()) {%>
                                        <%= raceMentee %> <br />
                                    <%}%>
                                </th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2">
                                        <%for (String raceMentor : match.getMentor().getRaceArray()) {
                                            if (match.checkRace(raceMentor, match.getMentee())){%>
                                                <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                            <%= raceMentor %><br />
                                        <%}%>
                                    </td>
                                <%}%>
                            </tr>

                            <!-- Display Hobbies -->
                            <tr class="text-center d-flex">
                                <th class="table-categories col-1" scope="row">Hobbies</th>
                                <th class="table-mentor col-1">
                                    <%for (String hobbyMentee : mentee.getHobbiesArray()) {%>
                                        <%= hobbyMentee %> <br />
                                    <%}%>
                                </th>
                                <% for (Match match : matches) {%>
                                    <td class="col-2">
                                        <%for (String hobbyMentor : match.getMentor().getHobbiesArray()) {
                                            if (match.checkHobby(hobbyMentor, match.getMentee())){%>
                                                <span style="color: rgb(17, 202, 17);"><i class="fas fa-check-circle"></i></span><%}%>
                                            <%= hobbyMentor %> <br />
                                        <%}%>
                                    </td>
                                <%}%>
                            </tr>

                            <!-- Display Buttons -->
                            <tr class="text-center d-flex">
                                <th class="col-1" scope="row"></th>
                                <th class="col-1"></th>
                                <% for (Match match : matches) {%>
                                <td class="col-2">
                                    <!-- Compare Button sends data when clicked to modal (see js/compareModal.js) -->
                                    <button
                                            type="button"
                                            class="compare-button"
                                            data-toggle="modal"
                                            data-target="#compareModal"
                                            data-firstname="<%=match.getMentor().getFirstName()%>"
                                            data-lastname="<%=match.getMentor().getLastName()%>"
                                            data-email="<%=match.getMentor().getEmail()%>"
                                            data-major="<%=match.getMentor().getMajor()%>"
                                            data-year="<%=(match.getMentor().getYear().equals("3")) ? "Junior" : "Senior"%>"
                                            data-hobbies="<%=match.getMentor().getHobbiesList()%>"
                                            data-hometown="<%=match.getMentor().getHometown()%>"
                                            data-parenteducation="<%=match.getMentor().getParentEducation()?"Yes":"No"%>"
                                            data-language="<%=match.getMentor().getLanguageList()%>"
                                            data-race="<%=match.getMentor().getRaceList()%>"
                                            data-gender="<%=match.getMentor().getGender()%>"
                                            data-attitudefordifference="<%=match.getMentor().getAttitudeForDifference()%>"
                                            data-challenge="<%=match.getMentor().getChallenge()%>"
                                            data-successfulfirstyear="<%=match.getMentor().getForSuccessfulFirstYear()%>"
                                            data-menteecount="<%=match.getMentor().getMenteeCount()%>"
                                    >
                                        Compare Match
                                    </button>

                                    <!-- Select Mentee Button matches mentor to mentee -->
                                    <form action="dynamic_matches" method="post">
                                        <input type="hidden" name="mentorEmail" value=<%=match.getMentor().getEmail()%>>
                                        <input type="hidden" name="menteeEmail" value=<%=match.getMentee().getEmail()%>>
                                        <button type="submit" class="btn btn-success btn-sm">
                                            Select Mentor
                                        </button>
                                    </form>
                                </td>
                                <% } %>
                            </tr>

                            <!-- Compare Match Modal -->
                            <div class="modal fade" id="compareModal" tabindex="-1" role="dialog" aria-labelledby="compareModalLabel" aria-hidden="true">
                                <div class="modal-dialog modal-lg" role="document">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <div class="text-center col-11">
                                                <h3 class="modal-title" id="compareModalLabel">Compare Match</h3>
                                            </div>
                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                            </button>
                                        </div>
                                        <div class="modal-body">
                                            <div class="row">
                                        <%--  Mentor details--%>
                                                <div class="col-md-6">
                                                    <div class="text-center">
                                                        <h2>Mentor</h2>
                                                    </div>
                                                    <p><strong> Name: </strong> <span class="lastname"> </span> </p>
                                                    <p><strong>Major: </strong> <span class="major"> </span> </p>
                                                    <p><strong>Year: </strong> <span class="year"> </span> </p>
                                                    <p><strong>Hobbies: </strong> <span class="hobbies"> </span> </p>
                                                    <p><strong>Hometown: </strong> <span class="hometown"> </span> </p>
                                                    <p><strong>Parent Education: </strong> <span class="parentEducation"> </span> </p>
                                                    <p><strong>Language: </strong> <span class="language"> </span> </p>
                                                    <p><strong>Race: </strong> <span class="race"> </span> </p>
                                                    <p><strong>Gender: </strong> <span class="gender"> </span> </p>
                                                    <p><strong>Attitude For Difference: </strong> <span class="attitudeForDifference"> </span> </p>
                                                    <p><strong>Challenge: </strong> <span class="challenge"> </span> </p>
                                                    <p><strong>Successful First Year: </strong> <span class="successfulFirstYear"> </span> </p>
                                                    <p><strong>Mentee Count: </strong> <span class="menteeCount"> </span> </p>
                                                </div>
                                        <%--  Mentee details--%>
                                                <div class="col-md-6">
                                                    <div class="text-center">
                                                        <h2>Mentee</h2>
                                                    </div>
                                                    <p><strong>Name: </strong><%= mentee.getFirstName() + " " + mentee.getLastName()%>  </p>
                                                    <p><strong>Major: </strong><%=mentee.getMajor()%></p>
                                                    <p><strong>Year: </strong> <%= (mentee.getYear().equals("1")) ? "First Year" : "Sophomore"%></p>
                                                    <p><strong>Hobbies: </strong><%=mentee.getHobbiesList()%></p>
                                                    <p><strong>Hometown: </strong><%=mentee.getHometown()%></p>
                                                    <p><strong>Parental Education: </strong><%=mentee.getParentEducation()?"Yes":"No"%></p>
                                                    <p><strong>Language: </strong><%=mentee.getLanguageList()%></p>
                                                    <p><strong>Race: </strong><%=mentee.getRaceList()%></p>
                                                    <p><strong>Gender: </strong><%=mentee.getGender()%></p>
                                                    <p><strong>Looking Forward: </strong><%=mentee.getLookingForward()%></p>
                                                    <p><strong>Why do you want a peer mentor: </strong><%=mentee.getWhyMentor()%></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">Close</button>
                                            <form action="dynamic_matches" method="post">
                                                <input  type="hidden" name="mentorEmail" class="approveBtn">
                                                <input type="hidden" name="menteeEmail" value=<%=mentee.getEmail()%>>
                                                <button type="submit" class="btn btn-success btn-sm">
                                                    Approve Match
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </tbody>
                    </table>
                </div>
            </div>
           <% } %>

        <jsp:include page="includes/footer.jsp"/>
        <script type="text/javascript" src="js/typeahead.js"></script>
        <script>
            $(document).ready(function(){
                // Defining the local dataset
                let orphans = [];
                <% for (Mentee orphan : (List<Mentee>) request.getAttribute("orphans")) { %>
                    orphans.push("<%= orphan.getEmail() %>");
                <% } %>


                // Constructing the suggestion engine
                orphans = new Bloodhound({
                    datumTokenizer: Bloodhound.tokenizers.whitespace,
                    queryTokenizer: Bloodhound.tokenizers.whitespace,
                    local: orphans
                });

                // Initializing the typeahead
                $('.typeahead').typeahead({
                        hint: true,
                        highlight: true, /* Enable substring highlighting */
                        minLength: 1 /* Specify minimum characters required for showing suggestions */
                    },
                    {
                        name: 'orphans',
                        source: orphans
                    });
            });
        </script>
    </body>
</html>

