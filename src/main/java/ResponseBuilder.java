import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class ResponseBuilder {

    public byte[] makeResponse(Response response) {
        ByteArrayOutputStream responseStream = new ByteArrayOutputStream();
        try {
            responseStream.write(response.getHttpVersion());
            responseStream.write(" ".getBytes());
            responseStream.write(response.getStatus().getPhrase());
            responseStream.write("\n".getBytes());


            for (Map.Entry<byte[], byte[]> entry : response.getHeaders().entrySet()) {
                responseStream.write(entry.getKey());
                responseStream.write(entry.getValue());
                responseStream.write("\n".getBytes());
            }

            responseStream.write("\n".getBytes());
            responseStream.write(response.getBodyContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseStream.toByteArray();
    }
}
