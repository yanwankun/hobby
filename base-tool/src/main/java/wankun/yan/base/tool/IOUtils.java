package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:55
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IOUtils {
    private static Logger logger = LoggerFactory.getLogger(IOUtils.class);

    private IOUtils() {
    }

    public static void close(InputStream inputStream) {
        closeSafe(inputStream);
    }

    public static void close(OutputStream outputStream) {
        closeSafe(outputStream);
    }

    public static void close(Reader reader) {
        closeSafe(reader);
    }

    public static void close(Writer writer) {
        closeSafe(writer);
    }

    private static void closeSafe(Object object) {
        if(object != null) {
            try {
                if(object instanceof InputStream) {
                    ((InputStream)object).close();
                } else if(object instanceof OutputStream) {
                    ((OutputStream)object).close();
                } else if(object instanceof Reader) {
                    ((Reader)object).close();
                } else if(object instanceof Writer) {
                    ((Writer)object).close();
                }
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

    }

    public static String readString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;

        while((line = reader.readLine()) != null) {
            sb.append(line).append('\n');
        }

        return sb.toString();
    }

    public static String readString(InputStream is) throws IOException {
        return readString(is, true);
    }

    public static String readString(InputStream is, boolean close) throws IOException {
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            byte[] datas = new byte[1024];
            boolean var4 = false;

            int len;
            while((len = is.read(datas, 0, datas.length)) != -1) {
                baos.write(datas, 0, len);
            }

            String var5 = baos.toString("UTF-8");
            return var5;
        } catch (UnsupportedEncodingException var9) {
            logger.error(var9.getMessage(), var9);
        } finally {
            close((OutputStream)baos);
            if(close) {
                close(is);
            }

        }

        return null;
    }

    public static byte[] readBytes(InputStream is) throws IOException {
        return readBytes(is, true);
    }

    public static byte[] readBytes(InputStream is, boolean close) throws IOException {
        ByteArrayOutputStream baos = null;

        try {
            baos = new ByteArrayOutputStream();
            byte[] datas = new byte[1024];
            boolean var4 = false;

            int len;
            while((len = is.read(datas, 0, datas.length)) != -1) {
                baos.write(datas, 0, len);
            }

            byte[] var5 = baos.toByteArray();
            return var5;
        } finally {
            close((OutputStream)baos);
            if(close) {
                close(is);
            }

        }
    }
}
