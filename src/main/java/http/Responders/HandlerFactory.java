package http.Responders;

import http.Requesters.HTTPVerb;

public class HandlerFactory {

    public Handler buildHandler(HTTPVerb httpVerb) {
        if (httpVerb == HTTPVerb.OPTIONS) {
            return new OPTIONSHandler();
        } else if (httpVerb == HTTPVerb.GET || httpVerb == HTTPVerb.HEAD) {
            return new GETHandler();
        } else if (httpVerb == HTTPVerb.PUT) {
            return new PUTHandler();
        } else {
            return new UnrecognisedMethodHandler();
        }
    }
}
