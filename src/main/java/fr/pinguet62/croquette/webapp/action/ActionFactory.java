package fr.pinguet62.croquette.webapp.action;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;
import fr.pinguet62.croquette.commons.dto.SendSmsDto;
import fr.pinguet62.croquette.webapp.action.sms.exchange.ReceivedSMSAction;

/** Factory used to generate the corresponding {@link IAction} to execute. */
public final class ActionFactory {

    /** {@link Map} who associate the action name and {@link Class}. */
    private static final Map<String, Class<?>> ACTION_CLASS = new HashMap<>();

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(ActionFactory.class);

    /** Initialize this factory at the beginning of application. */
    static {
        // Find classes who inherit from IAction
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                true);
        provider.addIncludeFilter(new AssignableTypeFilter(IAction.class));
        Set<BeanDefinition> components = provider
                .findCandidateComponents("fr.pinguet62.croquette.action"
                        .replace(".", "/"));

        for (BeanDefinition component : components) {
            Class<?> findedClass = null;
            try {
                findedClass = Class.forName(component.getBeanClassName());
            } catch (ClassNotFoundException e) {
                continue;
            }

            Action annotation = findedClass.getAnnotation(Action.class);
            if (annotation == null)
                continue;

            ActionFactory.ACTION_CLASS.put(annotation.value(), findedClass);
        }
    }

    /**
     * Get the {@link Action}.
     * <p>
     * To be found, the action class must:
     * <ul>
     * <li>implements {@link IAction};
     * <li>have {@link Action} annotation;
     * <li>have a constructor with {@link JsonObject} attribute.
     * </ul>
     *
     * @param jsonMessage
     *            The JSON message.
     * @return The {@link Action}.
     */
    public static IAction getAction(String json) {
        // Get action key
        JsonElement root;
        try {
            root = new JsonParser().parse(json);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Bad JSON format", e);
        }
        JsonElement actionElement = root.getAsJsonObject().get("action");
        if (actionElement == null)
            throw new IllegalArgumentException("Missing \"action\" key");
        String action = actionElement.getAsString();

        // Factory
        if (SendSmsDto.KEY.equals(action))
            return null;
        else if (ReceivedSmsDto.KEY.equals(action))
            return new ReceivedSMSAction(json);
        // else if (LoadingConversationAction.ACTION_VALUE.equals(action))
        // return new LoadingConversationAction(json);
        // else if (LoadedConversationAction.ACTION_VALUE.equals(action))
        // return null;
        else
            throw new UnsupportedOperationException("Unknown action");

        // try {
        // Constructor<?> constructor = classe
        // .getConstructor(JsonObject.class);
        // LOGGER.info("Action class: " + classe.getName());
        // IAction action = (IAction) constructor.newInstance(jsonMessage);
        // return action;
        // } catch (NoSuchMethodException | SecurityException
        // | InstantiationException | IllegalAccessException
        // | IllegalArgumentException | InvocationTargetException exception) {
        // LOGGER.error("Invalid action class: " + classe.getName(), exception);
        // return null;
        // }
    }

    /** Private constructor. */
    private ActionFactory() {
    }

}
