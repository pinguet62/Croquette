package oauth;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Used to call Google OAuth service. */
@WebServlet(urlPatterns = "/OAuth/login")
public final class LoginOAuth extends HttpServlet {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 751973283607034249L;

    private static final String url = "https://accounts.google.com/o/oauth2/auth"
	    + "?response_type=code"
	    + "&redirect_uri=http://localhost:8081/Croquette/OAuth/redirect"
	    + "&client_id=79632324639.apps.googleusercontent.com"
	    + "&scope=https://www.googleapis.com/auth/userinfo.email"
	    + "+https://www.google.com/m8/feeds"
	    + "+https://www.googleapis.com/auth/googletalk";

    /** The token. */
    private String token = null;

    /** Default constructor. */
    public LoginOAuth() {
    }

    /**
     * Constructor.
     * 
     * @param token
     *            The token.
     */
    public LoginOAuth(final String token) {
	this.token = token;
    }

    @Override
    public void doGet(final HttpServletRequest request,
	    final HttpServletResponse response) {
	try {
	    response.sendRedirect(LoginOAuth.url);
	} catch (IOException e) {
	}
    }

    /**
     * Gets the token.
     * 
     * @return The token.
     */
    public String getToken() {
	return this.token;
    }

    /**
     * Sets the token.
     * 
     * @param token
     *            The token to set.
     */
    public void setToken(final String token) {
	this.token = token;
    }

}
