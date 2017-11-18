package wankun.yan.hobby.file.english.study.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import wankun.yan.hobby.file.english.study.bean.Sentence;
import wankun.yan.hobby.file.english.study.dao.SentenceDao;
import wankun.yan.hobby.file.english.study.dao.SentenceMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  21:01
 */
public class SentenceTest {
    public static void main(String [] args) throws IOException {
        SentenceMapperTest();
    }

    public static void SentenceMapperTest()  throws IOException {
//        使用类加载器，加载mybatis的配置文件
        InputStream inputStream= Resources.getResourceAsStream("mybatis-config.xml");

//        构件sqlSession工厂
        SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession=sqlSessionFactory.openSession();
        SentenceMapper sentenceMapper=sqlSession.getMapper(SentenceMapper.class);
        Sentence sentence = sentenceMapper.getById(1L);
    }

    public static void SentenceDaoTest() {
        SentenceDao sentenceDao = new SentenceDao();
        Sentence sentence = sentenceDao.getById(1L);
        System.out.println(sentence);
    }
}
