package fr.pinguet62.croquette.test.action;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.json.Json;
import javax.json.JsonObject;

import org.junit.Assert;
import org.junit.Test;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.LoadedSMSAction;
import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;

/** Tests for {@link ActionFactory}. */
public final class ActionFactoryTest {

    /** Test for <code>getAllClasses()</code>. */
    @Test
    public void getAllClasses() throws Exception {
	Method method = ActionFactory.class.getDeclaredMethod("getAllClasses",
		String.class);
	method.setAccessible(true);
	@SuppressWarnings("unchecked")
	Collection<Class<?>> classes = (Collection<Class<?>>) method.invoke(
		null, "fr.pinguet62.croquette");
	// Assert.assertTrue(classes.contains(Action.class));
	// Assert.assertTrue(classes.contains(ActionFactory.class));
	// Assert.assertTrue(classes.contains(IAction.class));
	Assert.assertTrue(classes.contains(ActionFactoryTest.class));
    }

    /** Test for {@link LoadedSMSAction}. */
    @Test
    public void testLoadedSMSAction() {
	JsonObject jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadedSMSAction.ACTION_VALUE).build();
	IAction action = ActionFactory.getAction(jsonMessage);
	Assert.assertTrue(action instanceof LoadedSMSAction);
    }

    /** Test for {@link ReceivedSMSAction}. */
    @Test
    public void testReceivedSMSAction() {
	JsonObject jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
		.build();
	IAction action = ActionFactory.getAction(jsonMessage);
	Assert.assertTrue(action instanceof ReceivedSMSAction);
    }

}
