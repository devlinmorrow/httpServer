package http.server;

import http.util.Logger;
import http.Handlers.RequestRouter;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;

public class httpServer {

    private PrintStream stdOut;
    private ServerSocket serverSocket;
    private ServerStatus serverStatus;
    private Executor executor;
    private RequestRouter requestRouter;
    private Logger logger;

    public httpServer(PrintStream stdOut, ServerSocket serverSocket, ServerStatus serverStatus, Executor executor,
                      RequestRouter requestRouter, Logger logger) {
        this.requestRouter = requestRouter;
        this.logger = logger;
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.serverStatus = serverStatus;
        this.executor = executor;
    }

    public void start() {
            try {
                while (serverStatus.isRunning()) {
                    Socket clientConnection = serverSocket.accept();
                    stdOut.println("Request made.");
                    executor.execute(new ServerRunner(clientConnection,
                            new ConnectionManager(requestRouter, logger)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}
