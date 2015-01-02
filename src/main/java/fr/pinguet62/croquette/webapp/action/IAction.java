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

}
