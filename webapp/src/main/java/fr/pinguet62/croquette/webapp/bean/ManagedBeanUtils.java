package fr.pinguet62.croquette.webapp.bean;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;

/** {@link javax.faces.bean.ManagedBean} utils. */
public final class ManagedBeanUtils {

    /**
     * Get bean from its name.
     *
     * @param name
     *            The name of bean (without <code>#{</code> <code>}</code> of
     *            EL).
     * @return The bean.
     * @throws FacesException
     *             Bean not found.
     */
    public static Object getManagedBean(String name) {
        Object bean;
        try {
            ELContext elContext = FacesContext.getCurrentInstance()
                    .getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, name);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);
        }
        if (bean == null)
            throw new FacesException(
                    "Managed bean with name '"
                            + name
                            + "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
        return bean;
    }

    private ManagedBeanUtils() {
    }

}
