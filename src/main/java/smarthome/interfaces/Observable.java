package smarthome.interfaces;

public interface Observable {
    void attachObserver(Observer o);
    void detachObserver(Observer o);
    void notifyObservers(Observable observable);
}
