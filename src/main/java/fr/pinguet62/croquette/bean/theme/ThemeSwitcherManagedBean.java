package fr.pinguet62.croquette.bean.theme;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Used to switch of PrimeFaces theme. */
@ManagedBean(name = "themeSwitcherManagedBean")
@SessionScoped
public class ThemeSwitcherManagedBean {

    /** The list of {@link Theme}s. */
    private static final Iterable<Theme> themes;
    static {
	List<Theme> tmpThemes = new ArrayList<Theme>();
	tmpThemes.add(new Theme("afterdark", "Afterdark", "afterdark.png"));
	tmpThemes.add(new Theme("afternoon", "Afternoon", "afternoon.png"));
	tmpThemes.add(new Theme("afterwork", "Afterwork", "afterwork.png"));
	tmpThemes.add(new Theme("aristo", "Aristo", "aristo.png"));
	tmpThemes.add(new Theme("black-tie", "Black-Tie", "black-tie.png"));
	tmpThemes.add(new Theme("blitzer", "Blitzer", "blitzer.png"));
	tmpThemes.add(new Theme("bluesky", "Bluesky", "bluesky.png"));
	tmpThemes.add(new Theme("bootstrap", "Bootstrap", "bootstrap.png"));
	tmpThemes.add(new Theme("casablanca", "Casablanca", "casablanca.png"));
	tmpThemes.add(new Theme("cruze", "Cruze", "cruze.png"));
	tmpThemes.add(new Theme("cupertino", "Cupertino", "cupertino.png"));
	tmpThemes.add(new Theme("delta", "Delta", "delta.png"));
	tmpThemes.add(new Theme("dark-hive", "Dark-Hive", "dark-hive.png"));
	tmpThemes.add(new Theme("dot-luv", "Dot-Luv", "dot-luv.png"));
	tmpThemes.add(new Theme("eggplant", "Eggplant", "eggplant.png"));
	tmpThemes
		.add(new Theme("excite-bike", "Excite-Bike", "excite-bike.png"));
	tmpThemes.add(new Theme("flick", "Flick", "flick.png"));
	tmpThemes.add(new Theme("glass-x", "Glass-X", "glass-x.png"));
	tmpThemes.add(new Theme("home", "Home", "home.png"));
	tmpThemes.add(new Theme("hot-sneaks", "Hot-Sneaks", "hot-sneaks.png"));
	tmpThemes.add(new Theme("humanity", "Humanity", "humanity.png"));
	tmpThemes.add(new Theme("le-frog", "Le-Frog", "le-frog.png"));
	tmpThemes.add(new Theme("midnight", "Midnight", "midnight.png"));
	tmpThemes.add(new Theme("mint-choc", "Mint-Choc", "mint-choc.png"));
	tmpThemes.add(new Theme("overcast", "Overcast", "overcast.png"));
	tmpThemes.add(new Theme("pepper-grinder", "Pepper-Grinder",
		"pepper-grinder.png"));
	tmpThemes.add(new Theme("redmond", "Redmond", "redmond.png"));
	tmpThemes.add(new Theme("rocket", "Rocket", "rocket.png"));
	tmpThemes.add(new Theme("sam", "Sam", "sam.png"));
	tmpThemes.add(new Theme("smoothness", "Smoothness", "smoothness.png"));
	tmpThemes.add(new Theme("south-street", "South-Street",
		"south-street.png"));
	tmpThemes.add(new Theme("start", "Start", "start.png"));
	tmpThemes.add(new Theme("sunny", "Sunny", "sunny.png"));
	tmpThemes.add(new Theme("swanky-purse", "Swanky-Purse",
		"swanky-purse.png"));
	tmpThemes.add(new Theme("trontastic", "Trontastic", "trontastic.png"));
	tmpThemes
		.add(new Theme("ui-darkness", "UI-Darkness", "ui-darkness.png"));
	tmpThemes.add(new Theme("ui-lightness", "UI-Lightness",
		"ui-lightness.png"));
	tmpThemes.add(new Theme("vader", "Vader", "vader.png"));
	themes = Collections.unmodifiableList(tmpThemes);
    }

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
	return ThemeSwitcherManagedBean.themes;
    }

    /** Initialization of this bean. */
    @PostConstruct
    private void init() {
	for (Theme theme : ThemeSwitcherManagedBean.themes)
	    if ("bootstrap".equals(theme.getKey()))
		this.theme = theme;
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
