package fr.pinguet62.croquette.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;

/** Tests for {@link Conversation} manipulation. */
public final class ConversationTest {

    @Test
    public void addExisting() {
	// TODO
    }

    /** Insert latest {@link Message} into {@link Conversation}. */
    @Test
    public void addNew() {
	Conversation conversation = new Conversation();
	assertEquals(0, conversation.size());

	Message mOldest = new Message();
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_YEAR, -1);
	mOldest.setDate(calendar.getTime());
	Integer mOldestId = 2;
	mOldest.setId(mOldestId);
	boolean res1 = conversation.add(mOldest);
	assertTrue(res1);
	assertEquals(1, conversation.size());

	Message mLatest = new Message();
	mLatest.setDate(new Date());
	Integer mLatestId = 1;
	mLatest.setId(mLatestId);
	boolean res2 = conversation.add(mLatest);
	assertTrue(res2);
	assertEquals(2, conversation.size());

	assertEquals(mLatestId, conversation.first().getId());
	assertEquals(mOldestId, conversation.last().getId());
    }

    /** Insert oldest {@link Message} into {@link Conversation}. */
    @Test
    public void addOld() {
	Conversation conversation = new Conversation();
	assertEquals(0, conversation.size());

	Message mLatest = new Message();
	mLatest.setDate(new Date());
	Integer mLatestId = 1;
	mLatest.setId(mLatestId);
	boolean res1 = conversation.add(mLatest);
	assertTrue(res1);
	assertEquals(1, conversation.size());

	Message mOldest = new Message();
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_YEAR, -1);
	mOldest.setDate(calendar.getTime());
	Integer mOldestId = 2;
	mOldest.setId(mOldestId);
	boolean res2 = conversation.add(mOldest);
	assertTrue(res2);
	assertEquals(2, conversation.size());

	assertEquals(mLatestId, conversation.first().getId());
	assertEquals(mOldestId, conversation.last().getId());
    }

}
