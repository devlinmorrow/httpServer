package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;

public class COOKIEHandler implements Handler {

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        if (request.getHeaders().get("Cookie") != null) {
            response.setStatus(ResponseStatus.OK);
            response.setBodyContent(("mmmm " + request.getHeaders().get("Cookie")).getBytes());
        } else {
            File resource = new File(request.getURI());
            String cookieType = getCookieType(resource.getName());
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
