package fr.pinguet62.croquette.bean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/** Used to manage connected user. */
@ManagedBean(name = LoginManagedBean.BEAN_NAME)
public final class LoginManagedBean implements Serializable {

    /** The managed bean name. */
    public static final String BEAN_NAME = "loginManagedBean";

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 3287035492567067448L;

    /**
     * Tests if user is connected.
     * 
     * @return Result.
     */
    public static boolean isConnected() {
	// TODO Implement : LoginManagedBean.isConnected()
	return true;
    }

    /** Initialization of this bean. */
    @PostConstruct
    private void init() {
	try {
	    FacesContext.getCurrentInstance().getExternalContext()
		    .redirect("http://www.google.fr");
	} catch (IOException e) {
	}
    }

}
