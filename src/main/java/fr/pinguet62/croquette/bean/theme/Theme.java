package fr.pinguet62.croquette.bean.theme;

/** Used to store informations about a theme. */
public final class Theme {

    /** The path of image. */
    private String image;

    /** The key used by PrimeFaces. */
    private String key;

    /** The name. */
    private String name;

    /**
     * Constructor.
     * 
     * @param key
     *            The key.
     * @param name
     *            The value.
     * @param image
     *            The image.
     */
    public Theme(final String key, final String name, final String image) {
	this.key = key;
	this.name = name;
	this.image = image;
    }

    /**
     * Gets the image.
     * 
     * @return The image.
     */
    public String getImage() {
	return this.image;
    }

    /**
     * Gets the key.
     * 
     * @return The key.
     */
    public String getKey() {
	return this.key;
    }

    /**
     * Gets the name.
     * 
     * @return The name.
     */
    public String getName() {
	return this.name;
    }

    /**
     * Sets the image.
     * 
     * @param image
     *            The image to set.
     */
    public void setImage(final String image) {
	this.image = image;
    }

    /**
     * Sets key.
     * 
     * @param key
     *            The key to set.
     */
    public void setKey(final String key) {
	this.key = key;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Return the {@link String} representation of this current {@link Theme}. <br >>
     * Used by the <code>ThemeSwitcher</code> to get the new key.
     * 
     * @return The {@link String} representation.
     */
    @Override
    public String toString() {
	return this.key;
    }

}
