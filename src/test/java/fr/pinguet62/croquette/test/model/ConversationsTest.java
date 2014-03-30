package fr.pinguet62.croquette.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Conversations;
import fr.pinguet62.croquette.model.Message;

/** Tests for {@link Conversations} manipulation. */
public final class ConversationsTest {

    /** Insert latest {@link Conversation} into {@link Conversations}. */
    @Test
    public void addNew() {
	Conversations conversations = new Conversations();
	assertEquals(0, conversations.size());

	Conversation cOldest = new Conversation();
	Integer cOldestId = 2;
	cOldest.setId(cOldestId);
	Message mOldest = new Message();
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_YEAR, -1);
	mOldest.setDate(calendar.getTime());
	cOldest.add(mOldest);
	boolean res1 = conversations.add(cOldest);
	assertTrue(res1);
	assertEquals(1, conversations.size());

	Conversation cLatest = new Conversation();
	Integer cLatestId = 1;
	cLatest.setId(cLatestId);
	Message mLatest = new Message();
	mLatest.setDate(new Date());
	cLatest.add(mLatest);
	boolean res2 = conversations.add(cLatest);
	assertTrue(res2);
	assertEquals(2, conversations.size());

	assertEquals(cLatestId, conversations.first().getId());
	assertEquals(cOldestId, conversations.last().getId());
    }

    /** Insert oldest {@link Conversation} into {@link Conversations}. */
    @Test
    public void addOld() {
	Conversations conversations = new Conversations();
	assertEquals(0, conversations.size());

	Conversation cLatest = new Conversation();
	Integer cLatestId = 1;
	cLatest.setId(cLatestId);
	Message mLatest = new Message();
	mLatest.setDate(new Date());
	cLatest.add(mLatest);
	boolean res1 = conversations.add(cLatest);
	assertTrue(res1);
	assertEquals(1, conversations.size());

	Conversation cOldest = new Conversation();
	Integer cOldestId = 2;
	cOldest.setId(cOldestId);
	Message mOldest = new Message();
	Calendar calendar = Calendar.getInstance();
	calendar.add(Calendar.DAY_OF_YEAR, -1);
	mOldest.setDate(calendar.getTime());
	cOldest.add(mOldest);
	boolean res2 = conversations.add(cOldest);
	assertTrue(res2);
	assertEquals(2, conversations.size());

	assertEquals(cLatestId, conversations.first().getId());
	assertEquals(cOldestId, conversations.last().getId());
    }

}
