package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:58
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class Base64 {
    static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    static char pad = 61;

    private Base64() {
    }

    public static String encode(String str) throws RuntimeException {
        byte[] bytes = StrUtils.getBytes(str);
        return encode(bytes);
    }

    public static String encode(String str, String charset) throws RuntimeException {
        byte[] bytes;
        try {
            bytes = str.getBytes(charset);
        } catch (UnsupportedEncodingException var4) {
            throw new RuntimeException("Unsupported charset: " + charset, var4);
        }

        return encode(bytes);
    }

    public static byte[] decode(String str) throws RuntimeException {
        byte[] bytes;
        try {
            bytes = str.getBytes("ASCII");
        } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException("ASCII is not supported!", var3);
        }

        byte[] decoded = decode(bytes);
        return decoded;
    }

    public static String decode(String str, String charset) throws RuntimeException {
        byte[] bytes;
        try {
            bytes = str.getBytes("ASCII");
        } catch (UnsupportedEncodingException var6) {
            throw new RuntimeException("ASCII is not supported!", var6);
        }

        byte[] decoded = decode(bytes);

        try {
            return new String(decoded, charset);
        } catch (UnsupportedEncodingException var5) {
            throw new RuntimeException("Unsupported charset: " + charset, var5);
        }
    }

    public static String encode(byte[] bytes) throws RuntimeException {
        return encode(bytes, 0);
    }

    public static String encode(byte[] bytes, int wrapAt) throws RuntimeException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            encode((InputStream)inputStream, (OutputStream)outputStream, wrapAt);
        } catch (IOException var15) {
            throw new RuntimeException("Unexpected I/O error", var15);
        } finally {
            try {
                inputStream.close();
            } catch (Exception var14) {
                ;
            }

            try {
                outputStream.close();
            } catch (Exception var13) {
                ;
            }

        }

        return new String(outputStream.toByteArray());
    }

    public static byte[] decode(byte[] bytes) throws RuntimeException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            decode((InputStream)inputStream, (OutputStream)outputStream);
        } catch (IOException var14) {
            throw new RuntimeException("Unexpected I/O error", var14);
        } finally {
            try {
                inputStream.close();
            } catch (Exception var13) {
                ;
            }

            try {
                outputStream.close();
            } catch (Exception var12) {
                ;
            }

        }

        return outputStream.toByteArray();
    }

    public static void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
        encode((InputStream)inputStream, (OutputStream)outputStream, 0);
    }

    public static void encode(InputStream inputStream, OutputStream outputStream, int wrapAt) throws IOException {
        Base64.Base64OutputStream aux = new Base64.Base64OutputStream(outputStream, wrapAt);
        copy(inputStream, aux);
        aux.commit();
    }

    public static void decode(InputStream inputStream, OutputStream outputStream) throws IOException {
        copy(new Base64.Base64InputStream(inputStream), outputStream);
    }

    public static void encode(File source, File target, int wrapAt) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            encode((InputStream)inputStream, (OutputStream)outputStream, wrapAt);
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception var14) {
                    ;
                }
            }

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception var13) {
                    ;
                }
            }

        }

    }

    public static void encode(File source, File target) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            encode((InputStream)inputStream, (OutputStream)outputStream);
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception var13) {
                    ;
                }
            }

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception var12) {
                    ;
                }
            }

        }

    }

    public static void decode(File source, File target) throws IOException {
        InputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new FileInputStream(source);
            outputStream = new FileOutputStream(target);
            decode((InputStream)inputStream, (OutputStream)outputStream);
        } finally {
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception var13) {
                    ;
                }
            }

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception var12) {
                    ;
                }
            }

        }

    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] b = new byte[1024];

        int len;
        while((len = inputStream.read(b)) != -1) {
            outputStream.write(b, 0, len);
        }

    }

    public static class Base64OutputStream extends OutputStream {
        private OutputStream outputStream;
        private int buffer;
        private int bytecounter;
        private int linecounter;
        private int linelength;

        public Base64OutputStream(OutputStream outputStream) {
            this(outputStream, 76);
        }

        public Base64OutputStream(OutputStream outputStream, int wrapAt) {
            this.outputStream = null;
            this.buffer = 0;
            this.bytecounter = 0;
            this.linecounter = 0;
            this.linelength = 0;
            this.outputStream = outputStream;
            this.linelength = wrapAt;
        }

        public void write(int b) throws IOException {
            int value = (b & 255) << 16 - this.bytecounter * 8;
            this.buffer |= value;
            ++this.bytecounter;
            if(this.bytecounter == 3) {
                this.commit();
            }

        }

        public void close() throws IOException {
            this.commit();
            this.outputStream.close();
        }

        protected void commit() throws IOException {
            if(this.bytecounter > 0) {
                if(this.linelength > 0 && this.linecounter == this.linelength) {
                    this.outputStream.write(StrUtils.getBytes("\r\n"));
                    this.linecounter = 0;
                }

                char b1 = Base64.chars.charAt(this.buffer << 8 >>> 26);
                char b2 = Base64.chars.charAt(this.buffer << 14 >>> 26);
                char b3 = this.bytecounter < 2?Base64.pad:Base64.chars.charAt(this.buffer << 20 >>> 26);
                char b4 = this.bytecounter < 3?Base64.pad:Base64.chars.charAt(this.buffer << 26 >>> 26);
                this.outputStream.write(b1);
                this.outputStream.write(b2);
                this.outputStream.write(b3);
                this.outputStream.write(b4);
                this.linecounter += 4;
                this.bytecounter = 0;
                this.buffer = 0;
            }

        }
    }

    public static class Base64InputStream extends InputStream {
        private InputStream inputStream;
        private int[] buffer;
        private int bufferCounter = 0;
        private boolean eof = false;

        public Base64InputStream(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        public int read() throws IOException {
            if(this.buffer == null || this.bufferCounter == this.buffer.length) {
                if(this.eof) {
                    return -1;
                }

                this.acquire();
                if(this.buffer.length == 0) {
                    this.buffer = null;
                    return -1;
                }

                this.bufferCounter = 0;
            }

            return this.buffer[this.bufferCounter++];
        }

        private void acquire() throws IOException {
            char[] four = new char[4];
            int i = 0;

            do {
                int b = this.inputStream.read();
                if(b == -1) {
                    if(i != 0) {
                        throw new IOException("Bad base64 stream");
                    }

                    this.buffer = new int[0];
                    this.eof = true;
                    return;
                }

                char c = (char)b;
                if(Base64.chars.indexOf(c) == -1 && c != Base64.pad) {
                    if(c != 13 && c != 10) {
                        throw new IOException("Bad base64 stream");
                    }
                } else {
                    four[i++] = c;
                }
            } while(i < 4);

            boolean padded = false;

            for(i = 0; i < 4; ++i) {
                if(four[i] != Base64.pad) {
                    if(padded) {
                        throw new IOException("Bad base64 stream");
                    }
                } else if(!padded) {
                    padded = true;
                }
            }

            byte l;
            if(four[3] == Base64.pad) {
                if(this.inputStream.read() != -1) {
                    throw new IOException("Bad base64 stream");
                }

                this.eof = true;
                if(four[2] == Base64.pad) {
                    l = 1;
                } else {
                    l = 2;
                }
            } else {
                l = 3;
            }

            int aux = 0;

            for(i = 0; i < 4; ++i) {
                if(four[i] != Base64.pad) {
                    aux |= Base64.chars.indexOf(four[i]) << 6 * (3 - i);
                }
            }

            this.buffer = new int[l];

            for(i = 0; i < l; ++i) {
                this.buffer[i] = aux >>> 8 * (2 - i) & 255;
            }

        }

        public void close() throws IOException {
            this.inputStream.close();
        }
    }
}

