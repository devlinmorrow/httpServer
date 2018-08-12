package http.Handlers;

import http.Request.HTTPVerb;
import http.Request.Request;
import http.Response.Response;
import http.Response.ResponseStatus;
import http.util.FileContentConverter;
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

    private final String testRootPath = "src/test/resources";
    private final String patchFilePath = "/testPatchFile.txt";
    private final String fullTestPath = testRootPath + patchFilePath;
    private final File testPatchFile = new File(fullTestPath);
    private final PatchHandler patchHandler = new PatchHandler(testRootPath);
    private final FileContentConverter fileContentConverter = new FileContentConverter();

    @Test
    public void givenPatchRequestWithNoEtag_setPreconditionFailedResponse() {
        HashMap<String, String> emptyHeaders = new HashMap<>();
        String emptyBody = "";
        Request request = new Request(HTTPVerb.PATCH, patchFilePath, emptyHeaders, emptyBody);

        Response response = patchHandler.getResponse(request);

        assertEquals(ResponseStatus.PRECONDITIONFAILED, response.getStatus());
    }

    @Test
    public void givenPathRequestWithCorrectEtag_patchFileAndSetNoContentResponse() throws NoSuchAlgorithmException, IOException {
        overwriteDataToFile("some stuff".getBytes(), fullTestPath);

        String SHAData = createSHA1();
        HashMap<String, String> ETagHeader = new HashMap<>();
        ETagHeader.put("If-Match", SHAData);
        String contentToPatch = "patched content";
        Request request = new Request(HTTPVerb.PATCH, patchFilePath, ETagHeader, contentToPatch);

        Response response = patchHandler.getResponse(request);

        assertEquals(ResponseStatus.NOCONTENT, response.getStatus());
        assertArrayEquals("patched content".getBytes(), fileContentConverter.getFullContents(testPatchFile));
    }

    private void overwriteDataToFile(byte[] content, String path) throws IOException {
        Files.write(Paths.get(path), content);
    }

    private String createSHA1() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(fileContentConverter.getFullContents(testPatchFile));
        return new BigInteger(1, digest.digest()).toString(16);
    }
}