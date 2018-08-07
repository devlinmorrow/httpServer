package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Handlers.Handler;
import http.Responders.Handlers.HandlerFactory;

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
        } else if (tempMoved(resource.getName())) {
            setFoundResponse();
        } else {
            Handler handler = handlerFactory.buildHandler(request);
            response = handler.handle(request);
        }
        return response;
    }

    private void setFoundResponse() {
        response.setLocationHeader("/");
        response.setBodyContent(ResponseStatus.FOUND.getStatusBody());
        response.setStatus(ResponseStatus.FOUND);
    }

    private boolean tempMoved(String URI) {
        return (URI.equals("redirect"));
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
