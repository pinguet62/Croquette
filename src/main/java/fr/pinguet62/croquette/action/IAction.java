package fr.pinguet62.croquette.action;

/** Possible action with smartphone. */
public interface IAction {

    /** Key for {@link ActionType}. */
    public static final String ACTION_TYPE = "action";

    /** Execute the action. */
    void execute();

}
