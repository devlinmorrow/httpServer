public class GETHandlerSpy extends GETHandler {

    private boolean handleGETWasCalled;

    public GETHandlerSpy() {
        handleGETWasCalled = false;
    }

    public void handleGET(String request) {
        handleGETWasCalled = true;
    }

    public boolean isHandleGETWasCalled() {
        return handleGETWasCalled;
    }
}
