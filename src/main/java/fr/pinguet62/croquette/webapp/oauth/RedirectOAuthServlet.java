package fr.pinguet62.croquette.webapp.oauth;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.google.gson.JsonParser;

import fr.pinguet62.croquette.webapp.model.User;
import fr.pinguet62.croquette.webapp.springsecurity.OAuthAuthenticationToken;

/**
 * {@link HttpServlet} used to intercept the {@link ServletRequest} after
 * redirection by OAuth authentication, and get all informations of OAuth
 * connection and user's informations.
 * <p>
 * <ol>
 * <li>GET request with {@code "code"} parameter <br>
 * {@link #doGet(HttpServletRequest, HttpServletResponse)}</li>
 * <li>POST request with code to get token;<br>
 * {@link #getToken(String)}</li>
 * <li>GET request with token to get user's email<br>
 * {@link #getEmail(String)}</li>
 * </ol>
 */
@WebServlet(urlPatterns = RedirectOAuthServlet.URL)
public final class RedirectOAuthServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RedirectOAuthServlet.class);

    /** The URL where user will be redirected after OAuth authentication. */
    public static final String REDIRECT_URL = "/index.xhtml";

    private static final long serialVersionUID = 1;

    /** URL of this {@link Servlet}. */
    public static final String URL = "/OAuth/redirect";

    /** OAuth redirection after authentication. */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Error
        String error = request.getParameter("error");
        if (error != null) {
            LOGGER.error("OAuth redirect error: " + error);
            response.sendRedirect(request.getContextPath()
                    + RedirectOAuthServlet.REDIRECT_URL);
            return;
        }

        // Get code
        String code = request.getParameter("code");
        if (code == null) {
            LOGGER.error("OAuth redirect error: no error or code.");
            response.sendRedirect(request.getContextPath()
                    + RedirectOAuthServlet.REDIRECT_URL);
            return;
        }

        // Get token
        String token = getToken(code);

        // Spring security authentication
        Authentication authentication = new OAuthAuthenticationToken(token);
        // - for multi-threading
        SecurityContextHolder
                .setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // - store security context into session
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        // Get email
        String email = getEmail(token);
        User.get().setEmail(email);

        // Redirect
        response.sendRedirect(request.getContextPath()
                + RedirectOAuthServlet.REDIRECT_URL);
    }

    /**
     * Get user's email from OAuth token.
     *
     * @param token
     *            The OAuth token.
     * @return The email.
     */
    private String getEmail(String token) throws IOException {
        String strUrl = "https://www.googleapis.com/oauth2/v1/userinfo"
                + "?access_token=" + token;
        LOGGER.debug("HTTP request to get email: " + strUrl);
        URL url = new URL(strUrl);

        HttpsURLConnection connection = (HttpsURLConnection) url
                .openConnection();

        Reader reader = new InputStreamReader(connection.getInputStream());
        String email = new JsonParser().parse(reader).getAsJsonObject()
                .get("email").getAsString();
        LOGGER.info("OAuth email: " + email);

        return email;
    }

    /**
     * Get OAuth token from OAuth code.
     *
     * @param code
     *            The OAuth code.
     * @return The OAuth token.
     */
    private String getToken(String code) throws IOException {
        String strUrl = "https://accounts.google.com/o/oauth2/token";
        LOGGER.debug("HTTP request to get token: URL=" + strUrl);
        URL url = new URL(strUrl);

        HttpsURLConnection connection = (HttpsURLConnection) url
                .openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        // Request
        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String parameters = "code=" + code
                + "&client_id=79632324639.apps.googleusercontent.com"
                + "&client_secret=tGxcnt4qjzJ7c39pL87UG_Ek"
                + "&grant_type=authorization_code"
                + "&redirect_uri=http://localhost:8081/Croquette"
                + RedirectOAuthServlet.URL;
        LOGGER.debug("HTTP request for getting token: Parameters=" + parameters);
        dataOutputStream.writeBytes(parameters);
        dataOutputStream.flush();
        dataOutputStream.close();

        // Get token
        Reader reader = new InputStreamReader(connection.getInputStream());
        String token = new JsonParser().parse(reader).getAsJsonObject()
                .get("access_token").getAsString();
        LOGGER.info("OAuth token: " + token);

        return token;
    }

}
