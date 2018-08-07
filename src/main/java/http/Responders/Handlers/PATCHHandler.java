package http.Responders.Handlers;

import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseStatus;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PATCHHandler implements Handler {

    private FileContentConverter fileContentConverter;
    private Request request;

    public PATCHHandler() {
        fileContentConverter = new FileContentConverter();
    }

    @Override
    public Response handle(Request request) {
        Response response = new Response();
        this.request = request;
        String shaActual = createSHA1(request.getURI());
        if (notCorrectETAG(shaActual)) {
            response.setStatus(ResponseStatus.PRECONDITIONFAILED);
        } else {
            try {
                Files.write(Paths.get(request.getURI()), request.getBodyContent().getBytes());
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
