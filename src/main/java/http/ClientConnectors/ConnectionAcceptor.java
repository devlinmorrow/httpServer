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
    private ClientConnectionManager clientConnectionManager;
    private ServerStatus serverStatus;

    public ConnectionAcceptor(PrintStream stdOut, ServerSocket serverSocket, ClientConnectionManager clientConnectionManager, ServerStatus serverStatus) {
        this.stdOut = stdOut;
        this.serverSocket = serverSocket;
        this.clientConnectionManager = clientConnectionManager;
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
        clientConnectionManager.respondTo(clientConnection);
        clientConnection.close();
    }
}
