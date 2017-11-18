package wankun.yan.base.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:41
 */
public class StrUtils {
    private static Logger logger = LoggerFactory.getLogger(StrUtils.class);
    public static final String UTF_8 = "UTF-8";
    public static final String GBK = "GBK";
    public static final String ISO_8859_1 = "ISO-8859-1";
    public static final String GB2312 = "GB2312";
    public static final String DOUBLE_QUOTATION = "\"";
    public static final String NUMBERS_REGEX = "-?[\\d]+(,\\d{3})*\\.?[\\d]*";
    public static final String VALUE_HOLDER_PREFIX = "${";
    public static final String VALUE_HOLDER_SUFFIX = "}";

    private StrUtils() {
    }

    public static boolean isEmpty(String str) {
        if(str != null && str.length() != 0) {
            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                if(c != 32 && c != 9 && c != 13 && c != 10) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean notEmpty(String str) {
        return !isEmpty(str);
    }

    public static String trim(String htmlText) {
        if(htmlText == null) {
            return "";
        } else if(htmlText.indexOf(160) == -1) {
            return htmlText.trim();
        } else {
            char[] cArray = htmlText.toCharArray();
            int startIndex = 0;

            int endIndex;
            for(endIndex = 0; endIndex < cArray.length; ++endIndex) {
                if(cArray[endIndex] != 160) {
                    startIndex = endIndex;
                    break;
                }
            }

            endIndex = cArray.length - 1;
            if(endIndex > startIndex) {
                for(int j = cArray.length - 1; j >= 0; --j) {
                    if(cArray[j] != 160) {
                        endIndex = j;
                        break;
                    }
                }

                if(endIndex > startIndex) {
                    char[] newArray = new char[endIndex - startIndex + 1];
                    int x = 0;

                    for(int i = startIndex; i <= endIndex; ++i) {
                        newArray[x++] = cArray[i];
                    }

                    return (new String(newArray)).trim();
                }
            }

            return "";
        }
    }

    public static String trimQuotation(String text) {
        String temp = trim(text);
        if(temp.startsWith("\"")) {
            temp = temp.substring(1);
        }

        if(temp.endsWith("\"")) {
            temp = temp.substring(0, temp.length() - 1);
        }

        return trim(temp);
    }

    public static String valueOf(String str) {
        return str != null?str.trim():"";
    }

    public static boolean isUpperCase(char ch) {
        return ch >= 65 && ch <= 90;
    }

    public static boolean isLowerCase(char ch) {
        return ch >= 97 && ch <= 122;
    }

    public static boolean isEqual(String str1, String str2) {
        return str1 != null && str2 != null?str1.equals(str2):false;
    }

    public static boolean notEqual(String str1, String str2) {
        return !isEqual(str1, str2);
    }

    public static boolean isEqualIgnoreCase(String str1, String str2) {
        return str1 != null && str2 != null?str1.equalsIgnoreCase(str2):false;
    }

    public static boolean notEqualIgnoreCase(String str1, String str2) {
        return !isEqualIgnoreCase(str1, str2);
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static boolean isDifferentCharString(String str) {
        if(str != null && str.length() > 1) {
            char[] charArray = str.toCharArray();
            char firstChar = charArray[0];
            boolean different = false;
            char[] var4 = charArray;
            int var5 = charArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                char c = var4[var6];
                if(firstChar != c) {
                    different = true;
                    break;
                }
            }

            return different;
        } else {
            return false;
        }
    }

    public static StringBuilder append(Object... args) {
        StringBuilder sb = new StringBuilder();
        if(args != null && args.length > 0) {
            Object[] var2 = args;
            int var3 = args.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Object obj = var2[var4];
                sb.append(obj);
            }
        }

        return sb;
    }

    public static <T> String append(List<T> list) {
        if(list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator var2 = list.iterator();

            while(var2.hasNext()) {
                Object obj = var2.next();
                sb.append(obj);
            }

            return sb.toString();
        } else {
            return null;
        }
    }

    public static <T> String appendWithDivider(String divider, List<T> list) {
        if(list != null && !list.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Object obj = var3.next();
                sb.append(obj).append(divider);
            }

            return sb.substring(0, sb.length() - divider.length());
        } else {
            return null;
        }
    }

