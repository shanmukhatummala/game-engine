package game.logger;

/**
 * This interface represents an observer in the observer pattern. It defines a single method,
 * update, which is called by an Observable object when it has been updated.
 */
public interface Observer {
    /**
     * This method is called by an Observable object when it has been updated.
     *
     * @param p_observable The Observable object that has been updated.
     */
    public void update(Observable p_observable);
}
