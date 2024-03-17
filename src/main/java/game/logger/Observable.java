package game.logger;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private final List<Observer> d_observers = new ArrayList<Observer>();
    public void attach(Observer p_o){
        this.d_observers.add(p_o);
    }
    public void detach(Observer p_o){
        if (!d_observers.isEmpty()) {
            d_observers.remove(p_o);
        }
    }
    public void notifyObservers(Observable p_observable) {
        for (Observer observer : d_observers) {
            observer.update(p_observable);
        }
    }
}
