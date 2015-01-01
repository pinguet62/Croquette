package fr.pinguet62.croquette.webapp.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

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
    public static IAction getAction(JsonObject jsonMessage) {
        String actionName;
        try {
            actionName = jsonMessage.getString(IAction.ACTION_KEY);
        } catch (NullPointerException exception) {
            LOGGER.error("Action key not found: " + IAction.ACTION_KEY);
            return null;
        }

        Class<?> classe = ActionFactory.ACTION_CLASS.get(actionName);
        if (classe == null) {
            LOGGER.error("Action value not found: " + actionName);
            return null;
        }

        try {
            Constructor<?> constructor = classe
                    .getConstructor(JsonObject.class);
            LOGGER.info("Action class: " + classe.getName());
            IAction action = (IAction) constructor.newInstance(jsonMessage);
            return action;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException exception) {
            LOGGER.error("Invalid action class: " + classe.getName(), exception);
            return null;
        }
    }

    /** Private constructor. */
    private ActionFactory() {
    }

}
