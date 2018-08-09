package http.ClientConnectors;

import http.Responders.RequestRouter;

import java.io.BufferedWriter;
import java.net.Socket;

public class ConnectionManagerSpy extends ConnectionManager {

    private boolean handleRequestWasCalled;

    public ConnectionManagerSpy(BufferedWriter bufferedWriter, RequestRouter requestRouter) {
        super(bufferedWriter, requestRouter);
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
