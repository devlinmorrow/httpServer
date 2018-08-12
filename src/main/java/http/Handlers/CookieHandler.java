package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;

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
        if (request.getResourcePath().contains("eat") && request.getHeaders().get("Cookie") != null) {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent(("mmmm " + request.getHeaders().get("Cookie")).getBytes());
        } else if (request.getResourcePath().contains("eat")) {
            response.setStatus(ResponseStatus.NOTFOUND);
            response.setBodyContent(ResponseStatus.NOTFOUND.getStatusBody());
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
