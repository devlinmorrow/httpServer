public class GETHandlerSpy extends GETHandler {

    private boolean handleGETWasCalled;

    public GETHandlerSpy() {
        handleGETWasCalled = false;
    }

    public String handleGET(String request) {
        handleGETWasCalled = true;
        return null;
    }

    public boolean isHandleGETWasCalled() {
        return handleGETWasCalled;
    }
}
