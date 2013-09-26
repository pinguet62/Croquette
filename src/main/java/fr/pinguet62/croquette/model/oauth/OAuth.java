package fr.pinguet62.croquette.model.oauth;

public final class OAuth {

    /** The token. */
    private String token = null;

    /**
     * Constructor.
     * 
     * @param token
     *            The token.
     */
    public OAuth(final String token) {
	this.token = token;
    }

    /**
     * Gets the token.
     * 
     * @return The token.
     */
    public String getToken() {
	return this.token;
    }

    /**
     * Sets the token.
     * 
     * @param token
     *            The token to set.
     */
    public void setToken(final String token) {
	this.token = token;
    }

}
