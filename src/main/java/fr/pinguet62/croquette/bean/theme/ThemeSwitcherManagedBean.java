package fr.pinguet62.croquette.bean.theme;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/** Used to switch of PrimeFaces theme. */
@ManagedBean(eager = true, name = "themeSwitcherManagedBean")
@ApplicationScoped
public final class ThemeSwitcherManagedBean {

    /** The current {@link Theme}. */
    private Theme theme = null;

    /**
     * Gets the current {@link Theme}.
     * 
     * @return The current {@link Theme}.
     */
    public Theme getTheme() {
	return this.theme;
    }

    /**
     * Gets the list of {@link Theme}s.
     * 
     * @return The list of {@link Theme}s.
     */
    public Iterable<Theme> getThemes() {
	return Theme.AVAILABLES;
    }

    /** Initialization of this bean. */
    @PostConstruct
    private void init() {
	this.theme = Theme.fromKey("bootstrap");
    }

    /**
     * Sets the new {@link Theme}.
     * 
     * @param theme
     *            The new {@link Theme}.
     */
    public void setTheme(final Theme theme) {
	if (theme == null)
	    return;
	this.theme = theme;
    }

}
