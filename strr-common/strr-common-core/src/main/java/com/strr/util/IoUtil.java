package com.strr.util;

import java.io.*;

public class IoUtil {
    public static int copy(InputStream in, OutputStream out) throws IOException {
        int count = (int) in.transferTo(out);
        out.flush();
        return count;
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (Exception ignored) {
            }
        }

    }

    public static ByteArrayOutputStream read(InputStream in, boolean isClose) throws RuntimeException {
        ByteArrayOutputStream out;
        if (in instanceof FileInputStream) {
            try {
                out = new ByteArrayOutputStream(in.available());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            out = new ByteArrayOutputStream();
        }
        try {
            copy(in, out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (isClose) {
                close(in);
            }
        }
        return out;
    }

    public static byte[] readBytes(InputStream in) throws RuntimeException {
        return readBytes(in, true);
    }

    public static byte[] readBytes(InputStream in, boolean isClose) throws RuntimeException {
        return read(in, isClose).toByteArray();
    }
}
