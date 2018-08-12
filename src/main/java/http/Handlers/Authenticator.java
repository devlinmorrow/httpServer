package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;

import java.util.Base64;

public class Authenticator {

    private Request request;
    private String authorizationHeader;

    public Authenticator() {
        authorizationHeader = "Authorization";
    }

    public String handleLogs(Request request) {
        this.request = request;
        if (notGET()) {
            return "NotAllowed";
        } else {
            if (unAuthorised()) {
                return "Unauthorised";
            } else {
                return "GET";
            }
        }
    }

    private boolean unAuthorised() {
        boolean unAuthorised = true;
        if ((request.getHeaders().get(authorizationHeader)) != null) {
            String credentials = request.getHeaders().get(authorizationHeader)
                    .replace("Basic ", "");
            String decodedCreds = (new String(Base64.getDecoder().decode(credentials.getBytes())));
            if ("admin:hunter2".equals(decodedCreds)) {
                unAuthorised = false;
            }
        }
        return unAuthorised;
    }

    private boolean notGET() {
        return request.getHTTPVerb() != HTTPVerb.GET;
    }
}
