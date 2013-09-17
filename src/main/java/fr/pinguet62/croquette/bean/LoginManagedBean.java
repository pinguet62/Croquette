package fr.pinguet62.croquette.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

/**
 * Used to manage connected user.
 * 
 * @author Pinguet62
 */
@ManagedBean(name = LoginManagedBean.BEAN_NAME)
public final class LoginManagedBean implements Serializable {

    /** The managed bean name. */
    public static final String BEAN_NAME = "loginManagedBean";

    /** Auto generated UID. */
    private static final long serialVersionUID = 3287035492567067448L;

    /**
     * Tests if user is connected.
     * 
     * @return Result.
     * @author Pinguet62
     */
    public static boolean isConnected() {
	// TODO Implement : LoginManagedBean.isConnected()
	return true;
    }

}
