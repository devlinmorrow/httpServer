package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;

import java.util.ArrayList;

public abstract class Handler {

    private final ArrayList<HTTPVerb> handledVerbs = new ArrayList<>();
    private final ArrayList<String> handledPathSegments = new ArrayList<>();

    public abstract Response getResponse(Request request);

    public void addHandledVerb(HTTPVerb httpVerb) {
        handledVerbs.add(httpVerb);
    }

    public boolean isHandledVerb(Request request) {
        return handledVerbs.contains(request.getHTTPVerb());
    }

    public void addHandledPathSegment(String path) {
        handledPathSegments.add(path);
    }

    public boolean isHandledPathSegment(Request request) {
        for (String handledPathSegment : handledPathSegments) {
            if (request.getResourcePath().contains(handledPathSegment)) {
                return true;
            }
        }
        return false;
    }

    public boolean handles(Request request) {
        return isHandledVerb(request) && isHandledPathSegment(request);
    }
}
