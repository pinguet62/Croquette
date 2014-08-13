package fr.pinguet62.croquette.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Conversations;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.test.DateUtil;

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

    /** Test for {@link Conversations#sort()}. */
    @Test
    public void sort() {
        Conversations conversations = new Conversations();

        // New conversation
        Conversation conversation1 = new Conversation(1, null);
        conversation1.add(new Message(11, DateUtil.generateDate(2014, 0, 1),
                "11"));
        conversations.add(conversation1);
        assertEquals(1, conversations.first().getId().intValue());
        assertEquals(1, conversations.last().getId().intValue());
        // New conversation
        Conversation conversation2 = new Conversation(2, null);
        conversation2.add(new Message(21, DateUtil.generateDate(2014, 0, 2),
                "21"));
        conversations.add(conversation2);
        assertEquals(2, conversations.first().getId().intValue());
        assertEquals(1, conversations.last().getId().intValue());

        // Update existing conversation
        conversation1.add(new Message(12, DateUtil.generateDate(2014, 0, 3),
                "12"));
        conversations.sort();
        assertEquals(1, conversations.first().getId().intValue());
        assertEquals(2, conversations.last().getId().intValue());
        // Update existing conversation
        conversation2.add(new Message(22, DateUtil.generateDate(2014, 0, 4),
                "22"));
        conversations.sort();
        assertEquals(2, conversations.first().getId().intValue());
        assertEquals(1, conversations.last().getId().intValue());
    }

}
