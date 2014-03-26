package fr.pinguet62.croquette.bean;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

public final class ManagedBean {

    /**
     * Gets bean from its name.
     *
     * @param beanName
     *            The name of bean (without <code>#{</code> <code>}</code>).
     * @return The bean, <code>null</code> if not found.
     */
    public static Object getManagedBean(String beanName) {
	Object bean;
	try {
	    ELContext elContext = FacesContext.getCurrentInstance()
		    .getELContext();
	    bean = elContext.getELResolver()
		    .getValue(elContext, null, beanName);
	} catch (RuntimeException e) {
	    throw new FacesException(e.getMessage(), e);
	}
	if (bean == null)
	    throw new FacesException(
		    "Managed bean with name '"
			    + beanName
			    + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
	return bean;
    }

    /** Constructor. */
    private ManagedBean() {
    }

}
