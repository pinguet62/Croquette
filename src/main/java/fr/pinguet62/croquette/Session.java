package fr.pinguet62.croquette;

import java.util.Map;

import javax.faces.context.FacesContext;

import fr.pinguet62.croquette.model.User;

/** Gets & sets informations into session map. */
public final class Session {

    /** Key for connected user */
    public static final String USER = "user";

    /**
     * Gets session map.
     * 
     * @return The session map.
     */
    public static Map<String, Object> getMap() {
	return FacesContext.getCurrentInstance().getExternalContext()
		.getSessionMap();
    }

    /**
     * Get the {@link User}.
     * 
     * @return The {@link User}.
     */
    public static User getUser() {
	return (User) Session.getMap().get(Session.USER);
    }

}
