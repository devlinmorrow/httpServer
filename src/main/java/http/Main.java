package http;

import http.ClientConnectors.ConnectionManager;
import http.ClientConnectors.ConnectionAcceptor;
import http.Responders.RequestRouter;
import http.Responders.ServerStatus;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException {
        RequestRouter requestRouter = new RequestRouter("/Users/devlin/cob_spec/public");
        String logsPath = startLog();
        ServerSocket serverSocket = new ServerSocket(5000);
        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(System.out, serverSocket,
                        new ConnectionManager(new BufferedWriter(new FileWriter
                                (logsPath)), requestRouter), new ServerStatus());
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
