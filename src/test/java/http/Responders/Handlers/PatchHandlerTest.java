package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class PatchHandlerTest {

    private static FileContentConverter fileContentConverter;
    private final static String testRootPath = "src/test/resources";
    private final static String resourcePath = "/testPatchFile.txt";
    private final static String fullTestPath = testRootPath + resourcePath;
    private final static File testPatchFile = new File(fullTestPath);

    @Test
    public void getResponse_PATCHRequest_noETAG() {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody = "";
        Response response = getResponseToPatchRequest(emptyHeaders, emptyBody);

        assertEquals(ResponseStatus.PRECONDITIONFAILED, response.getStatus());
    }

    @Test
    public void getResponse_PATCHRequest_withETAG() throws NoSuchAlgorithmException, IOException {
        Files.write(Paths.get(fullTestPath), "some stuff".getBytes());

        String shaDouble = createSHA1();
        HashMap<String, String> ETagHeader = new HashMap<>();
        ETagHeader.put("If-Match", shaDouble);
        String bodyContent = "patched content";

        Response response = getResponseToPatchRequest(ETagHeader, bodyContent);

        byte[] updatedFileContents = fileContentConverter.getFullContents(testPatchFile);

        assertEquals(ResponseStatus.NOCONTENT, response.getStatus());
        assertArrayEquals("patched content".getBytes(), updatedFileContents);
    }

    private String createSHA1() throws NoSuchAlgorithmException {
        fileContentConverter = new FileContentConverter();
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(fileContentConverter.getFullContents(testPatchFile));
        return new BigInteger(1, digest.digest()).toString(16);
    }

    private Response getResponseToPatchRequest(HashMap<String, String> headers, String bodyContent) {
        Request request = new Request(HTTPVerb.PATCH, resourcePath, headers, bodyContent);
        PatchHandler patchHandler = new PatchHandler(testRootPath);
        return patchHandler.getResponse(request);
    }
}