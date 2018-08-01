package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Handlers.Handler;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class OPTIONSHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        File resource = new File(request.getURI());
        String allowedMethods;
        if (resource.getName().toLowerCase().contains("logs")) {
            allowedMethods = HTTPVerb.getAllowedForLogsMethods();
        } else {
            allowedMethods = HTTPVerb.getAllowedMethods();
        }
        response.setAllowHeader(allowedMethods);
        response.setStatus(ResponseStatus.OK);
        return response;
    }
}
