package fr.pinguet62.croquette.bean.contact;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "suggestionManagedBean")
@ViewScoped
public final class SuggestionManagedBean extends ContactManagedBean {

    @Override
    public void send() {
	// TODO
	confirmSending();
    }

}
