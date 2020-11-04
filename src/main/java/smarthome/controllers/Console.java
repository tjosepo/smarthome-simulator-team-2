package smarthome.controllers;

import io.javalin.Javalin;
import io.javalin.websocket.WsConnectContext;
import io.javalin.websocket.WsHandler;

import java.util.List;

public class ConsoleOutput {
    private static List<WsConnectContext> connections;

    public static void createConnection(Javalin app) {
        app.ws("/websocket/console", ws -> {
            ws.onConnect(ctx -> {
                connections.add(ctx);
            });
        });
    }

    public static void print(String message) {
        connections.forEach(ctx -> {
            ctx.send(message);
        });
    }
}
