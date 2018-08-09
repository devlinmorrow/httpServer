package http.ClientConnectors;

import http.Message;
import http.Responders.ServerStatus;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionAcceptor {

    private PrintStream stdOut;
    private ServerSocket serverSocket;
    private ConnectionManager connectionManager;
    private ServerStatus serverStatus;

    public ConnectionAcceptor(PrintStream stdOut, ServerSocket serverSocket, ConnectionManager connectionManager, ServerStatus serverStatus) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.connectionManager = connectionManager;
        this.serverStatus = serverStatus;
    }

    public void start() {
            try {
                while (serverStatus.isRunning()) {
                    respondToRequest(serverSocket.accept());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void respondToRequest(Socket clientConnection) throws IOException {
        stdOut.println(Message.REQUESTMADE.getS());
        connectionManager.respondTo(clientConnection);
        clientConnection.close();
    }
}
