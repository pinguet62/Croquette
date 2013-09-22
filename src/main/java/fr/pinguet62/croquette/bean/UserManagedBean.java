package fr.pinguet62.croquette.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import fr.pinguet62.croquette.Session;
import fr.pinguet62.croquette.model.User;

/** Used to manage the user. */
@ManagedBean(name = "userManagedBean")
@ViewScoped
public class UserManagedBean implements Serializable {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 8906413415885416210L;

    /**
     * Gets the {@link User}.
     * 
     * @return The {@link User}.
     */
    public User getUser() {
	return Session.getUser();
    }

    /** Call when user wants to disconnect. */
    public void logout() {
	Session.reset();
    }
}
