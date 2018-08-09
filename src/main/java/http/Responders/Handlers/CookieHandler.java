package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class CookieHandler extends Handler {

    public CookieHandler() {
        addHandledVerb(HTTPVerb.GET);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return (request.getResourcePath().toLowerCase().contains("cookie"));
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        if (request.getHeaders().get("Cookie") != null) {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent(("mmmm " + request.getHeaders().get("Cookie")).getBytes());
        } else {
            String cookieType = getCookieType(request.getResourcePath());
            response.setStatus(ResponseStatus.OK);
            response.setCookieHeader(cookieType);
            response.setBodyContent("Eat".getBytes());
        }
        return response;
    }

    private String getCookieType(String fullURI) {
        int paramStart = fullURI.indexOf("?");
        String fullCookie = fullURI.substring(paramStart + 1);
        return fullCookie.split("=")[1];
    }
}
