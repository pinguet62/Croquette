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

import fr.pinguet62.croquette.commons.action.BroadcastException;
import fr.pinguet62.croquette.commons.action.IAction;

/**
 * Factory used to generate the corresponding {@link IAction} from the JSON
 * message received by Smartphone.
 *
 * @see IAction
 * @see SmartphoneHandler
 * @see BroadcastIgnored
 */
@SuppressWarnings("unchecked")
public final class ActionFactory {

    /**
     * {@link IAction} annotation with {@link BroadcastIgnored}.
     * <p>
     * <ul>
     * <li>Key: key of {@link IAction}</li>
     * <li>Value: {@link Class} of {@link IAction}</li>
     * </ul>
     */
    private static final Map<String, Class<? extends IAction>> BROADCAST = new HashMap<>();

    /**
     * {@link IAction} annotation with {@link SmartphoneHandler}.
     * <p>
     * <ul>
     * <li>Key: key of {@link IAction}</li>
     * <li>Value: {@link Class} of {@link IAction}</li>
     * </ul>
     */
    private static final Map<String, Class<? extends IAction>> HANDLERS = new HashMap<>();

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ActionFactory.class);

    /** Initialize this factory at launch, by scanning the base package. */
    static {
        LOGGER.info("Initialization of " + IAction.class);

        String scannedPackage = "fr.pinguet62.croquette.webapp";
        LOGGER.info("Scanning package " + scannedPackage + " ...");

        // Find classes who inherit from IAction
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false);
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
            SmartphoneHandler handler = findedClass
                    .getAnnotation(SmartphoneHandler.class);
            BroadcastIgnored broadcast = findedClass
                    .getAnnotation(BroadcastIgnored.class);
            if (handler != null && broadcast != null) {
                LOGGER.error("The " + IAction.class
                        + " class cannot be annotated with the 2 annotations.");
                continue;
            }
            // - handler
            if (handler != null) {
                LOGGER.info("Handler action key: " + handler.value());
                ActionFactory.HANDLERS.put(handler.value(), findedClass);
            }
            // - broadcast
            if (broadcast != null) {
                LOGGER.info("Broadcast action key: " + broadcast.value());
                ActionFactory.BROADCAST.put(broadcast.value(), findedClass);
            }
        }
    }

    /**
     * Get the implementation of {@link IAction}.
     *
     * @param json
     *            The JSON message received from Smartphone.
     * @return The corresponding implementation of {@link IAction}.
     * @throws IllegalArgumentException
     *             Invalid argument: bag JSON format or action key unknown.
     * @throws UnsupportedOperationException
     *             Error during reflection.
     * @throws BroadcastException
     *             It's a broadcast message.
     * @see SmartphoneHandler
     * @see BroadcastIgnored
     */
    public static IAction getAction(String json) throws BroadcastException {
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

        // Broadcast
        if (BROADCAST.containsKey(key))
            throw new BroadcastException("Action ignored: " + key);

        Class<? extends IAction> classe = HANDLERS.get(key);
        if (classe == null)
            throw new IllegalArgumentException("Unknown action: " + key);
        LOGGER.debug("Action class: " + classe);

        // Handler
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
