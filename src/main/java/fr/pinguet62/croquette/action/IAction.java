package fr.pinguet62.croquette.action;

/** Possible action with smartphone. */
public interface IAction {

    /** Key for {@link ActionType}. */
    public static final String ACTION_KEY = "action";

    /** Execute the action. */
    void execute();

    /**
     * Test if it's an action from the smartphone.<br />
     * Used to not execute action sent to the smartphone and immediately
     * received because of the broadcast.
     * 
     * @return Result.
     */
    boolean fromSmartphone();

}
