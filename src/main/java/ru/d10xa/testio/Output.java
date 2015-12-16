package ru.d10xa.testio;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Output implements TestRule {

    private ByteArrayOutputStream copyOut;
    private ByteArrayOutputStream copyErr;
    private ByteArrayOutputStream copyOutErr;
    private CaptureOutputStream captureOut;
    private CaptureOutputStream captureErr;

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                captureOutput();
                try {
                    base.evaluate();
                }
                finally {
                    releaseOutput();
                }
            }
        };
    }

    private void captureOutput() {
        this.copyOut = new ByteArrayOutputStream();
        this.copyErr = new ByteArrayOutputStream();
        this.copyOutErr = new ByteArrayOutputStream();

        this.captureOut = new CaptureOutputStream(System.out, this.copyOut, this.copyOutErr);
        this.captureErr = new CaptureOutputStream(System.err, this.copyErr, this.copyOutErr);

        System.setOut(new PrintStream(this.captureOut));
        System.setErr(new PrintStream(this.captureErr));
    }

    private void releaseOutput() {
        System.setOut(this.captureOut.getOriginal());
        System.setErr(this.captureErr.getOriginal());
        this.copyOut = null;
        this.copyErr = null;
        this.copyOutErr = null;
    }

    public void flush() {
        try {
            this.captureOut.flush();
            this.captureErr.flush();
        }
        catch (IOException ex) {
            // ignore
        }
    }

    @Override
    public String toString() {
        flush();
        return this.copyOutErr.toString();
    }

    public String outToString() {
        flush();
        return this.copyOut.toString();
    }

    public String errToString() {
        flush();
        return this.copyErr.toString();
    }

    private static class CaptureOutputStream extends OutputStream {

        private final PrintStream original;

        private final OutputStream[] copies;

        CaptureOutputStream(PrintStream original, OutputStream... copies) {
            this.original = original;
            this.copies = copies;
        }

        @Override
        public void write(int b) throws IOException {
            for (OutputStream copy : this.copies) {
                copy.write(b);
            }
            this.original.write(b);
            this.original.flush();
        }

        @Override
        public void write(byte[] b) throws IOException {
            write(b, 0, b.length);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            for (OutputStream copy : this.copies) {
                copy.write(b, off, len);
            }
            this.original.write(b, off, len);
        }

        public PrintStream getOriginal() {
            return this.original;
        }

        @Override
        public void flush() throws IOException {
            for (OutputStream copy : this.copies) {
                copy.flush();
            }
            this.original.flush();
        }
    }

}
