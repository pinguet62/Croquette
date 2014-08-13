package fr.pinguet62.croquette.oauth;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import fr.pinguet62.croquette.model.User;
import fr.pinguet62.croquette.springsecurity.OAuthAuthenticationToken;

/** Used when the OAuth server redirect user after her authentication. */
@WebServlet(urlPatterns = RedirectOAuthServlet.URL)
public final class RedirectOAuthServlet extends HttpServlet {

    /** Logger. */
    private static final Logger LOGGER = Logger
            .getLogger(LoginOAuthServlet.class);

    /** Redirect URL after OAuth authentication. */
    public static final String REDIRECT_URL = "/index.xhtml";

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** URL of this {@link Servlet}. */
    public static final String URL = "/OAuth/redirect";

    /** Google OAuth redirection after authentication. */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Get code
        String error = request.getParameter("error");
        if (error != null) {
            LOGGER.error("OAuth redirect error: " + error);
            response.sendRedirect(request.getContextPath()
                    + RedirectOAuthServlet.REDIRECT_URL);
            return;
        }
        String code = request.getParameter("code");
        if (code == null) {
            LOGGER.error("OAuth redirect error: no error or code.");
            response.sendRedirect(request.getContextPath()
                    + RedirectOAuthServlet.REDIRECT_URL);
            return;
        }

        String token = getToken(code);

        // Spring security authentication
        Authentication authentication = new OAuthAuthenticationToken(token);
        // For multi-threading
        SecurityContextHolder
        .setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // Store security context into session
        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        String email = getEmail(token);
        User.get().setEmail(email);

        response.sendRedirect(request.getContextPath()
                + RedirectOAuthServlet.REDIRECT_URL);
    }

    /**
     * Gets email from token.
     *
     * @param token
     *            The OAuth token.
     * @return The email.
     * @throws IOException
     *             Exception.
     */
    private String getEmail(String token) throws IOException {
        String strUrl = "https://www.googleapis.com/oauth2/v1/userinfo"
                + ("?access_token=" + token);
        LOGGER.debug("HTTP request for getting email: " + strUrl);
        URL url = new URL(strUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url
                .openConnection();

        JsonReader reader = Json.createReader(connection.getInputStream());
        JsonObject jsonResponse = reader.readObject();
        String email = jsonResponse.getString("email");
        LOGGER.info("OAuth email: " + email);

        return email;
    }

    /**
     * Gets OAuth token from code.
     *
     * @param code
     *            The OAuth code.
     * @return The OAuth token.
     * @throws IOException
     *             Exception.
     */
    private String getToken(String code) throws IOException {
        String strUrl = "https://accounts.google.com/o/oauth2/token";
        LOGGER.debug("HTTP request for getting token: URL=" + strUrl);
        URL url = new URL(strUrl);
        HttpsURLConnection connection = (HttpsURLConnection) url
                .openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        OutputStream outputStream = connection.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        String parameters = ("code=" + code)
                + "&client_id=79632324639.apps.googleusercontent.com"
                + "&client_secret=tGxcnt4qjzJ7c39pL87UG_Ek"
                + "&grant_type=authorization_code"
                + ("&redirect_uri=http://localhost:8081/Croquette" + RedirectOAuthServlet.URL);
        LOGGER.debug("HTTP request for getting token: Parameters=" + parameters);
        dataOutputStream.writeBytes(parameters);
        dataOutputStream.flush();
        dataOutputStream.close();

        JsonReader reader = Json.createReader(connection.getInputStream());
        JsonObject jsonResponse = reader.readObject();
        String token = jsonResponse.getString("access_token");
        LOGGER.info("OAuth token: " + token);

        return token;
    }

}
