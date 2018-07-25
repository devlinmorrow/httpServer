import org.junit.Test;

import static org.junit.Assert.*;

public class FileReaderTest {

    @Test
    public void readFile1() {
        String file1Contents = "file1 contents";
        FileReader fileReader = new FileReader();

        String actualContents = fileReader.convertFile("/Users/devlin/cob_spec/public/file1");

        assertEquals(file1Contents, actualContents);
    }

    @Test
    public void readFile2() {
        String file2Contents = "file2 contents\n";
        FileReader fileReader = new FileReader();

        String actualContents = fileReader.convertFile("/Users/devlin/cob_spec/public/file2");

        assertEquals(file2Contents, actualContents);
    }
}