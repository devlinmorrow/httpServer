public class RouterSpy extends Router {

    private boolean routeWasCalled;

    public RouterSpy() {
        routeWasCalled = false;
    }

    @Override
    public void route(String request) {
        super.route(request);
        routeWasCalled = true;
    }

    public boolean routeWasCalled() {
        return routeWasCalled;
    }

}
