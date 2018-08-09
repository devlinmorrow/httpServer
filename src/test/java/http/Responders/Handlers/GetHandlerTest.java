package http.Responders.Handlers;

import http.HardcodedValues;
import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;

import static org.junit.Assert.*;

public class GetHandlerTest {

    private String testFileRoot = "src/test/resources";
    private String mockLogsURI = "src/test/resources/logs";
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";
//
//    @Test
//    public void respondTo_GETRequest_withFileResource() {
//        byte[] dummyFileContents = "file1 contents\n".getBytes();
//        Request mockRequest = new Request(HTTPVerb.GET, "/dummyFile1.txt", emptyHeaders, emptyBody);
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(testFileRoot, formFields);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        Assert.assertEquals(ResponseStatus.OK, mockResponse.getStatus());
//        Assert.assertArrayEquals(ContentType.TXT.getBytesValue(),
//                mockResponse.getHeaders().get(ResponseHeader.CONTENTTYPE));
//        assertArrayEquals(dummyFileContents, mockResponse.getBodyContent());
//    }
//
//    @Test
//    public void respondTo_GETRequest_withNotFound() {
//        Request mockRequest = new Request(HTTPVerb.GET,"/no-file.txt", emptyHeaders, emptyBody);
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(testFileRoot, formFields);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        assertEquals(ResponseStatus.NOTFOUND, mockResponse.getStatus());
//        assertTrue(mockResponse.getHeaders().isEmpty());
//        assertArrayEquals(ResponseStatus.NOTFOUND.getStatusBody(), mockResponse.getBodyContent());
//    }
}