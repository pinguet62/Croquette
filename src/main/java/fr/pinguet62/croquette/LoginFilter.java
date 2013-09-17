package fr.pinguet62.croquette;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Filter offline users. <br />
 * Redirect to "Sign In" Google page if he is not identified.
 * 
 * @author Pinguet62
 */
@WebFilter(description = "Filter offline users", filterName = "loginFilter", urlPatterns = "/*")
public final class LoginFilter implements Filter {

    /** {@inheritDoc} */
    @Override
    public void destroy() {
    }

    /** {@inheritDoc} */
    @Override
    public void doFilter(final ServletRequest request,
	    final ServletResponse response, final FilterChain chain)
	    throws IOException, ServletException {
	// TODO implement LoginFilter.doFilter()
	// final LoginManagedBean loginBean = (LoginManagedBean)
	// ((HttpServletRequest) request)
	// .getSession().getAttribute("loginManagedBean");
	// if ((loginBean == null) || !LoginManagedBean.isConnected())
	// ((HttpServletResponse) response)
	// .sendRedirect(((HttpServletRequest) request)
	// .getContextPath() + "/login.xhtml");

	chain.doFilter(request, response);
    }

    /** {@inheritDoc} */
    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

}
