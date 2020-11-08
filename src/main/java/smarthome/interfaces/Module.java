package smarthome.interfaces;

import io.javalin.Javalin;
import smarthome.controllers.SmartHomeSimulator;

public interface Module {
    void onLoad(Javalin app, SmartHomeSimulator shs);
}
