package http;

import http.ClientConnectors.ClientConnectionManager;
import http.ClientConnectors.ConnectionAcceptor;
import http.Responders.ServerStatus;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        String logsPath = startLog();
        ServerSocket serverSocket = new ServerSocket(5000);
        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(System.out, serverSocket,
                        new ClientConnectionManager(new BufferedWriter(new FileWriter
                                (logsPath))), new ServerStatus());
        connectionAcceptor.start();
    }

    private static String startLog() {
        String logPathString = HardcodedValues.RESOURCEPATH.getS() + "/logs";
        Path logsPath = Paths.get(logPathString);
        if (Files.exists(logsPath)) {
            try {
                Files.delete(logsPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.createFile(logsPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logPathString;
    }
}
