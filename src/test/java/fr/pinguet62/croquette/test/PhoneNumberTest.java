package fr.pinguet62.croquette.test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

import org.junit.Assert;
import org.junit.Test;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;

import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.conversation.LoadedSMSAction;
import fr.pinguet62.croquette.action.sms.conversation.LoadingSMSAction;
import fr.pinguet62.croquette.model.PhoneNumber;

/** Test for phone numbers of Google i18 API. */
public final class PhoneNumberTest {

    @Test
    public void equals() {
	PhoneNumber test = new PhoneNumber("0647618122");

	Assert.assertFalse(test.equals(null));
	Assert.assertFalse(test.equals(new PhoneNumber(null)));
	Assert.assertFalse(new PhoneNumber(null).equals(test));
	// Assert.assertFalse(new PhoneNumber(null).equals(new
	// PhoneNumber(null)));
	Assert.assertTrue(test.equals(test));

	// Separator
	Assert.assertTrue(new PhoneNumber("06 47 61 81 22").equals(test));
	Assert.assertTrue(new PhoneNumber("06.47.61.81.22").equals(test));
	Assert.assertTrue(new PhoneNumber("06-47-61-81-22").equals(test));

	// Country calling codes
	Assert.assertTrue(new PhoneNumber("+211647618122").equals(test));
	Assert.assertTrue(new PhoneNumber("+33647618122").equals(test));
	Assert.assertTrue(new PhoneNumber("(+33)647618122").equals(test));
    }

    @Test
    public void testLib() throws NumberParseException {
	PhoneNumberUtil util = PhoneNumberUtil.getInstance();

	util.parse("+33647618122", null);
	Assert.assertEquals("+33647618122", util.format(
		util.parse("+33647618122", null), PhoneNumberFormat.E164));

	int conversationId = (int) (Math.random() * 25);
	JsonArrayBuilder jsonMessages = Json.createArrayBuilder();
	for (int i = 0; i < LoadingSMSAction.COUNT_VALUE; i++) {
	    int messageId = (int) (Math.random() * 100);
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.DAY_OF_YEAR, -(int) (Math.random() * 365));
	    System.out.println(calendar.getTime());
	    jsonMessages.add(Json
		    .createObjectBuilder()
		    .add(LoadedSMSAction.MESSAGE_ID, messageId)
		    .add(LoadedSMSAction.MESSAGE_SENT,
			    (Math.random() < 0.5 ? Boolean.TRUE.toString()
				    : Boolean.FALSE.toString()))
		    .add(LoadedSMSAction.MESSAGE_DATE,
			    new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss")
				    .format(new Date()))
		    .add(LoadedSMSAction.MESSAGE_CONTENT,
			    String.format("Conversation %d, message %d",
				    conversationId, messageId)));
	}
	String jsonMessage = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, IAction.ACTION_KEY)
		.add(LoadedSMSAction.CONVERSATION_ID, conversationId)
		.add(LoadedSMSAction.MESSAGES, jsonMessages).build().toString();
	System.out.println(jsonMessage);
    }
}
