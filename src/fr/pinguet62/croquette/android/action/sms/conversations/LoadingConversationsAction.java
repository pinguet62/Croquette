package fr.pinguet62.croquette.android.action.sms.conversations;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.action.sms.MessageDto;
import fr.pinguet62.croquette.android.sms.database.Sms;
import fr.pinguet62.croquette.android.sms.database.SmsService;
import fr.pinguet62.croquette.android.sms.database.Thread;

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

        // Read Threads
        LoadingConversationsDto inDto = new Gson().fromJson(json.toString(),
                LoadingConversationsDto.class);
        List<Thread> threads = SmsService.getThreads(inDto.getCount(),
                inDto.getOldest());

        // Convert data
        LoadedConversationsDto outDto = new LoadedConversationsDto();
        outDto.setConversations(new LinkedList<ConversationDto>());
        for (Thread thread : threads) {
            ConversationDto conversationDto = new ConversationDto();
            conversationDto.setConversation(thread.getId());
            // conversationDto.setPhoneNumber(thread.get());
            conversationDto.setMessages(new LinkedList<MessageDto>());
            for (Sms sms : thread.getSmss()) { // TODO limit SMS by thread
                MessageDto messageDto = new MessageDto();
                messageDto.setContent(sms.getBody());
                messageDto.setDate(new Date(sms.getDate()));
                messageDto.setId(sms.getId());
                // TODO messageDto.setSent();
                conversationDto.getMessages().add(messageDto);
            }
            outDto.getConversations().add(conversationDto);
        }

        // Send DTO to Smartphone
        new LoadedConversationsAction(outDto).execute();
    }

}
