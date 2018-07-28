package http;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        ConnectionAcceptor connectionAcceptor =
                new ConnectionAcceptor(System.out, serverSocket, new ClientConnectionManager(), new ServerStatus());
        connectionAcceptor.start();
    }
}
