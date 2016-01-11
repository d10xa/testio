package ru.d10xa.testio;

import org.junit.rules.ExternalResource;

import java.util.List;

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

    public void leftShift(String... lines) {
        in.leftShift(lines);
    }

    public void leftShift(List<String> lines) {
        in.leftShift(lines);
    }

    @Override
    public String toString() {
        return out.toString();
    }
}
