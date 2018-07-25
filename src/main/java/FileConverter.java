import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileConverter {

    public String convertFile(String filePath) {
        List<String> convertedFile = null;
        try {
            convertedFile = Files.readAllLines(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder fileContents = new StringBuilder();
            for (String sentence : convertedFile) {
                fileContents.append(sentence);
        }
        return String.valueOf(fileContents);
    }
}
