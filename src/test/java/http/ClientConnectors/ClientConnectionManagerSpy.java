package http.ClientConnectors;

import http.ClientConnectors.ClientConnectionManager;

import java.net.Socket;

public class ClientConnectionManagerSpy extends ClientConnectionManager {

    private boolean handleRequestWasCalled;

    public ClientConnectionManagerSpy() {
        handleRequestWasCalled = false;
    }

    @Override
    public void respondTo(Socket clientConnection) {
        handleRequestWasCalled = true;
    }

    public boolean wasRespondToRequestCalled() {
        return handleRequestWasCalled;
    }
}
