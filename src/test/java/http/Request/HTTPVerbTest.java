package http.Request;

import org.junit.Test;

import static org.junit.Assert.*;

public class HTTPVerbTest {

    @Test
    public void givenVerbName_getMatchingVerb() {
        assertEquals(HTTPVerb.GET, HTTPVerb.find("GET"));
    }

    @Test
    public void givenVerbNameWhichMatchesNone_returnNotRecognised() {
        assertEquals(HTTPVerb.NOTRECOGNISED, HTTPVerb.find("get"));
    }

    @Test
    public void getAllowedMethods() {
        assertEquals("DELETE, GET, HEAD, OPTIONS, PATCH, PUT", HTTPVerb.getAllowedMethods());
    }
}