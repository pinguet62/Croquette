package fr.pinguet62.croquette.test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.test.DateUtil;

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

        Message mOldest = new Message(2, DateUtil.generateDate(2014, 0, 2), "2");
        assertTrue(conversation.add(mOldest));
        assertEquals(1, conversation.size());

        Message mLatest = new Message(1, DateUtil.generateDate(2014, 0, 1), "1");
        assertTrue(conversation.add(mLatest));
        assertEquals(2, conversation.size());

        assertEquals(1, conversation.first().getId().intValue());
        assertEquals(2, conversation.last().getId().intValue());
    }

    /** Insert oldest {@link Message} into {@link Conversation}. */
    @Test
    public void addOld() {
        Conversation conversation = new Conversation();
        assertEquals(0, conversation.size());

        Message mLatest = new Message(1, DateUtil.generateDate(2014, 0, 1), "1");
        assertTrue(conversation.add(mLatest));
        assertEquals(1, conversation.size());

        Message mOldest = new Message(2, DateUtil.generateDate(2014, 0, 2), "2");
        assertTrue(conversation.add(mOldest));
        assertEquals(2, conversation.size());

        assertEquals(1, conversation.first().getId().intValue());
        assertEquals(2, conversation.last().getId().intValue());
    }

}
