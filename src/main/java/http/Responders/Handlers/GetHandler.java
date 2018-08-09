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
    private Authenticator authenticator;

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
        addHandledPathSegment("logs");
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(fileContentConverter);
        authenticator = new Authenticator();
    }

    @Override
    public Response getResponse(Request request) {
        this.request = request;
        Response response;
        File resource = new File(rootPath + request.getResourcePath());
        if (isLogs()) {
            String logsAction = authenticator.handleLogs(request);
            response = routeLogs(logsAction, resource);
        } else {
            if (!resource.exists()) {
                response = setResourceNotFoundResponse();
            } else {
                response = GETFile(resource);
            }
        }
        if (headRequest()) {
            response.clearAllExceptStatusLine();
        }
        return response;
    }

    private Response routeLogs(String logsAction, File resource) {
        switch (logsAction) {
            case "NotAllowed":
                return setMethodNotAllowed();
            case "Unauthorised":
                return setUnauthorised();
            default:
                return GETFile(resource);
        }
    }

    private Response setUnauthorised() {
        Response response = new Response();
        response.setUnauthorisedHeader(HardcodedValues.AUTHENTICATEMESSAGE.getS());
        response.setBodyContent(ResponseStatus.UNAUTHORISED.getStatusBody());
        response.setStatus(ResponseStatus.UNAUTHORISED);
        return response;
    }

    private Response setMethodNotAllowed() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
        return response;
    }

    private boolean isLogs() {
        return request.getResourcePath().toLowerCase().contains("logs");
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
