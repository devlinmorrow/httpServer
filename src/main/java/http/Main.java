package http;

import http.ClientConnectors.httpServer;
import http.ClientConnectors.ConnectionManager;
import http.Responders.RequestRouter;
import http.Responders.ServerStatus;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger logger = new Logger();
        String rootPath = "/Users/devlin/cob_spec/public";
        RequestRouter requestRouter = new RequestRouter(rootPath, logger);
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        httpServer httpServer = new httpServer
                (System.out, serverSocket, new ServerStatus(),
                        Executors.newFixedThreadPool(20), requestRouter, logger);
        httpServer.start();
    }
}
