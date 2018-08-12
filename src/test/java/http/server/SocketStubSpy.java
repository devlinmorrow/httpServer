package http.server;

import http.util.IOHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SocketStubSpy extends Socket {

    private IOHelper io;
    private boolean closeWasCalled;

    public SocketStubSpy(String input) {
        io = new IOHelper(input);
        closeWasCalled = false;
    }

    @Override
    public OutputStream getOutputStream() {
        return io.getOut();
    }

    @Override
    public InputStream getInputStream() {
        return io.getIn();
    }

    public String getOutputS() {
        return io.getStringOutput();
    }

    @Override
    public void close() {
        closeWasCalled = true;
        try {
            super.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean wasClosedCalled() {
        return closeWasCalled;
    }

}
