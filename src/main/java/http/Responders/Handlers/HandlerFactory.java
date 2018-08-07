package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.util.HashMap;

public class HandlerFactory {

    private FormFields formFields;

    public HandlerFactory() {
        formFields = new FormFields(new HashMap<>());
    }

    public Handler buildHandler(Request request) {
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (httpVerb == HTTPVerb.OPTIONS) {
            return new OPTIONSHandler();
        } else if (httpVerb == HTTPVerb.GET || httpVerb == HTTPVerb.HEAD || httpVerb == HTTPVerb.POST
        || httpVerb == HTTPVerb.PUT && request.getURI().contains("form") || httpVerb == HTTPVerb.DELETE &&
        request.getURI().contains("form")) {
            if (request.getURI().contains("cookie")) {
                return new COOKIEHandler();
            } else if (request.getURI().contains("?")) {
                return new PARAMETERHandler();
            } else {
                return new GETHandler(formFields);
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
