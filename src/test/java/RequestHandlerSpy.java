import java.net.Socket;

public class RequestHandlerSpy extends RequestHandler {

    private boolean handleRequestWasCalled;

    public RequestHandlerSpy() {
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
