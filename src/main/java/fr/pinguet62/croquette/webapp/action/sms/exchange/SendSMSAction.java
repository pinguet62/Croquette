package fr.pinguet62.croquette.webapp.action.sms.exchange;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.SendSmsDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/** Send a SMS. */
@Action(SendSmsDto.KEY)
public final class SendSMSAction implements IAction {

    /** The {@link Message} to send. */
    private Message message = null;

    /**
     * Constructor with {@link Message}.
     *
     * @param message
     *            The message to send.
     */
    public SendSMSAction(Message message) {
        this.message = message;
    }

    /**
     * <ul>
     * <li>Convert {@link Message} to {@link SendSmsDto}</li>
     * <li>Send DTO to Smartphone</li>
     * </ul>
     */
    @Override
    public void execute() {
        // Convert data
        SendSmsDto dto = new SendSmsDto();
        dto.setContent(message.getContent());
        dto.setConversation(message.getConversation().getId());
        dto.setPhoneNumber(message.getConversation().getContact()
                .getPhoneNumber().toString());

        // Send DTO to Smartphone
        String json = new Gson().toJson(dto);
        User.get().getXmppManager().send(json);
    }

    @Override
    public boolean fromSmartphone() {
        return false;
    }

}
