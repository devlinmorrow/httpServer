package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.FileContentConverter;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PatchHandler extends Handler {

    private String rootPath;
    private FileContentConverter fileContentConverter;
    private Request request;

    public PatchHandler(String rootPath) {
        this.rootPath = rootPath;
        fileContentConverter = new FileContentConverter();
        addHandledVerb(HTTPVerb.PATCH);
    }

    @Override
    public boolean isHandledPathSegment(Request request) {
        return true;
    }

    @Override
    public Response getResponse(Request request) {
        Response response = new Response();
        this.request = request;
        String shaActual = createSHA1(rootPath + request.getResourcePath());
        if (notCorrectETAG(shaActual)) {
            response.setStatus(ResponseStatus.PRECONDITIONFAILED);
        } else {
            try {
                Files.write(Paths.get(rootPath + request.getResourcePath()), request.getBodyContent().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            response.setStatus(ResponseStatus.NOCONTENT);
        }
        return response;
    }

    private boolean notCorrectETAG(String shaActual) {
        return !request.getHeaders().containsKey("If-Match") ||
                !request.getHeaders().get("If-Match").equals(shaActual);
    }

    private String createSHA1(String fileURI) {
        fileContentConverter = new FileContentConverter();
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        digest.reset();
        File shaFile = new File(fileURI);
        digest.update(fileContentConverter.getFullContents(shaFile));
        return new BigInteger(1, digest.digest()).toString(16);
    }
}
