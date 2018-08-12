package http.ClientConnectors;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ServerSocketSpy extends ServerSocket {

    private List<Socket> clientSockets;
    private int currentClient;
    private int timesAcceptCalled;

    public ServerSocketSpy(List<Socket> clientConnections) throws IOException {
        this.clientSockets = clientConnections;
        currentClient = 0;
        timesAcceptCalled = 0;
    }

    @Override
    public Socket accept() {
        Socket currentClientConnection = clientSockets.get(currentClient);
        currentClient++;
        timesAcceptCalled++;
        return currentClientConnection;
    }

    public int howManyTimesAcceptCalled() {
        return timesAcceptCalled;
    }
}
