package smarthome;

import smarthome.controllers.SmartHomeSimulator;
import smarthome.controllers.modules.SmartHomeCore;
import smarthome.controllers.modules.SmartHomeSecurity;

/**
 * Main class.
 */
public class Main {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SmartHomeSimulator shs = new SmartHomeSimulator();
        shs.loadModule(new SmartHomeCore());
        shs.loadModule(new SmartHomeSecurity());
    }
}
