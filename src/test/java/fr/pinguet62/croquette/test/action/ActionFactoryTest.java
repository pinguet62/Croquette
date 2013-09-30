package fr.pinguet62.croquette.test.action;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Assert;
import org.junit.Test;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;

/** Tests for {@link ActionFactory}. */
public final class ActionFactoryTest {

    /** Test for {@link ReceivedSMSAction}. */
    @Test
    public void test() {
	JsonObject jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
		.build();
	IAction action = ActionFactory.getAction(jsonMessage);
	Assert.assertTrue(action instanceof ReceivedSMSAction);
    }

}
