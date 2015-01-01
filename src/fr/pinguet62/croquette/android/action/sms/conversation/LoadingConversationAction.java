package fr.pinguet62.croquette.android.action.sms.conversation;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.sms.database.Sms;
import fr.pinguet62.croquette.android.sms.database.SmsService;
import fr.pinguet62.croquette.commons.dto.LoadedConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.commons.dto.MessageDto;

/** @see LoadingConversationDto */
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

        // Read SMSs
        LoadingConversationDto inDto = new Gson().fromJson(json.toString(),
                LoadingConversationDto.class);
        List<Sms> smss = SmsService.getByThread(inDto.getId(),
                inDto.getCount(), inDto.getOldest());

        // Convert data
        LoadedConversationDto outDto = new LoadedConversationDto();
        outDto.setConversation(inDto.getId());
        outDto.setMessages(new LinkedList<MessageDto>());
        for (Sms sms : smss) {
            MessageDto messageDto = new MessageDto();
            messageDto.setContent(sms.getBody());
            messageDto.setDate(new Date(sms.getDate()));
            messageDto.setId(sms.getId());
            // TODO messageDto.setSent();
            outDto.getMessages().add(messageDto);
        }

        // Send DTO to WebApp
        new LoadedConversationAction(outDto).execute();
    }

}
