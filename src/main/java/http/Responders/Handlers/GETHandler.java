package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;

import java.io.File;

public class GETHandler implements Handler {

    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private Response response;
    private Request request;
    private RangeResponder rangeResponder;
    private Authenticator authenticator;

    public GETHandler() {
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(fileContentConverter);
        authenticator = new Authenticator();
    }

    @Override
    public Response handle(Request request) {
        this.request = request;
        response = new Response();
        File resource = new File(request.getURI());
        if (isLogs()) {
            String logsAction = authenticator.handleLogs(request, response);
            routeLogs(logsAction, resource);
        } else if (isBeverage(resource.getName())) {
            setTeapotResponse(resource.getName());
        } else {
            if (!resource.exists()) {
                setResourceNotFoundResponse();
            } else {
                performGETResponse(resource);
            }
        }
        if (headRequest()) {
            response.clearAllExceptStatusLine();
        }
        return response;
    }

    private void setTeapotResponse(String URI) {
        if (URI.equals("coffee")) {
            response.setStatus(ResponseStatus.IMATEAPOT);
            response.setBodyContent(ResponseStatus.IMATEAPOT.getStatusBody());
        } else {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent("Here's some delicious tea!".getBytes());
        }
    }

    private boolean isBeverage(String URI) {
        return URI.equals("coffee") || URI.equals("tea");
    }

    private void routeLogs(String logsAction, File resource) {
        if (logsAction.equals("NotAllowed")) {
            setMethodNotAllowed();
        } else if (logsAction.equals("Unauthorised")) {
            setUnauthorised();
        } else {
            performGETResponse(resource);
        }
    }

    private void setUnauthorised() {
        response.setUnauthorisedHeader(HardcodedValues.AUTHENTICATEMESSAGE.getS());
        response.setBodyContent(ResponseStatus.UNAUTHORISED.getStatusBody());
        response.setStatus(ResponseStatus.UNAUTHORISED);
    }

    private void setMethodNotAllowed() {
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
    }

    private boolean isLogs() {
        return request.getURI().toLowerCase().contains("logs");
    }

    private boolean headRequest() {
        return request.getHTTPVerb() == HTTPVerb.HEAD;
    }


    private void setResourceNotFoundResponse() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
    }

    private void performGETResponse(File resource) {
        if (resource.isDirectory()) {
            GETDirectory(resource);
        } else {
            GETFile(resource);
        }
    }

    private void GETDirectory(File resource) {
        response.setContentTypeHeader(ContentType.HTML);
        response.setBodyContent(getDirectoryContent(resource));
        response.setStatus(ResponseStatus.OK);
    }

    private byte[] getDirectoryContent(File resource) {
        StringBuilder listing = new StringBuilder();
        listing.append("<html><head></head><body>");
        File[] files = resource.listFiles();
        for (File file : files) {
            String fileName = file.getName();
            listing.append("<a href='/").append(fileName).append("'>")
                    .append(fileName).append("</a>").append("<br>");
        }
        listing.append("</body></html>");
        return String.valueOf(listing).getBytes();
    }

    private void GETFile(File resource) {
        response.setContentTypeHeader(resourceTypeIdentifier.getType(resource));
        byte[] fullContents = fileContentConverter.getFullContents(resource);
        if (request.getHeaders().containsKey("Range")) {
            response = rangeResponder.performRangeRequest(request, fullContents);
        } else {
            response.setBodyContent(fullContents);
            response.setStatus(ResponseStatus.OK);
        }
    }
}
