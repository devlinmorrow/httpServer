package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;

import java.io.File;

public class GetHandler extends Handler {

    private String rootPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private Request request;
    private RangeResponder rangeResponder;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.GET);
        addHandledVerb(HTTPVerb.HEAD);
        addHandledPathSegment("file1");
        addHandledPathSegment("file2");
        addHandledPathSegment("txt");
        addHandledPathSegment("image");
        addHandledPathSegment("jpg");
        addHandledPathSegment("foobar");
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(fileContentConverter);
    }

    @Override
    public Response getResponse(Request request) {
        this.request = request;
        Response response;
        File resource = new File(rootPath + request.getResourcePath());
        if (!resource.exists()) {
            response = setResourceNotFoundResponse();
        } else {
            response = GETFile(resource);
        }
        if (headRequest()) {
            response.clearAllExceptStatusLine();
        }
        return response;
    }

    private boolean headRequest() {
        return request.getHTTPVerb() == HTTPVerb.HEAD;
    }

    private Response setResourceNotFoundResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
        return response;
    }

    private Response GETFile(File resource) {
        Response response = new Response();
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        byte[] fullContents = fileContentConverter.getFullContents(resource);
        if (request.getHeaders().containsKey("Range")) {
            response = rangeResponder.performRangeRequest(request, fullContents);
        } else {
            response.setBodyContent(fullContents);
            response.setStatus(ResponseStatus.OK);
        }
        return response;
    }
}
