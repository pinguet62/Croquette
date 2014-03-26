package fr.pinguet62.croquette.bean.theme;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Used to switch of PrimeFaces theme. */
@ManagedBean(name = "themeSwitcherManagedBean")
@SessionScoped
public final class ThemeSwitcherManagedBean {

    /** The current {@link Theme}. */
    private Theme theme;

    /**
     * Gets the current {@link Theme}.
     *
     * @return The current {@link Theme}.
     */
    public Theme getTheme() {
	return theme;
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
	theme = Theme.fromKey("dark-hive"); // TODO delete
    }

    /**
     * Sets the new {@link Theme}.
     *
     * @param theme
     *            The new {@link Theme}.
     */
    public void setTheme(Theme theme) {
	if (theme == null)
	    return;
	this.theme = theme;
    }

}
