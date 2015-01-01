package fr.pinguet62.croquette.android.action.sms.conversation;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.xmpp.XMPPManager;
import fr.pinguet62.croquette.commons.dto.LoadedConversationDto;

/** @see LoadedConversationDto */
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

        String json = new Gson().toJson(dto);
        XMPPManager.getInstance().send(json);
    }

}
