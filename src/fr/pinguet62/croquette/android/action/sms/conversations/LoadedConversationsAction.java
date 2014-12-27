package fr.pinguet62.croquette.android.action.sms.conversations;

import android.util.Log;
import fr.pinguet62.croquette.android.action.IAction;

public final class LoadedConversationsAction implements IAction {

    public static final String ACTION_VALUE = "SMS_CONVERSATIONS_LOADED";

    private static final String LOG = LoadedConversationsAction.class
            .getSimpleName();

    private final LoadedConversationsDto dto;

    public LoadedConversationsAction(LoadedConversationsDto dto) {
        this.dto = dto;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        // TODO Send by XMPP
    }

}
