package fr.pinguet62.croquette.webapp.bean.theme;

import java.io.Serializable;
import java.util.Objects;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Used to switch of PrimeFaces {@link Theme}. */
@ManagedBean(name = "themeSwitcherManagedBean")
@SessionScoped
public final class ThemeSwitcherManagedBean implements Serializable {

    private static final long serialVersionUID = 1;

    /** The current {@link Theme}. */
    private Theme theme = Theme.fromKey("dark-hive");

    /** Get the current {@link Theme}. */
    public Theme getTheme() {
        return theme;
    }

    /** @return {@link Theme#AVAILABLES} */
    public Iterable<Theme> getThemes() {
        return Theme.AVAILABLES;
    }

    /**
     * Set the new current {@link Theme}.
     *
     * @throws NullPointerException
     *             If the parameter is {@code null}.
     */
    public void setTheme(Theme theme) {
        Objects.requireNonNull(theme);
        this.theme = theme;
    }

}
