package fr.pinguet62.croquette.webapp.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * Factory used to generate the corresponding {@link IAction} to execute when an
 * JSON message is received by the web application.
 * <p>
 * Get the value of root attribute {@code "action"} to determinate the
 * corresponding implementation of {@link IAction}.
 *
 * @see SmartphoneHandler
 * @see IAction
 */
@SuppressWarnings("unchecked")
public final class ActionFactory {

    /**
     * Association of key of {@link IAction} to the corresponding {@link Class}.
     */
    private static final Map<String, Class<? extends IAction>> CLASSES = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ActionFactory.class);

    /** Initialize this factory at launch. */
    static {
        LOGGER.info("Initialization of " + IAction.class);

        String scannedPackage = "fr.pinguet62.croquette";
        LOGGER.info("Scanning package " + scannedPackage + " ...");

        // Find classes who inherit from IAction
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                true);
        provider.addIncludeFilter(new AssignableTypeFilter(IAction.class));
        Set<BeanDefinition> components = provider
                .findCandidateComponents(scannedPackage.replace(".", "/"));

        for (BeanDefinition component : components) {
            // Class
            Class<? extends IAction> findedClass = null;
            try {
                findedClass = (Class<? extends IAction>) Class
                        .forName(component.getBeanClassName());
            } catch (ClassNotFoundException e) {
                continue;
            }
            LOGGER.info("IAction: " + findedClass.getName());

            // Annotation
            SmartphoneHandler annotation = findedClass
                    .getAnnotation(SmartphoneHandler.class);
            if (annotation == null)
                continue;
            LOGGER.info("Handler for key: " + annotation.value());

            // Save
            ActionFactory.CLASSES.put(annotation.value(), findedClass);
        }
    }

    /**
     * Get the implementation of {@link IAction}.
     * <p>
     * To be found, the action class must:
     * <ul>
     * <li>implements {@link IAction};
     * <li>have {@link SmartphoneHandler} annotation;
     * <li>have a constructor with {@link String} parameter.
     * </ul>
     *
     * @param json
     *            The JSON message received by Smartphone.
     * @return The corresponding implementation of {@link IAction}.
     * @throws IllegalArgumentException
     *             Invalid argument: bag JSON format or action key unknown.
     * @throws UnsupportedOperationException
     *             Error during reflection.
     */
    public static IAction getAction(String json) {
        // Get action key
        JsonElement root;
        try {
            root = new JsonParser().parse(json);
        } catch (JsonSyntaxException exception) {
            throw new IllegalArgumentException("Bad JSON format", exception);
        }
        JsonElement actionElement = root.getAsJsonObject().get("action");
        if (actionElement == null)
            throw new IllegalArgumentException("Missing \"action\" key");
        String key = actionElement.getAsString();
        LOGGER.debug("Action key: " + key);

        Class<? extends IAction> classe = CLASSES.get(key);
        if (classe == null)
            throw new IllegalArgumentException("Unknown action: " + key);
        LOGGER.debug("Action class: " + classe);

        try {
            Constructor<?> constructor = classe.getConstructor(String.class);
            IAction action = (IAction) constructor.newInstance(json);
            return action;
        } catch (NoSuchMethodException | SecurityException
                | InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException exception) {
            LOGGER.error("Invalid action class: " + classe.getName(), exception);
            throw new UnsupportedOperationException("Invalid action class: "
                    + classe.getName(), exception);
        }
    }

    private ActionFactory() {
    }

}
