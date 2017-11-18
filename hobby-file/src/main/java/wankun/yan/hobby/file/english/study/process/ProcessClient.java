package wankun.yan.hobby.file.english.study.process;

import java.util.Arrays;
import java.util.List;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:23
 */
public class ProcessClient {
    private String text;
    private static final List<String> EnglishEndCharList = Arrays.asList( ".", "!", "?", "！", "。", "．");
    private static final List<String> ChineseStartCharList = Arrays.asList( "（", "(");
    private static final List<String> ChineseEndCharList = Arrays.asList("）", ")", "！");
    public void process() {

    }

    public static String deleteAll(String source, String ... sample) {
        for (String string : sample) {
            source = source.replace(string, "");
        }
        return source;
    }

    public static String deleStartSample(String source, String sample) {
        if (source.startsWith(sample)) {
            source = source.substring(sample.length());
        }
        return source;
    }

    public static String getEnglishDesc(String sentence) {
        int start = 0;
        int end = -1;

        for (String endChar : EnglishEndCharList) {
            if (sentence.indexOf(endChar) != -1) {
                end = sentence.lastIndexOf(endChar);
                break;
            }
        }

        if (start == -1 || end == -1) {
            return null;
        }
        String temp =  sentence.substring(start, end);
        if (temp.startsWith(".")) {
            temp = temp.substring(1);
        }
        return temp;
    }

    public static String getChineseDesc(String sentence) {
        int start = -1;
        int end = -1;
        for (String startChar : ChineseStartCharList) {
            if (sentence.indexOf(startChar) != -1) {
                start = sentence.lastIndexOf(startChar) ;
                break;
            }
        }
        if (start == -1) {
            for (String startChar : EnglishEndCharList) {
                if (sentence.indexOf(startChar) != -1) {
                    start = sentence.lastIndexOf(startChar);
                    break;
                }
            }
        }

        for (String endChar : ChineseEndCharList) {
            if (sentence.indexOf(endChar) != -1) {
                end = sentence.lastIndexOf(endChar);
                break;
            }
        }
        if (end == -1) {
            end = sentence.length();
        }

        if (start == -1 || end == -1) {
            return null;
        }
        return sentence.substring(start + 1, end);
    }

    public static String getDesc(String sentence) {
        int start = sentence.indexOf("【");
        int end = sentence.indexOf("】");
        if (start == -1 || end == -1) {
            return null;
        }
        return sentence.substring(start + 1, end);
    }

    public static String replaceAll(String source, String regex, String replacement) {
        return source.replaceAll(regex, replacement);
    }
    public static void main(String[] args) {
        String text = "<p>常用的英文口语赞美</p><p>1. you look great today.（你今天看上去很棒。）【每天都可以用！】</p><p>2. you did a good job. （你干得非常好。）【国际最通用的表扬！】</p><p>3. we're so proud of you.（我们十分为你骄傲。）";
        System.out.println(deleteAll(text, "<p>", "</p>"));
        System.out.println(replaceAll(text, "[\\.0-9]", "_"));
        String sentence = "1. I see． 我明白了。";
        System.out.println(getEnglishDesc(sentence));
        System.out.println(getChineseDesc(sentence));
        System.out.println(getDesc(sentence));
    }

}
