package wankun.yan.base.tool;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:56
 */
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodingUtils {
    private static Logger logger = LoggerFactory.getLogger(CodingUtils.class);

    private CodingUtils() {
    }

    public static String encodeURL(String url) {
        return encodeURL(url, "UTF-8");
    }

    public static String decodeURL(String url) {
        return decodeURL(url, "UTF-8");
    }

    public static String encodeURL(String url, String charset) {
        try {
            return URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException var3) {
            logger.error(var3.getMessage(), var3);
            return url;
        }
    }

    public static String decodeURL(String url, String charset) {
        try {
            return URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException var3) {
            logger.error(var3.getMessage(), var3);
            return url;
        }
    }

    public static String iso88591ToUTF8(String str) {
        if(str == null) {
            return "";
        } else {
            try {
                return StrUtils.trim(new String(str.getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException var2) {
                logger.error(var2.getMessage(), var2);
                return str;
            }
        }
    }
}

