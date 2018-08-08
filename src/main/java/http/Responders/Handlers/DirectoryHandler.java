package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.ContentType;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class DirectoryHandler extends Handler {

    private String rootPath;

    public DirectoryHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.GET);
        addHandledVerb(HTTPVerb.HEAD);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return request.getResourcePath()
                .charAt(request.getResourcePath().length() - 1) == '/';
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        response.setContentTypeHeader(ContentType.HTML);
        response.setBodyContent(getDirectoryContent(new File(rootPath + request.getResourcePath())));
        response.setStatus(ResponseStatus.OK);
        if (headRequest(request)) {
            response.clearAllExceptStatusLine();
        }
        return response;
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
    private boolean headRequest(Request request) {
        return request.getHTTPVerb() == HTTPVerb.HEAD;
    }
}
