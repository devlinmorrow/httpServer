package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GETHandler implements Handler {

    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private Response response;
    private Request request;
    private RangeResponder rangeResponder;

    public GETHandler() {
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
        rangeResponder = new RangeResponder(fileContentConverter);
    }

    @Override
    public Response handle(Request request) {
        this.request = request;
        response = new Response();
        File resource = new File(request.getURI());
        if (!resource.exists()) {
            setResourceNotFoundResponse();
        } else {
            performGETResponse(resource);
        }
        if (request.getHTTPVerb() == HTTPVerb.HEAD) {
            response.clearAllExceptStatusLine();
        }
        return response;
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
