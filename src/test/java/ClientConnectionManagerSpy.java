import java.net.Socket;

public class ClientConnectionManagerSpy extends ClientConnectionManager {

    private boolean handleRequestWasCalled;

    public ClientConnectionManagerSpy() {
        handleRequestWasCalled = false;
    }

    @Override
    public void handleRequest(Socket socket) {
        handleRequestWasCalled = true;
    }

    public boolean wasHandleRequestCalled() {
        return handleRequestWasCalled;
    }
}
