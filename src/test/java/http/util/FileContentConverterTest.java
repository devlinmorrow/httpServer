package http.util;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertArrayEquals;

public class FileContentConverterTest {

    @Test
    public void getFileContents() throws IOException {
        String filePath = "src/test/resources/testFile1.txt";
        byte[] file1Contents = "file1 contents\n".getBytes();
        Files.write(Paths.get(filePath), file1Contents);

        FileContentConverter fileContentConverter = new FileContentConverter();
        byte[] actualContents = fileContentConverter.getFullContents(new File
                (filePath));

        assertArrayEquals(file1Contents, actualContents);
    }
}
