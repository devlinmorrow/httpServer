import java.net.Socket;

public class RequestRouterSpy extends RequestRouter {

    private boolean routeWasCalled;

    public RequestRouterSpy(GETHandler getHandler) {
        super(getHandler);
        routeWasCalled = false;
    }

    @Override
    public void route(String request) {
        routeWasCalled = true;
    }

    public boolean routeWasCalled() {
        return routeWasCalled;
    }

}
