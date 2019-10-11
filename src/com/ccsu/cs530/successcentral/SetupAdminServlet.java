package com.ccsu.cs530.successcentral;

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

@WebServlet("/setupadmin")
public class SetupAdminServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("setupadmin.jsp").forward(req, resp);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
//        String retypeEmail = req.getParameter("retypeemail");
        String retypePassword = req.getParameter("retypepassword");
        String message;

        // validate username and password
//        if (email == null || retypeEmail == null || !email.equals(retypeEmail)) {
//            req.getSession().setAttribute("message", "<p class=\"text-danger\">The emails you entered did not match. Please try again.</p>");
//            resp.sendRedirect("setupadmin");
//            return;
//        }

        if (password == null || retypePassword == null || !password.equals(retypePassword)) {
            req.getSession().setAttribute("message", "<p class=\"text-danger\">The passwords you entered did not match. Please try again.</p>");
            resp.sendRedirect("setupadmin");
            return;
        }

        // make sure they are not already a user.
        if (crud.getUser(email).getEmail() != null) {
            message = "<p class=\"text-danger\">A user with the email supplied already exists.";
            message += " If you are already a mentor, click <a href=\"forgot_password\">here</a> to create a password.</p>";
            req.getSession().setAttribute("message", message);
            resp.sendRedirect("register");
            return;
        }

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

        // Create the user
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPwd);
        user.setIsAdmin(true);
        crud.createAdmin(user);

        //log them in
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }
        HttpSession newSession = req.getSession(true);
        newSession.setAttribute("email", email);
        newSession.setAttribute("isAdmin", "true");
        message = "<p class=\"text-success\">Registration Successful! Welcome " + user.getEmail() + "!</p>";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect("mentor_list");
    }
}
