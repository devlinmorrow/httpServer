package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;

public class RequestResponder {

    private HandlerFactory handlerFactory;
    private Response response;

    public RequestResponder() {
        handlerFactory = new HandlerFactory();
    }

    public Response respondTo(Request request) {
        response = new Response();
        File resource = new File(request.getURI());
        if (methodNotAllowed(request.getHTTPVerb(), resource.getName())) {
            setMethodNotAllowedResponse();
        } else {
            Handler handler = handlerFactory.buildHandler(request.getHTTPVerb());
            response = handler.handle(request);
        }
        return response;
    }

    private boolean methodNotAllowed(HTTPVerb httpVerb, String resourceName) {
        if (resourceName.toLowerCase().contains("logs")) {
            return httpVerb.isNotAllowedForLogs();
        } else {
            return httpVerb.isNotAllowed();
        }
    }

    private void setMethodNotAllowedResponse() {
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
    }



}
