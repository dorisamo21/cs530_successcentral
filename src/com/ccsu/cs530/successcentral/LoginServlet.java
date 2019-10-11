package com.ccsu.cs530.successcentral;

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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    public static final String SALT = "success_central";

    private CrudService crud = new CrudService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        // server side validation for email and password.

        // salt and hash password
        String saltedPwd = (password + SALT);
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(saltedPwd.getBytes());
            byte[] digest = md.digest();
            String hashedPwd = DatatypeConverter.printHexBinary(digest);
            String actualHashedPwd = crud.getPassword(email);

            if (actualHashedPwd != null && actualHashedPwd.equals(hashedPwd)) { // password is correct
                HttpSession oldSession = req.getSession(false);
                if (oldSession != null) {
                    oldSession.invalidate();
                }
                HttpSession newSession = req.getSession(true);
                newSession.setAttribute("email", email);
                newSession.setAttribute("isAdmin", crud.isAdmin(email)?"true":"false");
                if (newSession.getAttribute("isAdmin") == "true") {
                    resp.sendRedirect("mentor_list");
                } else {
                    resp.sendRedirect("my_mentee_list");
                }
                return;
            } else {
                req.getSession().setAttribute("message", "<p class=\"text-danger\">Login Unsuccessful. Please check Email/Password combination.</p>");
                resp.sendRedirect("login");
                return;
            }
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not implement MD5 hash of Success Central password correctly");
        }
    }
}
