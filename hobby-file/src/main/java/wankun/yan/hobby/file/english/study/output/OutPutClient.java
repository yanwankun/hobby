package wankun.yan.hobby.file.english.study.output;

import wankun.yan.base.tool.FileUtils;
import wankun.yan.hobby.file.english.study.bean.Sentence;
import wankun.yan.hobby.file.english.study.dao.SentenceDao;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:23
 */
public class OutPutClient {
    private static SentenceDao sentenceDao = new SentenceDao();
    public static void output(Object object) {
        System.out.println(object);
    }

    public static void outputToFile(String filePath, StringBuilder sb) {
        FileUtils.outputStringToFile(filePath, sb.toString());
    }

    public static void saveToDatabase(Sentence sentence) {
        sentenceDao.add(sentence);
    }
}
