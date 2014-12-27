package fr.pinguet62.croquette.android.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

public final class MessageListenerImpl implements MessageListener {

    @Override
    public void processMessage(Chat chat, Message message) {
        Log.i(getClass().getSimpleName(),
                "Message received: " + message.getBody());
    }

}
