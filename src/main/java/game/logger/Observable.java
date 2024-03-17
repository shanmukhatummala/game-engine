package game.logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an observable object, or "data" in the model-view paradigm. It can be
 * subclassed to represent an object that the application wants to have observed.
 */
public class Observable {
    /** The list of observers to notify upon changes. */
    private final List<Observer> d_observers = new ArrayList<Observer>();

    /**
     * Attaches an observer to this observable object.
     *
     * @param p_o The observer to attach.
     */
    public void attach(Observer p_o) {
        this.d_observers.add(p_o);
    }

    /**
     * Detaches an observer from this observable object.
     *
     * @param p_o The observer to detach.
     */
    public void detach(Observer p_o) {
        if (!d_observers.isEmpty()) {
            d_observers.remove(p_o);
        }
    }

    /**
     * Notifies all attached observers that the observable object has been updated.
     *
     * @param p_observable The observable object that has been updated.
     */
    public void notifyObservers(Observable p_observable) {
        for (Observer observer : d_observers) {
            observer.update(p_observable);
        }
    }
}
