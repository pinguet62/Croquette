package fr.pinguet62.croquette.webapp.bean.theme;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO enum
/** Used to store informations about a theme. */
public final class Theme implements Serializable {

    /** The list of available {@link Theme}s. */
    public static final Iterable<Theme> AVAILABLES;

    private static final long serialVersionUID = 1;

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
        AVAILABLES = Collections.unmodifiableList(tmpThemes);
    }

    /**
     * Get the {@link Theme} from the key value.
     *
     * @param key
     *            The key.
     * @return The {@link Theme}.<br>
     *         {@code null} if parameter is {@code null}.
     * @throws IllegalArgumentException
     *             {@link #key} unknown.
     */
    public static Theme fromKey(String key) {
        if (key == null)
            return null;
        for (Theme theme : Theme.AVAILABLES)
            if (key.equals(theme.getKey()))
                return theme;
        throw new IllegalArgumentException("Unknown key: " + key);
    }

    /** The path of image. */
    private final String image;

    /** The unique key used by PrimeFaces. */
    private final String key;

    private final String name;

    public Theme(String key, String name, String image) {
        this.key = key;
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return key;
    }

}
