package http.ClientConnectors;

import http.Responders.Router2;

import java.io.BufferedWriter;
import java.net.Socket;

public class ConnectionManagerSpy extends ConnectionManager {

    private boolean handleRequestWasCalled;

    public ConnectionManagerSpy(BufferedWriter bufferedWriter, Router2 router2) {
        super(bufferedWriter, router2);
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
