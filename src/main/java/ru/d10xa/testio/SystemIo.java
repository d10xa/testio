package ru.d10xa.testio;

import java.io.*;

public class SystemIo {

    private final InputStream systemIn;
    private final PrintStream systemOut;
    private final PrintStream systemErr;

    private InputStream redirectedIn;
    private ByteArrayOutputStream redirectedOut;
    private ByteArrayOutputStream redirectedErr;

    public SystemIo() {
        this.systemIn = System.in;
        this.systemOut = System.out;
        this.systemErr = System.err;
    }

    public void setRedirectedIn(InputStream inputStream) {
        this.redirectedIn = inputStream;
    }

    public String getOutString() {
        return redirectedOut.toString();
    }

    public String getErrString() {
        try {
            return redirectedErr.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getOutErrString() {
        return new StringBuilder(getOutString())
            .append(getErrString())
            .toString();
    }

    public void redirect() {
        this.redirectedOut = new ByteArrayOutputStream();
        this.redirectedErr = new ByteArrayOutputStream();
        if (this.redirectedIn == null) {
            this.redirectedIn = new ByteArrayInputStream("".getBytes());
        }
        System.setOut(new PrintStream(redirectedOut));
        System.setErr(new PrintStream(redirectedErr));
        System.setIn(this.redirectedIn);
    }

    public void restore() {
        System.setIn(this.systemIn);
        System.setOut(this.systemOut);
        System.setErr(this.systemErr);
    }

}
