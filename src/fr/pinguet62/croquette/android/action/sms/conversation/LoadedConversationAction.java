package fr.pinguet62.croquette.android.action.sms.conversation;

import android.util.Log;
import fr.pinguet62.croquette.android.action.IAction;

public final class LoadedConversationAction implements IAction {

    public static final String ACTION_VALUE = "SMS_CONVERSATION_LOADED";

    private static final String LOG = LoadedConversationAction.class
            .getSimpleName();

    private final LoadedConversationDto dto;

    public LoadedConversationAction(LoadedConversationDto dto) {
        this.dto = dto;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);
        
        // TODO Send by XMPP
    }

}
