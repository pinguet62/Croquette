package fr.pinguet62.croquette.webapp.action.sms.exchange;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.SendSmsDto;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Send a SMS.
 *
 * @see ReceivedSMSAction
 * @see SendSmsDto
 */
public final class SendSMSAction implements IAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SendSMSAction.class);

    private final Message message;

    public SendSMSAction(Message message) {
        this.message = message;
    }

    /**
     * <ul>
     * <li>Convert {@link Message} to {@link SendSmsDto}</li>
     * <li>Send {@link SendSmsDto DTO} to Smartphone</li>
     * </ul>
     */
    @Override
    public void execute() {
        LOGGER.info("Action: " + SendSmsDto.KEY);

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

}
