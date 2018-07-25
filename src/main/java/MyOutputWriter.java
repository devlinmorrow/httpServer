import java.io.OutputStream;
import java.io.PrintStream;

public class MyOutputWriter {

    public void write(OutputStream outputStream, String output) {
        PrintStream printer = new PrintStream(outputStream);
        printer.print(output);
    }
}
