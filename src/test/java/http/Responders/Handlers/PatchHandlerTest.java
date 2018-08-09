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

    private FileContentConverter fileContentConverter;
    private String mockRootPath = "src/test/resources";
    private String resourcePath = "/patchFile.txt";
    private String URI = mockRootPath + resourcePath;
    private File patchFileDummy = new File(URI);

    @Test
    public void handle_PATCHRequest_noEncoding() {
        Request mockRequest = new Request(HTTPVerb.PATCH, resourcePath, new HashMap<>(), "");
        PatchHandler patchHandler = new PatchHandler(mockRootPath);

        Response mockResponse = patchHandler.getResponse(mockRequest);


        assertEquals(ResponseStatus.PRECONDITIONFAILED, mockResponse.getStatus());
    }

    @Test
    public void handle_PATCHRequest_withCorrectEncoding() throws NoSuchAlgorithmException {
        try {
            Files.write(Paths.get(URI), "some stuff".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String shaMock = createSHA1();
        HashMap<String, String> ifMatchHeader = new HashMap<>();
        ifMatchHeader.put("If-Match", shaMock);
        Request mockRequest = new Request(HTTPVerb.PATCH, resourcePath, ifMatchHeader, "patched content");
        PatchHandler patchHandler = new PatchHandler(mockRootPath);

        Response mockResponse = patchHandler.getResponse(mockRequest);

        byte[] newPatchFileContents = fileContentConverter.getFullContents(patchFileDummy);

        assertEquals(ResponseStatus.NOCONTENT, mockResponse.getStatus());
        assertArrayEquals("patched content".getBytes(), newPatchFileContents);
    }

    private String createSHA1() throws NoSuchAlgorithmException {
        fileContentConverter = new FileContentConverter();
        MessageDigest digest = MessageDigest.getInstance("SHA-1");
        digest.reset();
        digest.update(fileContentConverter.getFullContents(patchFileDummy));
        return new BigInteger(1, digest.digest()).toString(16);
    }

}