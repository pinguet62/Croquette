package fr.pinguet62.croquette.action;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.json.JsonObject;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/** Factory used to generate the corresponding {@link IAction} to execute. */
public final class ActionFactory {

    /** {@link Map} who associate the action name and {@link Class}. */
    private static final Map<String, Class<?>> ACTION_CLASS = new HashMap<>();

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
	final String actionKey = jsonMessage.getString(IAction.ACTION_KEY);
	if (actionKey == null)
	    return null;

	try {
	    Class<?> classe = ActionFactory.ACTION_CLASS.get(actionKey);
	    Constructor<?> constructor = classe
		    .getConstructor(JsonObject.class);
	    // TODO Logger
	    System.out.println("Action class: " + classe.getName());
	    IAction action = (IAction) constructor.newInstance(jsonMessage);
	    return action;
	} catch (NoSuchMethodException | SecurityException
		| InstantiationException | IllegalAccessException
		| IllegalArgumentException | InvocationTargetException e) {
	}
	// TODO Logger
	System.out.println("Action \"" + actionKey + "\" not found.");
	return null;
    }

    /** Private constructor. */
    private ActionFactory() {
    }

}
