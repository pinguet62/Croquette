package fr.pinguet62.croquette.oauth;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.pinguet62.croquette.model.User;

/** Used when the OAuth server redirect user after her authentication. */
@WebServlet(urlPatterns = RedirectOAuth.URL)
public final class RedirectOAuth extends HttpServlet {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 7381061108303995228L;

    /** URL of this servlet. */
    public static final String URL = "/OAuth/redirect";

    @Override
    public void doGet(final HttpServletRequest request,
	    final HttpServletResponse response) throws IOException {

	String error = request.getParameter("error");
	if (error != null)
	    System.err.println(this.getClass().toString() + " : " + error);
	String token = request.getParameter("code");
	if (token == null) {
	    System.err.println(this.getClass().toString() + " : no token");
	    return;
	}

	User.initTest();
	User.get().setToken(token);

	// Authentication authentication = new OAuthAuthenticationToken(token);
	// SecurityContextHolder.getContext().setAuthentication(authentication);

	response.sendRedirect(request.getContextPath() + "/index.xhtml");
    }
}
