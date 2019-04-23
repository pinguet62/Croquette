package fr.pinguet62.croquette.commons.action;

/** Action executed to exchange data between devices. */
public interface IAction {

    /**
     * Execute the action.
     *
     * @throws ActionException
     *             Exception during execution.
     */
    void execute();

}
