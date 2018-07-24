import java.net.Socket;

public class RouterSpy extends Router {

    private boolean routeWasCalled;

    public RouterSpy(GETHandler getHandler) {
        super(getHandler);
        routeWasCalled = false;
    }

    @Override
    public void route(Socket clientSocket) {
        routeWasCalled = true;
    }

    public boolean routeWasCalled() {
        return routeWasCalled;
    }

}
