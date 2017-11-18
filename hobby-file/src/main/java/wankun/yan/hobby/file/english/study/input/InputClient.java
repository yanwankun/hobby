package wankun.yan.hobby.file.english.study.input;

import wankun.yan.base.tool.ObjectUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  0:23
 */
public class InputClient {

    private static String filePath = "E:\\hobby\\hobby\\hobby-file\\src\\main\\resources\\doc.txt";
    public static String readTxtFile(){
        return readTxtFile(filePath);
    }
    public static String readTxtFile(String filePath){
        StringBuilder sb = new StringBuilder();
        try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                    sb.append(lineTxt);
                }
                read.close();
            }else{
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void main(String argv[]){
        String filePath = "E:\\hobby\\hobby\\hobby-file\\src\\main\\resources\\doc.txt";

        System.out.println(readTxtFile(filePath));
    }
}
