package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;

public class OPTIONSHandler implements Handler {

    public Response handleRequest(Request request) {
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
