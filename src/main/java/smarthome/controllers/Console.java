package smarthome.controllers;

import io.javalin.Javalin;
import io.javalin.websocket.WsConnectContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Console {
    private static List<WsConnectContext> connections = new LinkedList<>();
    private static BufferedWriter log;

    public static void createConnection(Javalin app) {
        try {
            FileWriter logFile = new FileWriter(getFilename());
            log = new BufferedWriter(logFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        app.ws("/websocket/console", ws -> {
            ws.onConnect(ctx -> {
                connections.add(ctx);
                print("New connection established.");
            });
            ws.onClose(ctx -> {
                connections.remove(ctx);
                print("Connection lost.");
            });
        });
    }

    public static void print(String message) {
        String output = getTimestamp() + " " + message;
        System.out.println(output);
        try {
            log.write(output);
            log.newLine();
            log.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        connections.forEach(ctx -> {
            ctx.send(output);
        });
    }

    private static String getTimestamp() {
        LocalTime time = LocalTime.now();
        return "[" + time.format(DateTimeFormatter.ofPattern("HH:mm:ss")) + "]";
    }

    private static String getFilename() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(DateTimeFormatter.ofPattern("ee-MMMM-YYYY_A")) + ".log";
    }
}
