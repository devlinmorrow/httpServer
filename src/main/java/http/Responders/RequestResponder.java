package http.Responders;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;

import java.io.File;

public class RequestResponder {

    private FileContentConverter fileContentConverter;
    private Response response;

    public RequestResponder() {
        fileContentConverter = new FileContentConverter();
    }

    public Response respondTo(Request request) {
        response = new Response();
        File resource = new File(request.getURI());
        HTTPVerb httpVerb = request.getHTTPVerb();
        if (httpVerb == HTTPVerb.OPTIONS) {
            performOPTIONSRequest(resource.getName());
        } else if (doesNotExist(resource)) {
            setResourceNotFoundResponse();
        } else {
            if (methodNotAllowed(httpVerb, resource.getName())) {
                setMethodNotAllowedResponse();
            } else if (httpVerb == HTTPVerb.HEAD) {
                performHEADRequest();
            } else if (httpVerb == HTTPVerb.GET) {
                performGETRequest(resource);
            }
        }
        return response;
    }

    private boolean methodNotAllowed(HTTPVerb httpVerb, String resourceName) {
        if (resourceName.toLowerCase().contains("logs")) {
            return httpVerb.isNotAllowedForLogs();
        } else {
            return httpVerb.isNotAllowed();
        }
    }

    private void performOPTIONSRequest(String resourceName) {
        String allowedMethods;
        if (resourceName.toLowerCase().contains("logs")) {
            allowedMethods = HTTPVerb.getAllowedForLogsMethods();
        } else {
            allowedMethods = HTTPVerb.getAllowedMethods();
        }
        response.setAllowHeader(allowedMethods);
        response.setStatus(ResponseStatus.OK);
    }

    private void setMethodNotAllowedResponse() {
        response.setStatus(ResponseStatus.METHODNOTALLOWED);
        response.setBodyContent(ResponseStatus.METHODNOTALLOWED.getStatusBody());
    }

    private void performHEADRequest() {
        response.clearAllExceptStatusLine();
        response.setStatus(ResponseStatus.OK);
    }

    private boolean doesNotExist(File requestedFile) {
        return !requestedFile.exists();
    }

    private void setResourceNotFoundResponse() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
    }

    private void setResourceType(File resource) {
        String resourceName = resource.getName();
        ContentType contentType;
        if (resource.isDirectory() || resourceName.contains(".html")) {
            contentType = ContentType.HTML;
        } else if (resourceName.contains(".jpeg")) {
            contentType = ContentType.JPEG;
        } else if (resourceName.contains(".gif")) {
            contentType = ContentType.GIF;
        } else if (resourceName.contains(".png")) {
            contentType = ContentType.PNG;
        } else {
            contentType = ContentType.TXT;
        }
        setContentType(contentType);
    }

    private void setContentType(ContentType contentType) {
        response.setContentTypeHeader(contentType);
    }

    private void performGETRequest(File resource) {
        setResourceType(resource);
        if (resource.isDirectory()) {
            response.setBodyContent(getDirectoryContent(resource));
        } else {
            response.setBodyContent(fileContentConverter.getContents(resource));
        }
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

}
