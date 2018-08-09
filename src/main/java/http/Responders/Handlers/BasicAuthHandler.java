package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.ResourceTypeIdentifier;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class BasicAuthHandler extends Handler {

    private Authenticator authenticator;
    private FileContentConverter fileContentConverter;
    private ResourceTypeIdentifier resourceTypeIdentifier;
    private String rootPath;

    public BasicAuthHandler(String rootPath) {
        authenticator = new Authenticator();
        fileContentConverter = new FileContentConverter();
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.GET);
        addHandledPathSegment("logs");
    }

    @Override
    public Response getResponse(Request request) {
        Response response;
        String logsAction = authenticator.handleLogs(request);
        File resource = new File(rootPath + request.getResourcePath());
        response = routeLogs(logsAction, resource);
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

    private Response setMethodNotAllowed() {
        Response response = new Response();
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
        return response;
    }

    private Response setUnauthorised() {
        Response response = new Response();
        response.setUnauthorisedHeader(HardcodedValues.AUTHENTICATEMESSAGE.getS());
        response.setBodyContent(ResponseStatus.UNAUTHORISED.getStatusBody());
        response.setStatus(ResponseStatus.UNAUTHORISED);
        return response;
    }

    private Response GETFile(File resource) {
        Response response = new Response();
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        byte[] fullContents = fileContentConverter.getFullContents(resource);
        response.setBodyContent(fullContents);
        response.setStatus(ResponseStatus.OK);
        return response;
    }

}
