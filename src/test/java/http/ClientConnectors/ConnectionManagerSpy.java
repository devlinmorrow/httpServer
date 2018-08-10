package http.ClientConnectors;

import http.Logger;
import http.Responders.RequestRouter;
import sun.rmi.runtime.Log;

import java.io.BufferedWriter;
import java.net.Socket;

public class ConnectionManagerSpy extends ConnectionManager {

    private boolean handleRequestWasCalled;

    public ConnectionManagerSpy(RequestRouter requestRouter, Logger logger) {
        super(requestRouter, logger);
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
