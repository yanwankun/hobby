package wankun.yan.hobby.file.english.study.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;


/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  22:31
 */
public class BaseDao {
    public static InputStream inputStream;
    public static SqlSessionFactory sqlSessionFactory;
    public static SqlSession sqlSession;

    static {
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
