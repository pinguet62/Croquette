package fr.pinguet62.croquette.model.oauth;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Used when the OAuth server redirect user after her authentication. */
@WebServlet(urlPatterns = RedirectOAuth.URL)
public final class RedirectOAuth extends HttpServlet {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 7381061108303995228L;

    /** URL of this servlet. */
    public static final String URL = "/OAuth/redirect";

    @Override
    public void doGet(final HttpServletRequest request,
	    final HttpServletResponse response) {
    }

}
