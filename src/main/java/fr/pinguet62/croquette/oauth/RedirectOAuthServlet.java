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

import fr.pinguet62.croquette.model.User;

/** Used when the OAuth server redirect user after her authentication. */
@WebServlet(urlPatterns = RedirectOAuthServlet.URL)
public final class RedirectOAuthServlet extends HttpServlet {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 7381061108303995228L;

    /** URL of this {@link Servlet}. */
    public static final String URL = "/OAuth/redirect";

    /** Google OAuth redirection after authentication. */
    @Override
    protected void doGet(final HttpServletRequest request,
	    final HttpServletResponse response) throws ServletException,
	    IOException {
	response.sendRedirect(request.getContextPath() + "/index.xhtml");

	// Get code
	String error = request.getParameter("error");
	if (error != null) {
	    System.err.println(this.getClass().toString() + " : " + error);
	    return;
	}
	String code = request.getParameter("code");
	if (code == null) {
	    System.err.println(this.getClass().toString() + " : no token");
	    return;
	}

	// Request Google OAuth to get token
	URL url = new URL("https://accounts.google.com/o/oauth2/token");
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
	dataOutputStream.writeBytes(parameters);
	dataOutputStream.flush();
	dataOutputStream.close();

	// Get Token
	JsonReader reader = Json.createReader(connection.getInputStream());
	JsonObject jsonResponse = reader.readObject();
	String token = jsonResponse.getString("access_token");

	// TODO delete this
	User.initTest();
	User.get().setToken(token);
	// TODO Implement
	// Authentication authentication = new OAuthAuthenticationToken(token);
	// SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
