package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet("/reset_password")
public class ResetPasswordServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // They're already logged in.
        if (req.getSession().getAttribute("email") != null) {
            resp.sendRedirect("mentor_list");
            return;
        }

        //TODO Clean expired forgot requests here
        String token = req.getParameter("token");
        String userEmail = crud.getUserEmailFromForgotPasswordToken(token);

        req.setAttribute("email", userEmail);
        req.setAttribute("token", token);
        req.getRequestDispatcher("reset_password.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String token = req.getParameter("token");
        String userEmail = crud.getUserEmailFromForgotPasswordToken(token);
        String password = req.getParameter("password");
        String retypePassword = req.getParameter("retypepassword");

        if (password == null || !password.equals(retypePassword)) {
            req.getSession().setAttribute("message", "<p class=\"text-danger\">The passwords you entered did not match. Please try again.</p>");
            req.setAttribute("token", req.getParameter("token"));
            resp.sendRedirect("reset_password");
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

        // Update the passwword
        User user = crud.getUser(userEmail);
        user.setPassword(hashedPwd);
        crud.updateUser(user);

        // Delete the request to change the password.
        crud.deleteChangePasswordRequest(token);

        String message = "<p class=\"text-success\">Password changed successfully!</p>";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect("login");
    }
}