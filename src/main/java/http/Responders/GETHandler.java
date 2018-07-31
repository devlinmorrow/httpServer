package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;

public class GETHandler implements Handler {

    private ResourceTypeIdentifier resourceTypeIdentifier;
    private FileContentConverter fileContentConverter;
    private Response response;

    public GETHandler() {
        resourceTypeIdentifier = new ResourceTypeIdentifier();
        fileContentConverter = new FileContentConverter();
    }

    public Response handleRequest(Request request) {
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
        response.setBodyContent(fileContentConverter.getContents(resource));
        response.setStatus(ResponseStatus.OK);
    }
}
