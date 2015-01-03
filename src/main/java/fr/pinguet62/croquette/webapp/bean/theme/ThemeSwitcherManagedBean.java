package fr.pinguet62.croquette.webapp.bean.theme;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Used to switch of PrimeFaces {@link Theme}. */
@ManagedBean(name = "themeSwitcherManagedBean")
@SessionScoped
public final class ThemeSwitcherManagedBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** The current {@link Theme}. */
    private Theme theme = Theme.fromKey("dark-hive");

    /**
     * Gets the current {@link Theme}.
     *
     * @return The current {@link Theme}.
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Gets the list of available {@link Theme}s.
     *
     * @return The {@link Theme}s.
     */
    public Iterable<Theme> getThemes() {
        return Theme.AVAILABLES;
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
