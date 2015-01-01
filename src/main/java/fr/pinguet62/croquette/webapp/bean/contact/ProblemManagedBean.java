package fr.pinguet62.croquette.webapp.bean.contact;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "problemManagedBean")
@ViewScoped
public final class ProblemManagedBean extends ContactManagedBean {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    @Override
    public void send() {
        // TODO contact > problem
        confirmSending();
    }

}
