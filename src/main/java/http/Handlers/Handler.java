package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;

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

    public void addHandledPathSegment(String pathSegment) {
        handledPathSegments.add(pathSegment);
    }

    public boolean isHandledPathSegment(Request request) {
        for (String pathSegment : handledPathSegments) {
            if (matches(request, pathSegment)) {
                return true;
            }
        }
        return false;
    }

    public boolean handles(Request request) {
        return isHandledVerb(request) && isHandledPathSegment(request);
    }

    private boolean matches(Request request, String pathSegment) {
        return request.getResourcePath().contains(pathSegment);
    }
}
