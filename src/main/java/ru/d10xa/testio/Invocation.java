package ru.d10xa.testio;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class Invocation {

    private final Task task;
    private SystemIo systemIo;

    public Invocation(Task task) {
        this.task = task;
        this.systemIo = new SystemIo();
    }

    public Invocation setIn(String... in) {
        this.systemIo.setRedirectedIn(
                new ByteArrayInputStream(join(in).getBytes(StandardCharsets.UTF_8))
        );
        return this;
    }

    private String join(String... in) {
        return Testio.join(in);
    }

    public InvocationResult invoke() {
        this.systemIo.redirect();
        try {
            this.task.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.systemIo.restore();
        }
        return new InvocationResult(this.systemIo);
    }


}
