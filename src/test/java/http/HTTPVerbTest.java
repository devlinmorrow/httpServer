package http;

import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPVerbTest {

    @Test
    public void findRequestedVerb_GET() {
        assertEquals(HTTPVerb.GET, HTTPVerb.find("GET"));
    }

    @Test
    public void findRequestedVerb_NotRecognised() {
        assertEquals(HTTPVerb.NOTRECOGNISED, HTTPVerb.find("get"));
    }

    @Test
    public void getAllowedMethods() {
        assertEquals("GET, HEAD, OPTIONS, PUT, DELETE", HTTPVerb.getAllowedMethods());
    }
}