package fr.pinguet62.croquette.android.action.sms.conversations;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.action.sms.conversation.LoadingConversationAction;

public final class LoadingConversationsAction implements IAction {

    public static final String ACTION_VALUE = "SMS_CONVERSATIONS_LOADING";

    private static final String LOG = LoadingConversationsAction.class
            .getSimpleName();

    private final String json;

    public LoadingConversationsAction(String json) {
        this.json = json;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        // Action 1: Read SMS
        LoadingConversationsDto inDto = new Gson().fromJson(json.toString(),
                LoadingConversationsDto.class);
        // TODO Read conversations

        LoadedConversationsDto outDto = new LoadedConversationsDto();
        // outDto.getMessages().add(); TODO add conversations

        // Action 2: Send conversation
        new LoadedConversationsAction(outDto).execute();
    }

}
