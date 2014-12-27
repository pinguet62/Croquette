package fr.pinguet62.croquette.android.action.sms.conversation;

import java.util.LinkedList;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.action.sms.MessageDto;

public final class LoadingConversationAction implements IAction {

    public static final String ACTION_VALUE = "SMS_CONVERSATION_LOADING";

    private static final String LOG = LoadingConversationAction.class
            .getSimpleName();

    private final String json;

    public LoadingConversationAction(String json) {
        this.json = json;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        // Action 1: Read SMS
        LoadingConversationDto inDto = new Gson().fromJson(json.toString(),
                LoadingConversationDto.class);
        // TODO Read SMS

        LoadedConversationDto outDto = new LoadedConversationDto();
        outDto.setConversation(inDto.getId());
        outDto.setMessages(new LinkedList<MessageDto>());
        // outDto.getMessages().add(); TODO add messages

        // Action 2: Send conversation
        new LoadedConversationAction(outDto).execute();
    }

}
