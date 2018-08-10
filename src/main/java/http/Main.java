package http;

import http.ClientConnectors.ConnectionAcceptor;
import http.ClientConnectors.ConnectionManager;
import http.Responders.RequestRouter;
import http.Responders.ServerStatus;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        Logger logger = new Logger();
        String rootPath = "/Users/devlin/cob_spec/public";
        RequestRouter requestRouter = new RequestRouter(rootPath, logger);
        int port = 5000;
        ServerSocket serverSocket = new ServerSocket(port);
        ConnectionAcceptor connectionAcceptor = new ConnectionAcceptor
                (System.out, serverSocket, new ConnectionManager(requestRouter,
                        logger), new ServerStatus());
        connectionAcceptor.start();
    }
}
