<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Import Mentors or Mentees"/>
</jsp:include>
<body>
<jsp:include page="includes/navbar.jsp"/>

<div class="container">
    <% if (request.getSession().getAttribute("fileContents") == null) { %>
    <h1>Import Mentors or Mentees from CSV</h1>
    <p>To create a CSV file from an XLSX file, open the file in Excel, choose Save As... and choose the .CSV file extension.</p>

    <form action="import" method="post" enctype="multipart/form-data">
        <input type="file" name="csv" required>
        <label for="usertype">Test select:</label>
        <select class="form-control" id="usertype" name="usertype">
            <option>Mentors</option>
            <!--<option>Mentees</option>-->
        </select>
        <input type="submit" value="Import">
    </form>
    <% } else { %>
    <h1>Importing File</h1>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">#</th>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Email</th>
            <th scope="col">CCSU ID</th>
            <th scope="col">Major</th>
            <th scope="col">GPA</th>
            <th scope="col">Grade</th>
            <th scope="col">Hobbies</th>
            <th scope="col">Hometown</th>
            <th scope="col">Language</th>
            <th scope="col">Why are you a good mentor?</th>
            <th scope="col">How can you be a good mentor to someone different than you?</th>
            <th scope="col">Describe a time you overcame a challenge.</th>
            <th scope="col">How can a first-generation college student succeed?</th>
        </tr>
        </thead>
        <tbody>
        <% for (String[] row : (String[][])request.getSession().getAttribute("fileContents")) { %>
        <tr>
            <th scope="col"><%= row[0] %></th>
            <% for (int i = 4; i <=17; i++){ %>
            <td scope="col"><%= row[i].length() >= 50 ? row[i].substring(0, 50) + "..." : row[i] %></td>
            <% } %>

        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</div>


<jsp:include page="includes/footer.jsp"/>
</body>
</html>
