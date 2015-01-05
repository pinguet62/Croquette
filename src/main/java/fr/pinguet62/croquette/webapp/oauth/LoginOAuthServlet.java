package fr.pinguet62.croquette.webapp.oauth;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** {@link Servlet} used to redirect to Google OAuth.<br /> */
@WebServlet(urlPatterns = "/OAuth/login")
public final class LoginOAuthServlet extends HttpServlet {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoginOAuthServlet.class);

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** URL for Google OAuth. */
    private static final String TARGET_URL = "https://accounts.google.com/o/oauth2/auth"
            + "?client_id=79632324639.apps.googleusercontent.com"
            + ("&redirect_uri=http://localhost:8081/Croquette" + RedirectOAuthServlet.URL)
            + "&response_type=code"
            + ("&scope=https://www.googleapis.com/auth/userinfo.email" /* Email */
                    + "+https://www.google.com/m8/feeds" /* Contacts */
                    + "+https://www.googleapis.com/auth/googletalk" /* GTalk */);

    /** Redirect user to Google OAuth authentication. */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info(LoginOAuthServlet.TARGET_URL);
        response.sendRedirect(LoginOAuthServlet.TARGET_URL);
    }

}
