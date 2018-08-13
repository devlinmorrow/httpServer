package http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ServerSocketSpy extends ServerSocket {

    private List<Socket> clientSockets;
    private int currentClient;
    private int timesAcceptCalled;
    private SocketException socketException;
    private boolean exceptionSet;

    public ServerSocketSpy(List<Socket> clientConnections) throws IOException {
        this.clientSockets = clientConnections;
        currentClient = 0;
        timesAcceptCalled = 0;
        socketException = new SocketException();
        exceptionSet = false;
    }

    @Override
    public Socket accept() throws SocketException {
        Socket currentClientConnection = clientSockets.get(currentClient);
        currentClient++;
        timesAcceptCalled++;
        if (exceptionSet) {
            throw socketException;
        }
        return currentClientConnection;
    }

    public int howManyTimesAcceptCalled() {
        return timesAcceptCalled;
    }

    public void setSocketException() {
        exceptionSet = true;
    }
}
