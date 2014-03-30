package fr.pinguet62.croquette.test.action;

import static org.junit.Assert.assertTrue;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Test;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.conversation.LoadedSMSAction;
import fr.pinguet62.croquette.action.sms.exchange.ReceivedSMSAction;

/** Tests for {@link ActionFactory}. */
public final class ActionFactoryTest {

    /** Test for {@link LoadedSMSAction}. */
    @Test
    public void testLoadedSMSAction() {
	JsonObject jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadedSMSAction.ACTION_VALUE).build();
	IAction action = ActionFactory.getAction(jsonMessage);
	assertTrue(action instanceof LoadedSMSAction);
    }

    /** Test for {@link ReceivedSMSAction}. */
    @Test
    public void testReceivedSMSAction() {
	JsonObject jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
		.build();
	IAction action = ActionFactory.getAction(jsonMessage);
	assertTrue(action instanceof ReceivedSMSAction);
    }

}
