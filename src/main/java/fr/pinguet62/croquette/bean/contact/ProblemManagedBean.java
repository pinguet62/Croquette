package fr.pinguet62.croquette.bean.contact;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "problemManagedBean")
@ViewScoped
public final class ProblemManagedBean extends ContactManagedBean {

    @Override
    public void send() {
	// TODO
	confirmSending();
    }

}
