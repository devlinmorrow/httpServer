package http;

import http.Handlers.RequestRouter;
import http.server.ServerStatus;
import http.server.httpServer;
import http.util.Logger;
import http.util.ProgramArgParser;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws IOException {
        ProgramArgParser programArgParser = new ProgramArgParser(args);

        Logger logger = new Logger();
        RequestRouter requestRouter = new RequestRouter(programArgParser.getRootPath(), logger);
        ServerSocket serverSocket = new ServerSocket(programArgParser.getPort());

        httpServer httpServer = new httpServer(System.out, serverSocket, new ServerStatus(),
                        Executors.newFixedThreadPool(20), requestRouter, logger);

        httpServer.start();
    }
}
