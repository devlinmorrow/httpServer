import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MyInputReader {

    public String readInput(InputStream in) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder thing = new StringBuilder();
        String s;
        try {
            s = reader.readLine();
            thing.append(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.valueOf(thing);
    }
}
