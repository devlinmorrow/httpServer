package http.ClientConnectors;

import http.ClientConnectors.ClientConnectionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnectionManagerSpy extends ClientConnectionManager {

    private boolean handleRequestWasCalled;

    public ClientConnectionManagerSpy(BufferedWriter bufferedWriter) {
        super(bufferedWriter);
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
