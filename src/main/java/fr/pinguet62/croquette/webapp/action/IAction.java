package fr.pinguet62.croquette.webapp.action;

/**
 * Action executed to ask data to Smartphone, or for handler for data received
 * from Smartphone.
 */
public interface IAction {

    /**
     * Execute the action.
     *
     * @throws ActionException
     *             Exception during execution.
     */
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
