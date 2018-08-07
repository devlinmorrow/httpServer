package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

public class HandlerFactory {

    public Handler buildHandler(Request request) {
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (httpVerb == HTTPVerb.OPTIONS) {
            return new OPTIONSHandler();
        } else if (httpVerb == HTTPVerb.GET || httpVerb == HTTPVerb.HEAD) {
            if (request.getURI().contains("cookie")) {
                return new COOKIEHandler();
            } else if (request.getURI().contains("?")) {
                return new PARAMETERHandler();
            } else {
                return new GETHandler();
            }
        } else if (httpVerb == HTTPVerb.PATCH) {
            return new PATCHHandler();
        } else if (httpVerb == HTTPVerb.PUT) {
            return new PUTHandler();
        } else if (httpVerb == HTTPVerb.DELETE) {
            return new DELETEHandler();
        } else {
            return new UnrecognisedMethodHandler();
        }
    }
}
