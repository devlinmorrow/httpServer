package http.Responders;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResponseTest {

    @Test
    public void clearBody() {
        Response response = new Response();
        response.setBodyContent("example".getBytes());

        response.clearBody();

        assertArrayEquals("".getBytes(), response.getBodyContent());
    }
}