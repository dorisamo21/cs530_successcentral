package com.ccsu.cs530.successcentral;


import com.ccsu.cs530.successcentral.model.Mentor;
import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email") == null ? req.getParameter("reg_email") : req.getParameter("email");
        String password = req.getParameter("password");
        String retypeEmail = req.getParameter("retypeemail");
        String retypePassword = req.getParameter("retypepassword");
        String message;

//        // validate username and password
//        if (email == null || retypeEmail == null || !email.equals(retypeEmail)) {
//            req.getSession().setAttribute("message","<p class=\"text-danger\">The emails you entered did not match. Please try again.</p>");
//            resp.sendRedirect("register");
//            return;
//        }

        if (password == null || retypePassword == null || !password.equals(retypePassword)) {
            req.getSession().setAttribute("message", "<p class=\"text-danger\">The passwords you entered did not match. Please try again.</p>");
            resp.sendRedirect("register");
            return;
        }

        // make sure they are a mentor, not registed and are approved
        Mentor refMentor = crud.getMentor(email);

        if (refMentor.getEmail() == null){
            message = "<p class=\"text-danger\">No mentor with this email exists.<br />";
            message += "To apply to become a mentor, click <a href=\"mentor_form\">here</a>.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("register");
            return;
        } else if (refMentor.getPassword() != null){
            message = "<p class=\"text-danger\">This email has already been registered in the system.<br />";
            message += "To log in, click <a href=\"login\">here</a>.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("register");
            return;
        } else if (!refMentor.getApproved()) {
            message = "<p class=\"text-danger\">An application was received with this email, but has not been approved.<br />";
            message += "Please watch your email inbox for an approval notification, then you can register.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("register");
            return;
        }
//        if (oldUser.getEmail() != null & oldUser.getPassword() != null) { // They're already registered
//            message = "<p class=\"text-danger\">A user with the email supplied already exists.";
//            message += " If you are already a mentor, click <a href=\"forgot_password\">here</a> to create a password.</p>";
//            req.getSession().setAttribute("message", message);
//            resp.sendRedirect("register");
//            return;
//        }

        // salt and hash the password
        String saltedPwd = password + LoginServlet.SALT;
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not implement MD5 hash of Success Central password correctly");
        }

        md.update(saltedPwd.getBytes());
        byte[] digest = md.digest();
        String hashedPwd = DatatypeConverter.printHexBinary(digest);

        // Update the user, or create (though that shouldn't ever happen)
        if (refMentor.getEmail() != null) {
            refMentor.setPassword(hashedPwd);
            crud.updateUserPassword(refMentor);
        } else {
            User user = new User();
            user.setEmail(email);
            user.setPassword(hashedPwd);
            crud.createUser(user);
        }

        //log them in
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession newSession = req.getSession(true);
        newSession.setAttribute("email", email);
        newSession.setAttribute("isAdmin", "false");

        message = "<p class=\"text-success\">Registration Successful! Welcome " + email + "!</p>";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect("my_mentee_list");
    }
}
