import org.junit.Test;

import static org.junit.Assert.*;

public class MyOutputWriterTest {

    @Test
    public void write() {
        String exampleInput ="HTTP/1.1 200 OK\nContent-Type: text/plain" +
                "\nContent-Length: 12\n\nHello world!";
        IOHelper socketIO = new IOHelper("");
        MyOutputWriter myOutputWriter = new MyOutputWriter();

        myOutputWriter.write(socketIO.getOut(), exampleInput);

        assertEquals(exampleInput, socketIO.getStringOutput());
    }
}