package ru.d10xa.testio;

import org.junit.rules.ExternalResource;

public final class Testio extends ExternalResource {

    public final Input in = new Input();
    public final Output out = new Output();

    private Testio() {
    }

    @Override
    protected void before() throws Throwable {
        in.before();
        out.before();
    }

    @Override
    protected void after() {
        in.after();
        out.after();
    }

}
