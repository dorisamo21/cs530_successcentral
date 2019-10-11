<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<jsp:include page="includes/header.jsp">
    <jsp:param name="title" value="Mentee Form"/>
</jsp:include>
<body>
<jsp:include page="includes/navbar.jsp"/>


<div class="container">
    <!-- Display flash message -->
    <% if (request.getSession().getAttribute("message") != null) { %>
    <h3 class="text-center"><%= request.getSession().getAttribute("message") %></h3>
    <% request.getSession().setAttribute("message", null);} %>
    
    <div class="mx-5">
        <h1 class="text-center">Mentee Intake Form</h1>
        <form action="mentee_form" method="post" onsubmit="return valChecked(this)">

            <!-- Basic info -->
            <fieldset>
                <legend>Personal information:</legend>

                <!-- Name -->
                <label><span style="color:red">*</span> Student Name</label>
                <div class="form-row">
                    <div class="col">
                        <input
                                type="text"
                                class="form-control"
                                placeholder="First name"
                                required=""
                                name="firstname"
                        />
                    </div>
                    <div class="col">
                        <input
                                type="text"
                                class="form-control"
                                placeholder="Last name"
                                required=""
                                name="lastname"
                        />
                    </div>
                </div>
                <br />


                <!-- Gender -->
                <label><span style="color:red">*</span> Student Gender</label>
                <br>
                <div class="form-check">
                    <input
                            class="form-check-input"
                            type="radio"
                            id="exampleRadios1"
                            value="Male"
                            name="gender"
                            required=""
                    />
                    <label class="form-check-label" for="exampleRadios1">Male </label></div>
                <div class="form-check" >
                    <input
                            class="form-check-input"
                            type="radio"
                            id="exampleRadios2"
                            value="Female"
                            name="gender"
                            required=""
                    />
                    <label class="form-check-label" for="exampleRadios2"
                    >Female </label
                    ></div>
                <div class="form-check" >
                    <input
                            class="form-check-input"
                            type="radio"
                            id="exampleRadios3"
                            value="Other"
                            name="gender"
                            required=""
                    />
                    <label class="form-check-label" for="exampleRadios3"
                    >Other</label
                    >
                </div>
                <br>

                <!-- Ethnicity -->
                <div class="form-row">
                    <div class="col">
                        <label><span style="color:red">*</span> Ethnicity. Check all that apply.</label>
                        <div class="form-check" required="">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck1" value="White">
                            <label class="form-check-label" for="defaultCheck1"> White </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck2" value="Black/African Am.">
                            <label class="form-check-label" for="defaultCheck2"> Black/African American </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck3" value="Hispanic">
                            <label class="form-check-label" for="defaultCheck3"> Hispanic </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck4" value="Latinx">
                            <label class="form-check-label" for="defaultCheck1"> Latino/Latina </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck5" value="Native American">
                            <label class="form-check-label" for="defaultCheck5"> Native American/American Indian </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck6" value="Asian">
                            <label class="form-check-label" for="defaultCheck6"> Asian </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck7" value="Pacific Islander">
                            <label class="form-check-label" for="defaultCheck7"> Pacific Islander </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="ethnicity" type="checkbox" id="defaultCheck8" value="Other" onclick="disableSubjectField()">
                            <label class="form-check-label" for="defaultCheck8"> Other </label>
                        </div>
                    </div>
                    <div class="col">
                        <label>If you selected Other, please specify.</label>
                        <input
                                type="text"
                                class="form-control"
                                placeholder="Specify Ethnicity"
                                name="otherethnicity"
                                id="EthnicityOther"
                                disabled
                        />
                    </div>
                </div>
                <br />

                <!-- CCSU Info -->
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label> <span style="color:red">*</span>Student CCSU Email</label>
                        <input
                                type="email"
                                class="form-control"
                                id="inputEmail"
                                placeholder="Enter Email"
                                required=""
                                name="email"
                                pattern="[A-Za-z0-9+_.-]+@(my.)?(ccsu.edu)"
                                oninvalid="this.setCustomValidity('Email must end in either my.ccsu.edu or ccsu.edu')"
                                oninput="setCustomValidity('')"
                        />
                    </div>
                    <div class="col">
                        <label><span style="color:red">*</span> CCSU Student ID Number</label>
                        <input
                                type="text"
                                class="form-control"
                                id="studentId"
                                placeholder="Enter ID Number"
                                required=""
                                maxlength="8" minlength="8"
                                name="ccsuid"
                                pattern= "[0-9]{8}"
                                oninvalid="this.setCustomValidity('CCSU ID number must be 8 digits')"
                                oninput="setCustomValidity('')"
                        />
                    </div>
                </div>
                <br />
            </fieldset>


            <!-- Questionairre -->
            <fieldset>
                <legend>Questionnaire:</legend>

                <!-- Year -->
                <label><span style="color:red">*</span> What year are you?</label>
                <select class="form-control" id="year" name="year" required="">
                    <option value="1">First Year</option>
                    <option value="2">Sophomore</option>
                </select>
                <small class="form-text text-muted">You must be a first year or sophomore next academic year to apply for a mentor.</small>
                <br />


                <!-- Major -->
                <label><span style="color:red">*</span> What is your major?</label>
                <br />
                <select class="form-control" name="major" required="">
                    <% for (Map.Entry<Integer, String> entry : ((Map<Integer, String>) request.getAttribute("majors")).entrySet()) {; %>
                    <option value="<%= entry.getKey() %>"><%= entry.getValue() %></option>
                    <% } %>
                </select>
                <br /><br/>


                <!-- Hometown -->
                <div class="form-row">
                    <div class="col">
                        <label><span style="color:red">*</span> What town do you consider home?</label>
                        <select class="form-control" name="hometown" id="hometown" onclick="disableSubjectField()" required="">
                            <% for (Map.Entry<Integer, String> entry : ((Map<Integer, String>) request.getAttribute("towns")).entrySet()) {; %>
                            <option value="<%= entry.getKey() %>"><%= entry.getValue() %></option>
                            <% } %>
                        </select>
                    </div>
                    <div class="col">
                        <label>If you selected Outside Connecticut, please specify.</label>
                        <input
                                type="text"
                                class="form-control"
                                placeholder="Specify Hometown"
                                name="otherhometown"
                                id="OtherHometown"
                                disabled
                        />
                    </div>
                </div>
                <br />


                <!-- Language -->
                <div class="form-row">
                    <div class="col">
                        <label>Languages, other than English, you are fluent in.</label>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check1" value="Spanish">
                            <label class="form-check-label" for="Check1"> Spanish </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check2" value="Chinese">
                            <label class="form-check-label" for="Check2"> Chinese </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check3" value="Tagalog">
                            <label class="form-check-label" for="Check3"> Tagalog </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check4" value="Vietnamese">
                            <label class="form-check-label" for="Check4"> Vietnamese </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check5" value="Arabic">
                            <label class="form-check-label" for="Check5"> Arabic </label></div>
                        <div class="form-check">
                            <input class="form-check-input" name="language" type="checkbox" id="Check6" value="Other" onclick="disableSubjectField()">
                            <label class="form-check-label" for="Check6"> Other </label></div>
                    </div>
                    <div class="col">
                        <label>If you selected Other, please specify.</label>
                        <input
                                type="text"
                                class="form-control"
                                id="OtherLanguage"
                                placeholder="Specify Language"
                                name="otherlanguage"
                                disabled
                        />
                    </div>
                </div>
                <br />

                <!-- Generation -->
                <label><span style="color:red">*</span> Did at least one of your legal guardians graduate from a four year college?</label>
                <br>
                <div class="form-check" required="">
                    <input
                            class="form-check-input"
                            type="radio"
                            id="exampleRadios5"
                            value="Yes"
                            name="generation"
                    />
                    <label class="form-check-label" for="exampleRadios5">Yes </label></div>
                <div class="form-check" >
                    <input
                            class="form-check-input"
                            type="radio"
                            id="exampleRadios6"
                            value="No"
                            name="generation"
                    />
                    <label class="form-check-label" for="exampleRadios6"
                    >No </label>
                </div><br>


                <!-- Hobbies -->
                <label><span style="color:red">*</span> Please select the hobbies/interests that apply to you.</label>
                <small class="form-text text-muted"
                >For best match, please select 5 hobbies.</small
                ><br>
                <div class="form-group">
                    <div class="form-check" id="hobbies" name="hobbies" required="">
                        <div class="row">
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Animals"/>
                                Animals/Pets </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Art"/>
                                Art </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Coding">
                                Coding </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Dancing">
                                Dancing </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Eating/Cooking">
                                Eating/Cooking </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Gaming">
                                Gaming </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Gym/Exercise">
                                Gym/Exercise </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Hanging Out">
                                Hanging Out </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Hiking">
                                Hiking/Walking </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Listening to Music">
                                Listening to Music </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Playing Music">
                                Playing Music </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Reading">
                                Reading </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="School Organizations">
                                School Organizations </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Shopping">
                                Shopping </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Sleeping">
                                Sleeping </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Social Media">
                                Social Media </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Solo Sports"/>
                                Solo Sports </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Swimming/Beach">
                                Swimming/Beaches </label></div>
                            <div class="col-sm-4">
                                <label>
                                    <input class="form-check-input" type="checkbox" name="hobbies" value="Team Sports"/>
                                    Team Sports
                                </label>
                            </div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Travel">
                                Traveling </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="TV/Movies">
                                Tv/Movies </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Watching Sports"/>
                                Watching Sports </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Writing">
                                Writing </label></div>
                            <div class="col-sm-4"><label><input class="form-check-input" type="checkbox" name="hobbies" value="Volunteering">
                                Volunteering </label></div>

                        </div>
                    </div>
                </div>
                <br />


                <!-- College Experience -->
                <label><span style="color:red">*</span> What are you most looking forward to experiencing in college?</label>
                <textarea class="form-control" name="looking_forward" rows="3" required=""></textarea>
                <br />

                <!-- Why Mentee -->
                <label><span style="color:red">*</span> Why do you want a peer mentor?</label>
                <textarea class="form-control" name="why_mentor" rows="3" required=""></textarea>
                <br />
            </fieldset>


            <!-- Submit button -->
            <button type="submit" class="btn btn-primary mb-2" onClick="return valChecked();">Submit</button>
        </form>
    </div>
