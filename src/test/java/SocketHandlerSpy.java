import java.net.Socket;

public class SocketHandlerSpy extends SocketHandler {

    private boolean handleRequestWasCalled;

    public SocketHandlerSpy() {
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
