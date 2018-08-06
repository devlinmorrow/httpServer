package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Handlers.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;

public class HandlerFactoryTest {

    private HandlerFactory handlerFactory;

    @Before
    public void setUp() {
        handlerFactory = new HandlerFactory();
    }

    @Test
    public void defaultIsERRORHandler() {
        Request request = new Request(HTTPVerb.POST, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof UnrecognisedMethodHandler);
    }

    @Test
    public void buildGETHandler_withGET() {
        Request request = new Request(HTTPVerb.GET, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof GETHandler);
    }

    @Test
    public void buildGETHandler_withHEAD() {
        Request request = new Request(HTTPVerb.HEAD, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof GETHandler);
    }

    @Test
    public void buildOPTIONSHandler() {
        Request request = new Request(HTTPVerb.OPTIONS, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof OPTIONSHandler);
    }

    @Test
    public void buildPUTHandler() {
        Request request = new Request(HTTPVerb.PUT, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof PUTHandler);
    }

    @Test
    public void buildDELETEHandler() {
        Request request = new Request(HTTPVerb.DELETE, "/", new HashMap<>(), "");
        assert(handlerFactory.buildHandler(request) instanceof DELETEHandler);
    }
}