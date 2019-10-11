package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentee;
import com.ccsu.cs530.successcentral.service.CrudService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


@WebServlet("/mentee_form")
public class MenteeFormServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("towns", crud.getAllConnecticutTowns());
        req.setAttribute("majors", crud.getAllMajors());
        req.getRequestDispatcher("mentee_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // validate input
        boolean valemail = validateEmail(req.getParameter("email"));
        String email = req.getParameter("email");
        // Instantiate mentee and then save him/her to database
        String message;
        if (crud.getMentee(email).getEmail() != null) {
            message = "<p class=\"text-danger\">A mentee with this email already exists in the system. This mentee must be deleted before it can be overwritten.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("mentee_form");
            return;
        }
        if (valemail) {
            Mentee mentee = new Mentee();
            if (req.getParameter("firstname") != null) {
                mentee.setFirstName(req.getParameter("firstname"));
            }
            if (req.getParameter("lastname") != null) {
                mentee.setLastName(req.getParameter("lastname"));
            }
            if (req.getParameter("gender") != null) {
                mentee.setGender(req.getParameter("gender"));
            }
            if (req.getParameter("ethnicity") != null) {
                String[] ethnicityFromForm = req.getParameterValues("ethnicity");
                String ethnicityString= "";
                for (int i = 0; i < ethnicityFromForm.length; i++){
                    if (ethnicityFromForm[i].equals("Other") && req.getParameter("otherethnicity") != null && !req.getParameter("otherethnicity").equals("")){
                        ethnicityString += req.getParameter("otherethnicity") + (i < ethnicityFromForm.length - 1?",":"");
                    } else {
                        ethnicityString += ethnicityFromForm[i] + (i < ethnicityFromForm.length - 1?",":"");
                    }
                }

                mentee.setRace(ethnicityString);
            }
            if (req.getParameter("email") != null) {
                mentee.setEmail(req.getParameter("email"));
            }
            if (req.getParameter("ccsuid") != null) {
                mentee.setStudentId(req.getParameter("ccsuid"));
            }
            if (req.getParameter("year") != null) {
                mentee.setYear(req.getParameter("year"));
            }
            if (req.getParameter("major") != null) {
                mentee.setMajor(req.getParameter("major"));
            }
            if (req.getParameter("hometown") != null) {
                mentee.setCtHometown(req.getParameter("hometown"));
            }
            if (req.getParameter("otherhometown") != null) {
                mentee.setOtherHometown(req.getParameter("otherhometown"));
            }
            if (req.getParameter("language") != null) {
                String[] languageFromForm = req.getParameterValues("language");
                String languageString= "";
                for (int i = 0; i < languageFromForm.length; i++){
                    if (languageFromForm[i].equals("Other") && req.getParameter("otherlanguage") != null && !req.getParameter("otherlanguage").equals("")){
                        languageString += req.getParameter("otherlanguage") + (i < languageFromForm.length - 1?",":"");
                    } else {
                        languageString += languageFromForm[i] + (i < languageFromForm.length - 1?",":"");
                    }
                }

                mentee.setLanguage(languageString);
            } else {
                mentee.setLanguage("English Only");
            }
            if (req.getParameter("generation") != null) {
                mentee.setParentEducation(req.getParameter("generation").equals("Yes"));
            }
            if (req.getParameter("hobbies") != null) {
                String[] hobbiesFromForm = req.getParameterValues("hobbies");
                String hobbiesString = "";
                for (int i = 0; i < hobbiesFromForm.length; i++){
                    hobbiesString += hobbiesFromForm[i] + (i < hobbiesFromForm.length - 1?",":"");
                }
                mentee.setHobbies(hobbiesString);
            }
            if (req.getParameter("looking_forward") != null) {
                mentee.setLookingForward(req.getParameter("looking_forward"));
            }
            if (req.getParameter("why_mentor") != null) {
                mentee.setWhyMentor(req.getParameter("why_mentor"));
            }

            // if the user already exists, update the user
            if (crud.getUser(mentee.getEmail()).getEmail() != null) {
                crud.updateUser(mentee);
            } else {
                crud.createUser(mentee);
            }

            crud.createMentee(mentee);
            //message = "<p class=\"text-success border border-success\">Mentee successfully created!</p>";
            req.setAttribute("mentor", "false");
            req.setAttribute("email", mentee.getEmail());
            req.getRequestDispatcher("form_success").forward(req, resp);
        } else {
            message = "<p class=\"text-danger border border-danger\">One of the fields did not validate. Mentee was not created.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("mentee_form");
        }

//        req.getSession().setAttribute("message", message);
//        resp.sendRedirect("mentee_form");
    }


    private boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(my.)?(ccsu.edu)$";
        Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pat.matcher(email);
        if (email == null)
            return false;
        return matcher.matches();
    }

}
