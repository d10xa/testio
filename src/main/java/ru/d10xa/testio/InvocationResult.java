package ru.d10xa.testio;

public class InvocationResult {

    private final SystemIo systemIo;

    InvocationResult(SystemIo systemIo) {
        this.systemIo = systemIo;
    }

    public String getOutErrString() {
        return this.systemIo.getOutErrString();
    }

    public String getOutString() {
        return this.systemIo.getOutString();
    }

    public String getErrString() {
        return this.systemIo.getErrString();
    }

}
