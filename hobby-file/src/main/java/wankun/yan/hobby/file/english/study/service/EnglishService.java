package wankun.yan.hobby.file.english.study.service;

import wankun.yan.base.tool.ObjectUtils;
import wankun.yan.hobby.file.english.study.bean.Sentence;
import wankun.yan.hobby.file.english.study.input.InputClient;
import wankun.yan.hobby.file.english.study.output.OutPutClient;
import wankun.yan.hobby.file.english.study.process.ProcessClient;

import java.util.Date;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  10:24
 */
public class EnglishService {
    private InputClient inputClient = new InputClient();
    private ProcessClient processClient = new ProcessClient();
    private OutPutClient outPutClient = new OutPutClient();

    public void service() {
        String text = InputClient.readTxtFile();
        text = ProcessClient.deleteAll(text, "<p>", "</p>", "<div>", "</div>", "<span>", "</span>");
        text = ProcessClient.deleStartSample(text, "常用的英文口语赞美");
        text = ProcessClient.replaceAll(text, "\\d+\\.", "_");
        text = ProcessClient.replaceAll(text, "\\d+、", "_");
        Long id = 1L;
        StringBuilder sb = new StringBuilder();
        for (String sentenceString : text.split("_")) {
            if (ObjectUtils.notEmpty(sentenceString)) {

                System.out.println(sentenceString);

                Sentence sentence = new Sentence();
                sentence.setId(id++);
                sentence.setEnglishDesc(ProcessClient.getEnglishDesc(sentenceString));
                sentence.setChineseDesc(ProcessClient.getChineseDesc(sentenceString));
                sentence.setDesc(ProcessClient.getDesc(sentenceString));
                sentence.setCreateTime(new Date());
                sentence.setUpdateTime(new Date());


                OutPutClient.output(sentence);
//                OutPutClient.saveToDatabase(sentence);
                sb.append(sentence + "\r\n\r\n");
                if (ObjectUtils.isEmpty(sentence.getEnglishDesc())) {
                    System.out.println("*********************");
                    System.exit(-1);
                }
            }
        }

        OutPutClient.outputToFile("C:\\Users\\XG\\Desktop\\英语常用句子.txt", sb);
        OutPutClient.output(text);
    }

    public static void main(String[] args) {
        (new EnglishService()).service();
    }

}