</div>

<jsp:include page="includes/footer.jsp"/>
<script>function disableSubjectField() {
    if (document.getElementById('defaultCheck8').checked) {
        document.getElementById('EthnicityOther').disabled = false;
    } else {
        document.getElementById('EthnicityOther').value = '';
        document.getElementById('EthnicityOther').disabled = true;
    }


    if (document.getElementById('Check6').checked) {
        document.getElementById('OtherLanguage').disabled = false;
    } else {
        document.getElementById('OtherLanguage').value = '';
        document.getElementById('OtherLanguage').disabled = true;
    }

    if (document.getElementById("hometown").value === "170") {
        document.getElementById("OtherHometown").disabled = false;
    } else {
        document.getElementById("OtherHometown").value = '';
        document.getElementById("OtherHometown").disabled = true;
    }
}
function checkboxlimit(checkgroup, limit){
    var checkgroup=checkgroup;
    var limit=limit;
    for (var i=0; i<checkgroup.length; i++){
        checkgroup[i].onclick=function(){
            var checkedcount=0;
            for (var i=0; i<checkgroup.length; i++)
                checkedcount+=(checkgroup[i].checked)? 1 : 0;
            if (checkedcount>limit){
                alert("You can only select a maximum of "+limit+" hobbies");
                this.checked=false;
            }
        }
    }
}
checkboxlimit(document.getElementsByName("hobbies"), 5);

function valChecked()
{
    var checkboxs=document.getElementsByName("ethnicity");
    var okay=false;
    for(var i=0,l=checkboxs.length;i<l;i++)
    {
        if(checkboxs[i].checked)
        {
            okay=true;
            break;
        }
    }
    var checkhobby=document.getElementsByName("hobbies");
    var okay2=false;
    for(var i=0,l=checkhobby.length;i<l;i++)
    {
        if(checkhobby[i].checked)
        {
            okay2=true;
            break;
        }
    }
    if(!okay){
        alert("Please select at least one ethnicity.");
        return false;}
    else if(!okay2){
        alert("Please select at least one hobby.");
        return false;}
    else return true;
}
</script>
</body>
</html>