package http.ClientConnectors;

import java.util.concurrent.Executor;

public class ExecutorWithNoThreadsSpy implements Executor {

    private int amountExecuteWasCalled;

    public ExecutorWithNoThreadsSpy() {
        amountExecuteWasCalled = 0;
    }

    @Override
    public void execute(Runnable runnable) {
        amountExecuteWasCalled++;
        runnable.run();
    }

    public int getTimesExecuteWasCalled() {
        return amountExecuteWasCalled;
    }
}
