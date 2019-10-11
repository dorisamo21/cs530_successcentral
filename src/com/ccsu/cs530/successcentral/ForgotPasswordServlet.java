package com.ccsu.cs530.successcentral;

import com.ccsu.cs530.successcentral.model.User;
import com.ccsu.cs530.successcentral.service.CrudService;
import com.ccsu.cs530.successcentral.service.EmailService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebServlet("/forgot_password")
public class ForgotPasswordServlet extends HttpServlet {
    private CrudService crud = new CrudService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("forgot_password.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");

        User user = crud.getUser(email);
        if (user.getEmail() == null) {
            // not a Success Central user
            req.getSession().setAttribute("message", "<p class=\"text-danger\">The email you entered does not belong to a Success Central user.</p>");
            resp.sendRedirect("forgot_password");
            return;
        }

        // Generate a unique token
        String seed = user.getEmail() + Instant.now();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(seed.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not implement MD5 hash of Success Central password correctly");
        }

        byte[] digest = md.digest();

        final String token = DatatypeConverter.printHexBinary(digest);
        crud.deleteChangePasswordRequestForUser(email);
        crud.createPasswordReset(email, token);

        // Send email asynchronously (takes about 2000 ms).
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(() -> EmailService.sendPasswordResetEmail(user.getEmail(), token));
        executorService.shutdown();

        String message = "<p class=\"text-success\">A reminder has been sent to your email address " + email + ".</p>";
        req.getSession().setAttribute("message", message);
        resp.sendRedirect("forgot_password");
    }
}
