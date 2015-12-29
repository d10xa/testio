package ru.d10xa.testio;

import org.junit.rules.ExternalResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.List;

class Input extends ExternalResource {

    private InputStream originalIn;
    private SystemInMock mockIn;

    @Override
    protected void before() throws Throwable {
        this.originalIn = System.in;
        this.mockIn = new SystemInMock();
        System.setIn(mockIn);
    }

    @Override
    protected void after() {
        System.setIn(originalIn);
    }

    public void leftShift(String... lines) {
        mockIn.setText(joinLines(lines));
    }

    public void leftShift(List<String> lines) {
        mockIn.setText(joinLines(lines.toArray(new String[lines.size()])));
    }

    private String joinLines(String[] lines) {
        StringBuilder sb = new StringBuilder();
        for (String line : lines) {
            sb.append(line).append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }

    private static class SystemInMock extends InputStream {

        private StringReader reader;

        private byte[] separatorBytes;
        private String separator;

        SystemInMock() {
            separator = System.getProperty("line.separator");
            separatorBytes = separator
                .getBytes(Charset.defaultCharset());
        }

        void setText(String text) {
            reader = new StringReader(text);
        }

        @Override
        public int read() throws IOException {
            return reader.read();
        }

        @Override
        public int read(byte[] buffer, int offset, int len) throws IOException {
            if (buffer == null) {
                throw new NullPointerException();
            } else if (offset < 0 || len < 0 || len > buffer.length - offset) {
                throw new IndexOutOfBoundsException();
            } else if (len == 0) {
                return 0;
            } else {
                return readNextLine(buffer, offset, len);
            }
        }

        private int readNextLine(byte[] buffer, int offset, int len) throws IOException {
            int c = read();
            if (c == -1) {
                return -1;
            }
            buffer[offset] = (byte) c;

            int i = 0;
            while (isNotEol(buffer, ++i, len)) {
                byte read = (byte) read();
                if (read == -1) {
                    break;
                } else {
                    buffer[offset + i] = read;
                }
            }
            return i;
        }

        private boolean isNotEol(byte[] bytes, int index, int length) {
            return (index < length) && !isEol(bytes, index);
        }

        private boolean isEol(byte[] buffer, int index) {
            int separatorStartIndex = index - separatorBytes.length;
            return separatorStartIndex >= 0
                && contains(buffer, separatorBytes, separatorStartIndex);
        }

        private boolean contains(byte[] outer, byte[] inner, int outerStartIndex) {
            for (int i = 0; i < inner.length; i++) {
                if (outer[outerStartIndex + i] != inner[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}
