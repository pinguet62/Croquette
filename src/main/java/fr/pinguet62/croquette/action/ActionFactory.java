package fr.pinguet62.croquette.action;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;

import javax.json.JsonObject;

/** Factory used to generate the corresponding {@link IAction} to execute. */
public final class ActionFactory {

    /**
     * Recursive method used to explore package and sub-packages looking for
     * classes.
     * 
     * @param packageName
     *            The package name (for example
     *            <code>fr.pinguet62.croquette</code>)
     * @param directory
     *            The directory corresponding to the package.
     * @return The {@link Class}es.
     */
    private static Collection<Class<?>> explorePackage(
	    final String packageName, final File directory) {
	Collection<Class<?>> classes = new HashSet<Class<?>>();
	for (File file : directory.listFiles())
	    // Class
	    if (file.isFile()) {
		if (!file.getName().endsWith(".class"))
		    continue;
		try {
		    String className = packageName
			    + "."
			    + file.getName().substring(0,
				    file.getName().length() - 6);
		    Class<?> c = Class.forName(className);
		    classes.add(c);
		} catch (ClassNotFoundException e) {
		}
	    }
	    // Sub package
	    else if (file.isDirectory()) {
		String subPackageName = packageName + "." + file.getName();
		Collection<Class<?>> sousClasses = ActionFactory
			.explorePackage(subPackageName, file);
		classes.addAll(sousClasses);
	    }
	return classes;
    }

    // TODO Complete ActionFactory.getAction(final JsonMessage message)
    /**
     * Get the {@link Action}. <br />
     * Explore classes annotated with {@link Action} to find the
     * <code>action</code> corresponding value.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @return The {@link Action}.
     */
    public static IAction getAction(final JsonObject jsonMessage) {
	final String actionKey = jsonMessage.getString(IAction.ACTION_KEY);
	if (actionKey == null)
	    return null;

	for (Class<?> classe : ActionFactory
		.getAllClasses("fr.pinguet62.croquette.action")) {
	    Action annotation = classe.getAnnotation(Action.class);
	    if (annotation == null)
		continue;
	    String value = annotation.value();
	    if (actionKey.equals(value))
		try {
		    Constructor<?> constructor = classe
			    .getConstructor(JsonObject.class);
		    IAction action = (IAction) constructor
			    .newInstance(jsonMessage);
		    return action;
		} catch (NoSuchMethodException | SecurityException
			| InstantiationException | IllegalAccessException
			| IllegalArgumentException | InvocationTargetException e) {
		}
	}
	return null;
    }

    /**
     * Gets all {@link Class}es contain into the {@link Package}.
     * 
     * @param packageName
     *            The name of {@link Package}.
     * @return The {@link Class}es.
     */
    private static Collection<Class<?>> getAllClasses(final String packageName) {
	ClassLoader cld = Thread.currentThread().getContextClassLoader();
	String path = packageName.replace(".", "/");
	URL resource = cld.getResource(path);
	File directory = new File(resource.getFile());
	return ActionFactory.explorePackage(packageName, directory);
    }

}
