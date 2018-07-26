import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class RequestServicerTest {

    @Test
    public void simpleGET() {
        Request exGETRequest = makeGETRequest("/file1");
        RequestServicer requestServicer = new RequestServicer();

        String expectedResponse = HardcodedValues.HTTPVERSION.getS() + " "
                + ResponseStatus.TWOHUNDRED.getReasonPhrase()
                + Message.BLANKLINE.getS() + "file1 contents";

        String actualResponse = requestServicer.serviceRequest(exGETRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    public void attemptedGET_receives404() {
        Request exGETRequest = makeGETRequest("/no_file_here.txt");
        RequestServicer requestServicer = new RequestServicer();

        String expectedResponse = HardcodedValues.HTTPVERSION.getS() + " "
                + ResponseStatus.FOUROHFOUR.getReasonPhrase()
                + Message.BLANKLINE.getS() + ResponseStatus.FOUROHFOUR.getStatusBody();

        String actualResponse = requestServicer.serviceRequest(exGETRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    private Request makeGETRequest(String URI) {
        Request request = new Request();
        request.setRequestMethod(RequestMethod.GET);
        request.setURI(URI);
        return request;
    }
}