package fr.pinguet62.croquette.android.action.sms.conversations;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.xmpp.XMPPManager;

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

        String json = new Gson().toJson(dto);
        XMPPManager.getInstance().send(json);
    }

}
