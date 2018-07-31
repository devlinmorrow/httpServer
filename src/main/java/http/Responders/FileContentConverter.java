package http.Responders;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContentConverter {

    public byte[] getContents(File resource) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return encoded;
    }
}
