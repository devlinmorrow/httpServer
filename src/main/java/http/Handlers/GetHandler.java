package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.FileContentConverter;
import http.util.ResourceTypeIdentifier;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetHandler extends Handler {

    private String rootPath;
    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private RangeResponder rangeResponder;

    public GetHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.GET);
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(rootPath, fileContentConverter, resourceTypeIdentifier);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) throws IOException {
        return resourceDoesNotExist(request) ? notFoundResponse() : performGet(request);
    }

    private boolean resourceDoesNotExist(Request request) {
        return !Files.exists(Paths.get(rootPath + request.getResourcePath()));
    }

    private Response notFoundResponse() {
        Response response = new Response();
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
        return response;
    }

    private Response performGet(Request request) throws IOException {
        return range(request) ? rangeResponder.performRange(request) : fullGet(request);
    }

    private boolean range(Request request) {
        return request.getHeaders().containsKey("Range");
    }

    private Response fullGet(Request request) throws IOException {
        Response response = new Response();
        File resource = new File(rootPath + request.getResourcePath());
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        response.setBodyContent(fileContentConverter.getFullContents(resource));
        response.setStatus(ResponseStatus.OK);
        return response;
    }
}
