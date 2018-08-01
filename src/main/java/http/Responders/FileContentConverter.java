package http.Responders;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileContentConverter {

    public byte[] getFullContents(File resource) {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(resource.toURI()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return encoded;
    }

    public byte[] getPartialContent(byte[] content, int startingIndex, int endIndex) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for (int i = startingIndex; i <= endIndex; i++) {
            out.write(content[i]);
        }
        return out.toByteArray();
    }
}
