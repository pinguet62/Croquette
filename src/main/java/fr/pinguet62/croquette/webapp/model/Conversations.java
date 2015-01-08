package fr.pinguet62.croquette.webapp.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

/**
 * Contains list of {@link Conversation}s.
 * <p>
 * There are ordered by descending date of the latest {@link Message}.
 */
public final class Conversations extends TreeSet<Conversation> {

    private static final long serialVersionUID = 1;

    /** By default {@link Conversation}s are not all loaded. */
    private boolean hasOldConversations = true;

    /**
     * {@inheritDoc}
     * <p>
     * The {@link Conversation} requires at less one {@link Message}.
     */
    @Override
    public boolean add(Conversation conversation) {
        // TODO
        // if (conversation.isEmpty())
        // return false;
        // User.get().getContacts().add(conversation.getContact());
        return super.add(conversation);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Each {@link Conversation} requires at less one {@link Message}.
     */
    @Override
    public boolean addAll(Collection<? extends Conversation> conversations) {
        boolean res = true;
        for (Conversation conversation : conversations)
            // if (conversation.isEmpty())
            // res = false;
            // else
            add(conversation); // TODO error
        return res;
    }

    /**
     * Get {@link Conversation} by id.
     *
     * @return The corresponding {@link Conversation}<br>
     *         {@code null} if not found.
     */
    public Conversation get(int id) {
        for (Conversation conversation : this)
            if (conversation.getId().equals(id))
                return conversation;

        return null;
    }

    public boolean getHasOldMessages() {
        return hasOldConversations;
    }

    public void setHasOldMessages(boolean hasOldConversations) {
        this.hasOldConversations = hasOldConversations;
    }

    /**
     * Sort {@link Conversation}s after adding {@link Message} or creating a
     * {@link Conversation}.
     */
    public void sort() {
        Collection<Conversation> tmp = new ArrayList<>(this);
        clear();
        addAll(tmp);
    }

}
