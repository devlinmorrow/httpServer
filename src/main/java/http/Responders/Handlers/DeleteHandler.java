package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DeleteHandler extends Handler {

    private String rootPath;
    private Request request;
    private Response response;

    public DeleteHandler(String rootPath) {
        this.rootPath = rootPath;
        addHandledVerb(HTTPVerb.DELETE);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return request.getResourcePath().toLowerCase().contains("txt");
    }

    @Override
    public Response getResponse(Request request) {
        this.request = request;
        response = new Response();
        if (Files.exists(Paths.get(rootPath + request.getResourcePath()))) {
            deleteResource();
        } else {
            setNotFound();
        }
        return response;
    }

    private void setNotFound() {
        response.setStatus(ResponseStatus.NOTFOUND);
        response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
    }

    private void deleteResource() {
        try {
            Files.delete(Paths.get(rootPath + request.getResourcePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setStatus(ResponseStatus.OK);
    }
}
