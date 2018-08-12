package http.Response;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class ResponseTest {

    @Test
    public void clearResponseBody() {
        Response response = new Response();
        response.setBodyContent("example".getBytes());

        response.clearBody();

        assertArrayEquals("".getBytes(), response.getBodyContent());
    }
}