    public static String appendWithDivider(String divider, Object... objects) {
        if(objects != null && objects.length > 0) {
            StringBuilder sb = new StringBuilder();
            Object[] var3 = objects;
            int var4 = objects.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Object obj = var3[var5];
                sb.append(obj).append(divider);
            }

            return sb.substring(0, sb.length() - divider.length());
        } else {
            return null;
        }
    }

    public static String getMd5(String str) {
        return getMd5(getBytes(str, "UTF-8"));
    }

    public static String getMd5(byte[] bytes) {
        if(bytes != null) {
            try {
                MessageDigest digest = MessageDigest.getInstance("MD5");
                digest.update(bytes);
                byte[] hash = digest.digest();
                StringBuilder sb = new StringBuilder(hash.length * 2);
                byte[] var4 = hash;
                int var5 = hash.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    byte b = var4[var6];
                    if((b & 255) < 16) {
                        sb.append("0");
                    }

                    sb.append(Integer.toHexString(b & 255));
                }

                return sb.toString();
            } catch (Exception var8) {
                logger.error(var8.getMessage(), var8);
            }
        }

        return "";
    }

    public static boolean isPhoneNumber(String text) {
        return text != null && text.length() == 11 && text.matches("^1[3|4|5|7|8][0-9]\\d{8}$");
    }

    public static String replaceMiddleText(String content, String prefix, String suffix, String replacement) {
        return replaceMiddleText(content, prefix, suffix, replacement, true, true);
    }

    public static String replaceMiddleText(String content, String prefix, String suffix, String replacement, boolean lazyPrefix, boolean lazySuffix) {
        int index = lazyPrefix?content.indexOf(prefix):content.lastIndexOf(prefix);
        if(index >= 0) {
            String head = content.substring(0, index);
            String tail = content.substring(index + prefix.length());
            index = lazySuffix?tail.indexOf(suffix):tail.lastIndexOf(suffix);
            if(index >= 0) {
                tail = tail.substring(index + suffix.length());
                StringBuilder sb = new StringBuilder();
                sb.append(head);
                sb.append(prefix);
                sb.append(replacement);
                sb.append(suffix);
                sb.append(tail);
                return sb.toString();
            }
        }

        return null;
    }

    public static String replaceWholeText(String content, String prefix, String suffix, String replacement) {
        return replaceWholeText(content, prefix, suffix, replacement, true, true);
    }

    public static String replaceWholeText(String content, String prefix, String suffix, String replacement, boolean lazyPrefix, boolean lazySuffix) {
        String result = replaceMiddleText(content, prefix, suffix, "!0#9$", lazyPrefix, lazySuffix);
        if(result != null) {
            result = result.replace(prefix + "!0#9$" + suffix, replacement);
        }

        return result;
    }

    public static String replaceText(String template, Map<String, String> replacement) {
        String temp = template;
        Matcher matcher = Pattern.compile("\\{(\\w+)\\}").matcher(template);

        while(matcher.find()) {
            String key = matcher.group(1);
            String value = (String)replacement.get(key);
            if(value != null) {
                temp = temp.replace("{" + key + "}", value);
            }
        }

        return temp;
    }

    public static int getChildTextCount(String parent, String child) {
        Pattern pattern = Pattern.compile(child);
        Matcher matcher = pattern.matcher(parent);

        int count;
        for(count = 0; matcher.find(); ++count) {
            ;
        }

        return count;
    }

    public static String loadPlainXmlContent2(String xmlFilePath) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(xmlFilePath), "UTF-8"));
        StringBuilder sb = new StringBuilder();

        String line;
        while((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }

        br.close();
        String content = sb.toString();
        int anotationStartTagCount = getChildTextCount(content, "<!--");

        for(int i = 0; i < anotationStartTagCount; ++i) {
            content = replaceWholeText(content, "<!--", "-->", "");
        }

        return content;
    }

    public static String loadPlainXmlContent(String xmlFilePath) throws IOException {
        BufferedReader br = FileUtils.getBufferedReader(xmlFilePath, "UTF-8");
        StringBuilder sb = new StringBuilder();
        boolean anotationStarted = false;
        String anotationStartTag = "<!--";
        String anotationEndTag = "-->";

        String line;
        while((line = br.readLine()) != null) {
            int index = line.indexOf(anotationStartTag);
            if(index != -1) {
                sb.append(line.substring(0, index));
                index = line.indexOf(anotationEndTag);
                if(index != -1) {
                    anotationStarted = false;
                    sb.append(line.substring(index + anotationEndTag.length()));
                } else {
                    anotationStarted = true;
                    sb.append("\n");
                }
            } else {
                index = line.indexOf(anotationEndTag);
                if(index != -1) {
                    anotationStarted = false;
                    sb.append(line.substring(index + anotationEndTag.length()));
                } else if(anotationStarted) {
                    sb.append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
        }

        br.close();
        return sb.toString();
    }

    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if(hex.length() <= 1) {
                sb.append('0');
            }

            sb.append(hex);
        }

        return sb.toString();
    }

    public static String intToIp(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }

    public static Map<String, String> getParametersFromUrl(String url) {
        try {
            URL u = new URL(url);
            String query = u.getQuery();
            if(notEmpty(query)) {
                return getKeyValueMap(query);
            }
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
        }

        return new HashMap(0);
    }

    public static Map<String, String> getKeyValueMap(String text) {
        return getKeyValueMap(text, "&", "=");
    }

    public static Map<String, String> getKeyValueMap(String text, String connector, String divider) {
        return getKeyValueMap(text, connector, divider, true);
    }

    public static Map<String, String> getKeyValueMap(String text, String connector, String divider, boolean decodeUrl) {
        HashMap<String, String> map = new HashMap();
        if(notEmpty(text)) {
            StringTokenizer tokenizer = new StringTokenizer(text, connector);

            while(tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken();
                String key = getMiddleText(token, (String)null, divider);
                if(notEmpty(key)) {
                    String value = valueOf(getMiddleText(token, divider, (String)null));
                    if(decodeUrl) {
                        value = CodingUtils.decodeURL(value);
                    }

                    map.put(key, value);
                }
            }
        }

        return map;
    }

    public static String getParameterFromUrl(String url, String parameterName) {
        return (String)getParametersFromUrl(url).get(parameterName);
    }

    public static boolean isChinaMobileNumber(String phoneNumber) {
        if(phoneNumber != null && phoneNumber.length() == 11) {
            HashSet<String> notChinaMobileSet = new HashSet(17);
            notChinaMobileSet.add("130");
            notChinaMobileSet.add("131");
            notChinaMobileSet.add("132");
            notChinaMobileSet.add("145");
            notChinaMobileSet.add("155");
            notChinaMobileSet.add("156");
            notChinaMobileSet.add("185");
            notChinaMobileSet.add("186");
            notChinaMobileSet.add("145");
            notChinaMobileSet.add("133");
            notChinaMobileSet.add("153");
            notChinaMobileSet.add("180");
            notChinaMobileSet.add("181");
            notChinaMobileSet.add("189");
            return !notChinaMobileSet.contains(phoneNumber.substring(0, 3));
        } else {
            return false;
        }
    }

    public static String getMiddleText(String content, String prefix, String suffix) {
        return getMiddleText(content, prefix, suffix, true, true);
    }

    public static String getMiddleText(String content, String prefix, String suffix, boolean lazyPrefix, boolean lazySuffix) {
        if(content != null) {
            int prefixIndex;
            if(prefix != null && suffix != null) {
                prefixIndex = lazyPrefix?content.indexOf(prefix):content.lastIndexOf(prefix);
                if(prefixIndex >= 0) {
                    String tail = content.substring(prefixIndex + prefix.length());
                    int suffixIndex = lazySuffix?tail.indexOf(suffix):tail.lastIndexOf(suffix);
                    if(suffixIndex > 0) {
                        return tail.substring(0, suffixIndex);
                    }
                }
            } else if(prefix == null && suffix != null) {
                prefixIndex = lazySuffix?content.indexOf(suffix):content.lastIndexOf(suffix);
                if(prefixIndex > 0) {
                    return content.substring(0, prefixIndex);
                }
            } else if(prefix != null && suffix == null) {
                prefixIndex = lazyPrefix?content.indexOf(prefix):content.lastIndexOf(prefix);
                if(prefixIndex >= 0) {
                    return content.substring(prefixIndex + prefix.length(), content.length());
                }
            }
        }

        return null;
    }

    public static String getWholeText(String content, String prefix, String suffix, boolean lazyPrefix, boolean lazySuffix) {
        String middleText = getMiddleText(content, prefix, suffix, lazyPrefix, lazySuffix);
        return middleText != null?valueOf(prefix) + middleText + valueOf(suffix):null;
    }

    public static String getWholeText(String content, String prefix, String suffix) {
        return getWholeText(content, prefix, suffix, true, true);
    }

    public static String formatAmount(Double amount, int decimalDigits) {
        return formatAmount(amount, decimalDigits, false);
    }

    public static String formatAmount(Double amount, int decimalDigits, boolean scientific) {
        String result = String.format("%." + decimalDigits + "f", new Object[]{NumberUtils.valueOf(amount)});
        return scientific?formatScientificAmount(result):result;
    }

    public static String formatAmount(Double amount, int decimalDigits, boolean scientific, boolean clearZeroTail) {
        String amountText = formatAmount(amount, decimalDigits, scientific);
        if(clearZeroTail) {
            int index = amountText.indexOf(46);
            if(index > 0) {
                StringBuilder sb = new StringBuilder(amountText);

                char chr;
                while((chr = sb.charAt(sb.length() - 1)) == 48 || chr == 46) {
                    sb.deleteCharAt(sb.length() - 1);
                    if(chr == 46) {
                        break;
                    }
                }

                return sb.toString();
            }
        }

        return amountText;
    }

    private static String formatScientificAmount(String amount) {
        String[] arr = amount.split("\\.");
        String prefix = arr[0];
        int count = prefix.length();
        StringBuilder sb = new StringBuilder();

        int i;
        for(i = 0; i < count; ++i) {
            sb.append(prefix.charAt(i));
            int c = count - 1 - i;
            if(c % 3 == 0 && c != 0) {
                sb.append(",");
            }
        }

        if(arr.length > 1) {
            for(i = 1; i < arr.length; ++i) {
                sb.append(".").append(arr[i]);
            }
        }

        return sb.toString();
    }

    public static String formatAmount(Float amount, int decimalDigits) {
        return formatAmount(amount, decimalDigits, false);
    }

    public static String formatAmount(Float amount, int decimalDigits, boolean scientific) {
        return formatAmount(Double.valueOf(amount.doubleValue()), decimalDigits, scientific);
    }

    public static String formatAmount(Float amount, int decimalDigits, boolean scientific, boolean clearZeroTail) {
        return formatAmount(Double.valueOf(amount.doubleValue()), decimalDigits, scientific, clearZeroTail);
    }

    public static String formatFileSize(Long size, int decimalDigits) {
        if(size != null) {
            if(size.longValue() < 1024L) {
                return size + "B";
            } else {
                double dsize = (double)size.longValue() / 1024.0D;
                if(dsize < 1024.0D) {
                    return formatAmount(Double.valueOf(dsize), decimalDigits) + "K";
                } else {
                    dsize /= 1024.0D;
                    if(dsize < 1024.0D) {
                        return formatAmount(Double.valueOf(dsize), decimalDigits) + "M";
                    } else {
                        dsize /= 1024.0D;
                        return formatAmount(Double.valueOf(dsize), decimalDigits) + "G";
                    }
                }
            }
        } else {
            return "未知";
        }
    }

    public static String getUrlHost(String url) {
        if(url != null) {
            try {
                return (new URL(url)).getHost();
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static String getUrlReferer(String url) {
        if(url != null) {
            try {
                URL u = new URL(url);
                return u.getProtocol() + "://" + u.getHost();
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }
        }

        return null;
    }

    public static String getPlainUrl(String url) {
        try {
            String query = (new URL(url)).getQuery();
            if(!notEmpty(query)) {
                return url;
            }

            int index = url.indexOf(query);
            if(index > 0) {
                return url.substring(0, index).replace("?", "");
            }
        } catch (Exception var3) {
            logger.error(var3.getMessage(), var3);
        }

        return null;
    }

    public static String appendUrlParameter(String url, String name, Object value, boolean cover) {
        return appendUrlParameter(url, new StrUtils.NamedValue(name, value), cover);
    }

    public static String appendUrlParameter(String url, StrUtils.NamedValue namedValue, boolean cover) {
        List<StrUtils.NamedValue> pairList = new ArrayList(1);
        pairList.add(namedValue);
        return appendUrlParameters(url, (List)pairList, cover);
    }

    public static String appendUrlParameters(String url, List<StrUtils.NamedValue> namedValueList, boolean cover) {
        Map<String, Object> params = new HashMap(namedValueList.size());
        Iterator var4 = namedValueList.iterator();

        while(var4.hasNext()) {
            StrUtils.NamedValue namedValue = (StrUtils.NamedValue)var4.next();
            params.put(namedValue.name, namedValue.value);
        }

        return appendUrlParameters(url, (Map)params, cover);
    }

    public static <T> String appendUrlParameters(String url, Map<String, T> params, boolean cover) {
        String plainUrl = getPlainUrl(url);
        if(isEmpty(plainUrl)) {
            return null;
        } else if(params != null && !params.isEmpty()) {
            Map<String, String> existingParamsMap = getParametersFromUrl(url);
            Iterator newParamsIterator = params.entrySet().iterator();

            while(true) {
                String key;
                Object value;
                do {
                    if(!newParamsIterator.hasNext()) {
                        String paramsText = appendUrlParameters(existingParamsMap);
                        return plainUrl + "?" + paramsText;
                    }

                    Map.Entry<String, T> entry = (Map.Entry)newParamsIterator.next();
                    key = (String)entry.getKey();
                    value = entry.getValue();
                } while(!cover && existingParamsMap.containsKey(key));

                existingParamsMap.put(key, value != null?value.toString():"");
            }
        } else {
            return url;
        }
    }

    public static String appendUrlParameters(Map<String, String> params) {
        return appendUrlParameters(params, false);
    }

    public static String appendUrlParameters(Map<String, String> params, boolean urlEncodeValue) {
        Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();

        StringBuilder sb;
        String key;
        String value;
        for(sb = new StringBuilder(); it.hasNext(); sb.append("&").append(key).append("=").append(valueOf(value))) {
            Map.Entry<String, String> entry = (Map.Entry)it.next();
            key = (String)entry.getKey();
            value = (String)entry.getValue();
            if(value != null && urlEncodeValue) {
                value = CodingUtils.encodeURL(value);
            }
        }

        return sb.length() > 0?sb.substring(1):"";
    }

    public static boolean isNumbers(String text, boolean justNumbers) {
        return text != null?(justNumbers?text.matches("[\\d]+"):text.matches("-?[\\d]+(,\\d{3})*\\.?[\\d]*")):false;
    }

    public static boolean isNumbers(String text) {
        return isNumbers(text, true);
    }

    public static String shiftEncode(String str, int[] key) {
        char[] chr = str.toCharArray();

        for(int i = 0; i < chr.length; ++i) {
            swap(chr, i, key[i % key.length] % chr.length);
        }

        return String.valueOf(chr);
    }

    public static String shiftEncode(String str, String key) {
        return shiftEncode(str, toIntArray(key));
    }

    public static String shiftEncode(String str) {
        return shiftEncode(Base64.encode(str), toIntArray(StrUtils.class.getSimpleName()));
    }

    public static String shiftDecode(String str, int[] key) {
        char[] chr = str.toCharArray();

        for(int i = chr.length - 1; i >= 0; --i) {
            swap(chr, i, key[i % key.length] % chr.length);
        }

        return String.valueOf(chr);
    }

    public static String shiftDecode(String str, String key) {
        return shiftDecode(str, toIntArray(key));
    }

    public static String shiftDecode(String str) {
        return Base64.decode(shiftDecode(str, toIntArray(StrUtils.class.getSimpleName())), "UTF-8");
    }

    public static int[] toIntArray(String str) {
        int[] arr = new int[str.length()];

        for(int i = 0; i < str.length(); ++i) {
            arr[i] = str.charAt(i);
        }

        return arr;
    }

    public static void swap(char[] chr, int x, int y) {
        if(x < chr.length && y < chr.length && x != y) {
            char e = chr[x];
            chr[x] = chr[y];
            chr[y] = e;
        }

    }

    public static String getFirstNumberFromText(String text) {
        return getFirstNumberFromText(text, true);
    }

    public static String getFirstNumberFromText(String text, boolean justNumbers) {
        if(notEmpty(text)) {
            String regex = justNumbers?"\\d+":"-?[\\d]+(,\\d{3})*\\.?[\\d]*";
            Matcher numberMatcher = Pattern.compile(regex).matcher(text);
            if(numberMatcher.find()) {
                String number = numberMatcher.group();
                return number.replace(",", "");
            }
        }

        return "";
    }

    public static List<String> getNumberListFromText(String text) {
        return getNumberListFromText(text, true);
    }

    public static List<String> getNumberListFromText(String text, boolean justNumbers) {
        if(!notEmpty(text)) {
            return null;
        } else {
            String regex = justNumbers?"\\d+":"[\\d.]+";
            Matcher numberMatcher = Pattern.compile(regex).matcher(text);
            ArrayList list = new ArrayList();

            while(numberMatcher.find()) {
                list.add(numberMatcher.group());
            }

            return list;
        }
    }

    public static void printList(List list) {
        if(list != null) {
            Iterator var1 = list.iterator();

            while(var1.hasNext()) {
                Object obj = var1.next();
                System.out.println(obj);
            }
        }

    }

    public static void printArray(Object[] arr) {
        if(arr != null) {
            Object[] var1 = arr;
            int var2 = arr.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Object obj = var1[var3];
                System.out.println(obj);
            }
        }

    }

    public static String getRandomCode(int count) {
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < count; ++i) {
            sb.append(NumberUtils.random(0, 9));
        }

        return sb.toString();
    }

    public static String getString(Object object, String defaultValue) {
        return object == null?defaultValue:String.valueOf(object);
    }

    public static String getString(Object object) {
        return getString(object, "");
    }

    public static String setNullIfEmpty(String text) {
        return isEmpty(text)?null:text;
    }

    public static String unicode2StringUnsafe(String s) {
        int n = s.length() / 6;
        StringBuilder sb = new StringBuilder(n);
        int start = 0;
        int i = start;

        for(int j = start + 2; i < n; j += 6) {
            String code = s.substring(j, j + 4);
            char ch = (char)Integer.parseInt(code, 16);
            sb.append(ch);
            ++i;
        }

        return sb.toString();
    }

    public static String unicode2String(String s) {
        if(!notEmpty(s)) {
            return s;
        } else {
            StringBuilder sb = new StringBuilder();
            char[] arr = s.toCharArray();

            for(int i = 0; i < arr.length; ++i) {
                if(arr[i] == 92 && i + 1 <= arr.length - 5 && arr[i + 1] == 117) {
                    StringBuilder builder = new StringBuilder();

                    for(int x = 0; x < 6; ++x) {
                        builder.append(arr[i + x]);
                    }

                    i += 5;
                    sb.append(unicode2StringUnsafe(builder.toString()));
                } else {
                    sb.append(arr[i]);
                }
            }

            return sb.toString();
        }
    }

    public static byte[] getBytes(String text, String charset) {
        if(text != null) {
            try {
                return text.getBytes(charset);
            } catch (UnsupportedEncodingException var3) {
                logger.error(var3.getMessage(), var3);
                return text.getBytes();
            }
        } else {
            return null;
        }
    }

    public static byte[] getBytes(String text) {
        return getBytes(text, "UTF-8");
    }

    public static String objectToString(Object object) {
        return object != null?object.toString():"";
    }

    public static <T> String format(String template, Map<String, T> values) {
        String templateText = template;
        Map.Entry entry;
        String key;
        if(values != null && !values.isEmpty()) {
            for(Iterator it = values.entrySet().iterator(); it.hasNext(); templateText = templateText.replace(key, objectToString(entry.getValue()))) {
                entry = (Map.Entry)it.next();
                key = (String)entry.getKey();
                if(!containsValueHolder(key)) {
                    key = processValueHolder((String)entry.getKey());
                }
            }
        }

        return templateText;
    }

    public static boolean containsValueHolder(String text) {
        return notEmpty(getMiddleText(text, "${", "}"));
    }

    public static String processValueHolder(String key) {
        return "${" + key + "}";
    }

    public static class NamedValue {
        public String name;
        public Object value;

        public NamedValue(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }
}

