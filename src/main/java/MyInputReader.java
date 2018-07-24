import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyInputReader {

    private String input;

    public String readInput(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            input = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }
}
