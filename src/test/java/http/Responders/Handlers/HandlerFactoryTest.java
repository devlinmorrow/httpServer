package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Responders.Handlers.*;
import org.junit.Before;
import org.junit.Test;

public class HandlerFactoryTest {

    private HandlerFactory handlerFactory;

    @Before
    public void setUp() {
        handlerFactory = new HandlerFactory();
    }

    @Test
    public void defaultIsERRORHandler() {
        assert(handlerFactory.buildHandler(HTTPVerb.POST) instanceof UnrecognisedMethodHandler);
    }

    @Test
    public void buildGETHandler_withGET() {
        assert(handlerFactory.buildHandler(HTTPVerb.GET) instanceof GETHandler);
    }

    @Test
    public void buildGETHandler_withHEAD() {
        assert(handlerFactory.buildHandler(HTTPVerb.HEAD) instanceof GETHandler);
    }

    @Test
    public void buildOPTIONSHandler() {
        assert(handlerFactory.buildHandler(HTTPVerb.OPTIONS) instanceof OPTIONSHandler);
    }

    @Test
    public void buildPUTHandler() {
        assert(handlerFactory.buildHandler(HTTPVerb.PUT) instanceof PUTHandler);
    }

    @Test
    public void buildDELETEHandler() {
        assert(handlerFactory.buildHandler(HTTPVerb.DELETE) instanceof DELETEHandler);
    }
}