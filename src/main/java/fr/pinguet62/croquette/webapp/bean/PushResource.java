package fr.pinguet62.croquette.webapp.bean;

import org.primefaces.push.annotation.OnMessage;
import org.primefaces.push.annotation.PushEndpoint;
import org.primefaces.push.annotation.Singleton;

@Singleton
@PushEndpoint(PushResource.CHANNEL)
public final class PushResource {

    /** The channel ID. */
    public static final String CHANNEL = "/channel";

    @OnMessage()
    public String onMessage(String a) {
        System.out.println("Message");
        return a;
    }

}
