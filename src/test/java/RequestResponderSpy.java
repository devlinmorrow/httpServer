import java.net.Socket;

public class RequestResponderSpy extends RequestResponder {

    private boolean handleRequestWasCalled;

    public RequestResponderSpy() {
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
