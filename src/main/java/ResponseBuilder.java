import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ResponseBuilder {

    public byte[] makeResponse(Response response) {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        try {
            responseStream.write(response.getHttpVersion());
            responseStream.write(" ".getBytes());
            responseStream.write(response.getResponseStatus().getReasonPhrase());
            responseStream.write(response.getResponseStatus().getStatusBody());
            responseStream.write("\n\n".getBytes());
            responseStream.write(response.getBodyContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStream.toByteArray();
    }
}
