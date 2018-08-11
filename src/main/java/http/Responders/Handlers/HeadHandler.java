package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.ResourceTypeIdentifier;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class HeadHandler extends Handler {

    private String rootPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;

    public HeadHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.HEAD);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) {
        Response response;
        File resource = new File(rootPath + request.getResourcePath());
        if (!resource.exists()) {
            response = setResourceNotFoundResponse();
        } else {
            response = getHeadResponse(resource);
        }
        response.clearBody();
        return response;
    }

    private Response setResourceNotFoundResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.NOTFOUND);
        return response;
    }

    private Response getHeadResponse(File resource) {
        Response response = new Response();
        response.setStatus(ResponseStatus.OK);
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        return response;
    }
}
