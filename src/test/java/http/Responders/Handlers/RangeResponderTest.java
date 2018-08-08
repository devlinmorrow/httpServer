package http.Responders.Handlers;

import http.Requesters.HTTPVerb;
import http.Requesters.Request;
import http.Responders.FileContentConverter;
import http.Responders.Response;
import http.Responders.ResponseHeader;
import http.Responders.ResponseStatus;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.Assert.*;

public class RangeResponderTest {

    private String mockRootPath = "src/test/resources";
    private String resourcePath = "/dummyFile1.txt";
    private String mockFileURI = mockRootPath + resourcePath;
    private HashMap<String, String> emptyHeaders = new HashMap<>();
    private String emptyBody = "";

//    @Test
//    public void respondTo_GETRequest_PartialContent() {
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(mockRootPath, formFields);
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=0-4");
//        Request mockRequest = new Request(HTTPVerb.GET, resourcePath, rangeHeader, emptyBody);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        FileContentConverter fileContentConverter = new FileContentConverter();
//        File file = new File(mockFileURI);
//        byte[] fullContent = fileContentConverter.getFullContents(file);
//        byte[] expectedContent = Arrays.copyOfRange(fullContent, 0, 5);
//
//        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
//        assertArrayEquals("bytes 0-4/15".getBytes(), mockResponse.getHeaders()
//                .get(ResponseHeader.CONTENTRANGE));
//        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
//    }
//
//    @Test
//    public void respondTo_GETRequest_PartialContent_onlyStartIndex() {
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(mockRootPath, formFields);
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=11-");
//        Request mockRequest = new Request(HTTPVerb.GET, resourcePath, rangeHeader, emptyBody);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        FileContentConverter fileContentConverter = new FileContentConverter();
//        File file = new File(mockFileURI);
//        byte[] fullContent = fileContentConverter.getFullContents(file);
//        byte[] expectedContent = Arrays.copyOfRange(fullContent, 11, fullContent.length);
//
//        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
//        assertArrayEquals("bytes 11-14/15".getBytes(), mockResponse.getHeaders()
//                .get(ResponseHeader.CONTENTRANGE));
//        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
//    }
//
//    @Test
//    public void respondTo_GETRequest_PartialContent_onlyEndNumber() {
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(mockRootPath, formFields);
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=-6");
//        Request mockRequest = new Request(HTTPVerb.GET, resourcePath, rangeHeader, emptyBody);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        FileContentConverter fileContentConverter = new FileContentConverter();
//        File file = new File(mockFileURI);
//        byte[] fullContent = fileContentConverter.getFullContents(file);
//        byte[] expectedContent = Arrays.copyOfRange(fullContent, 9, fullContent.length);
//
//        assertEquals(ResponseStatus.PARTIALCONTENT, mockResponse.getStatus());
//        assertArrayEquals("bytes 9-14/15".getBytes(), mockResponse.getHeaders()
//                .get(ResponseHeader.CONTENTRANGE));
//        assertArrayEquals(expectedContent, mockResponse.getBodyContent());
//    }
//
//    @Test
//    public void respondTo_GETRequest_PartialContent_RangeNotSatisfiable() {
//        FormFields formFields = new FormFields(new HashMap<>());
//        GetHandler getHandler = new GetHandler(mockRootPath, formFields);
//        HashMap<String, String> rangeHeader = new HashMap<>();
//        rangeHeader.put("Range","bytes=10-20");
//        Request mockRequest = new Request(HTTPVerb.GET, resourcePath, rangeHeader, emptyBody);
//
//        Response mockResponse = getHandler.getResponse(mockRequest);
//
//        assertEquals(ResponseStatus.RANGENOTSATISFIABLE, mockResponse.getStatus());
//        assertArrayEquals("bytes */15".getBytes(), mockResponse.getHeaders()
//                .get(ResponseHeader.CONTENTRANGE));
//    }
}