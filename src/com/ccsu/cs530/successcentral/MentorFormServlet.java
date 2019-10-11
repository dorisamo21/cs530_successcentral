package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet("/mentor_form")
public class MentorFormServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setAttribute("towns", crud.getAllConnecticutTowns());
        req.setAttribute("majors", crud.getAllMajors());
        req.getRequestDispatcher("mentor_form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        // validate input
        boolean valemail = validateEmail(req.getParameter("email"));
        String email = req.getParameter("email");

        String message;
        if (crud.getMentor(email).getEmail() != null) {
            message = "<p class=\"text-danger\">A mentor with this email already exists in the system. This mentor must be deleted before it can be overwritten.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("mentor_form");
            return;
        }

        // Instantiate mentor and then save him/her to database
        if (valemail) {
            Mentor mentor = new Mentor();
            if (req.getParameter("firstname") != null) {
                mentor.setFirstName(req.getParameter("firstname"));
            }
            if (req.getParameter("lastname") != null) {
                mentor.setLastName(req.getParameter("lastname"));
            }
            if (req.getParameter("gender") != null) {
                mentor.setGender(req.getParameter("gender"));
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

                mentor.setRace(ethnicityString);
            }
            if (req.getParameter("email") != null) {
                mentor.setEmail(req.getParameter("email"));
            }
            if (req.getParameter("ccsuid") != null) {
                mentor.setStudentId(req.getParameter("ccsuid"));
            }
            if (req.getParameter("year") != null) {
                mentor.setYear(req.getParameter("year"));
            }
            if (req.getParameter("major") != null) {
                mentor.setMajor(req.getParameter("major"));
            }
            if (req.getParameter("hometown") != null) {
                mentor.setCtHometown(req.getParameter("hometown"));
            }
            if (req.getParameter("otherhometown") != null) {
                mentor.setOtherHometown(req.getParameter("otherhometown"));
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

                mentor.setLanguage(languageString);
            } else {
                mentor.setLanguage("English Only");
            }
            if (req.getParameter("generation") != null) {
                mentor.setParentEducation(req.getParameter("generation").equals("Yes"));
            }
            if (req.getParameter("hobbies") != null) {
                String[] hobbiesFromForm = req.getParameterValues("hobbies");
                String hobbiesString = "";
                for (int i = 0; i < hobbiesFromForm.length; i++){
                    hobbiesString += hobbiesFromForm[i] + (i < hobbiesFromForm.length - 1?",":"");
                }
                mentor.setHobbies(hobbiesString);
            }
            if (req.getParameter("reason") != null) {
                mentor.setAttitudeForDifference(req.getParameter("reason"));
            }
            if (req.getParameter("mentor_requirement") != null) {
                mentor.setMentorReq(req.getParameter("mentor_requirement"));
            }
            if (req.getParameter("challenge") != null) {
                mentor.setChallenge(req.getParameter("challenge"));
            }
            if (req.getParameter("success") != null) {
                mentor.setForSuccessfulFirstYear(req.getParameter("success"));
            }

            // if the user already exists, update 'em
            if (crud.getUser(mentor.getEmail()).getEmail() != null) {
                crud.updateUser(mentor);
            } else {
                crud.createUser(mentor);
            }
            crud.createMentor(mentor);
//            message = "<p class=\"text-success border border-success\">Mentor successfully created!</p>";
            req.setAttribute("mentor", "true");
            req.setAttribute("email", mentor.getEmail());
            req.getRequestDispatcher("form_success").forward(req, resp);
        } else {
            message = "<p class=\"text-danger border border-danger\">One of the fields did not validate. Mentor was not created.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("mentor_form");
        }
//
//        req.getSession().setAttribute("message", message);
//        resp.sendRedirect("mentor_form");
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
