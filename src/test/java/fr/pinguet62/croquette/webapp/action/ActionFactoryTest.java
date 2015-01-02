package fr.pinguet62.croquette.webapp.action;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import org.junit.Test;

import fr.pinguet62.croquette.commons.dto.LoadedConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadedConversationsDto;
import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadingConversationsDto;
import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;
import fr.pinguet62.croquette.commons.dto.SendSmsDto;
import fr.pinguet62.croquette.webapp.action.sms.conversation.LoadedSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.conversations.LoadedConversationsAction;
import fr.pinguet62.croquette.webapp.action.sms.exchange.ReceivedSMSAction;

/** @see ActionFactory */
public final class ActionFactoryTest {

    @Test
    public void test_IllegalArgumentException() {
        for (String key : Arrays.asList(SendSmsDto.KEY,
                LoadingConversationDto.KEY, LoadingConversationsDto.KEY))
            try {
                String json = "{\"action\": \"" + key + "\"}";
                ActionFactory.getAction(json);
                fail();
            } catch (IllegalArgumentException e) {
            }
    }

    @Test
    public void test_LoadedConversationsAction() {
        String json = "{\"action\": \"" + LoadedConversationsDto.KEY + "\"}";
        IAction action = ActionFactory.getAction(json);
        assertTrue(action instanceof LoadedConversationsAction);
    }

    @Test
    public void test_LoadedSMSAction() {
        String json = "{\"action\": \"" + LoadedConversationDto.KEY + "\"}";
        IAction action = ActionFactory.getAction(json);
        assertTrue(action instanceof LoadedSMSAction);
    }

    @Test
    public void test_ReceivedSMSAction() {
        String json = "{\"action\": \"" + ReceivedSmsDto.KEY + "\"}";
        IAction action = ActionFactory.getAction(json);
        assertTrue(action instanceof ReceivedSMSAction);
    }
}
