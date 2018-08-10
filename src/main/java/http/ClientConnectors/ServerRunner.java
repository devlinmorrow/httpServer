package http.ClientConnectors;

import java.net.Socket;

public class ServerRunner implements Runnable {

    private Socket clientConnection;
    private ConnectionManager connectionManager;

    public ServerRunner(Socket clientConnection, ConnectionManager connectionManager) {
        this.clientConnection = clientConnection;
        this.connectionManager = connectionManager;
    }

    @Override
    public void run() {
        connectionManager.respondTo(clientConnection);
    }
}
