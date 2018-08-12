package http;

import http.util.ProgramArgParser;
import http.server.httpServer;
import http.Handlers.RequestRouter;
import http.server.ServerStatus;
import http.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        int defaultPort = 5000;
        ProgramArgParser programArgParser = new ProgramArgParser(args, defaultPort);

        Logger logger = new Logger();
        RequestRouter requestRouter = new RequestRouter(programArgParser.getRootPath(), logger);
        ServerSocket serverSocket = new ServerSocket(programArgParser.getPort());

        httpServer httpServer = new httpServer(System.out, serverSocket, new ServerStatus(),
                        Executors.newFixedThreadPool(20), requestRouter, logger);

        httpServer.start();
    }
}
