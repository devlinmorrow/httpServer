package http.Responders;

import http.Requesters.HTTPVerb;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HandlerFactoryTest {

    private HandlerFactory handlerFactory;

    @Before
    public void setUp() {
        handlerFactory = new HandlerFactory();
    }

    @Test
    public void defaultIsERRORHandler() {
        assert(handlerFactory.buildHandler(HTTPVerb.POST) instanceof ERRORHandler);
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
}