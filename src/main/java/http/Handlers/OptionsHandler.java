package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;

import java.io.File;

public class OptionsHandler extends Handler {

    public OptionsHandler() {
        addHandledVerb(HTTPVerb.OPTIONS);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        File resource = new File(request.getResourcePath());
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
