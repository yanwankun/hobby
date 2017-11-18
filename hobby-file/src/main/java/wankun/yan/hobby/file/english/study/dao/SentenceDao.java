package wankun.yan.hobby.file.english.study.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import wankun.yan.hobby.file.english.study.bean.Sentence;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  21:18
 */
public class SentenceDao extends BaseDao implements SentenceMapper{

    private static SentenceMapper sentenceMapper;

    static {
        sentenceMapper = sqlSession.getMapper(SentenceMapper.class);
    }
    @Override
    public void add(Sentence var1) {
        sentenceMapper.add(var1);
    }

    @Override
    public int update(Sentence var1) {
        return sentenceMapper.update(var1);
    }

    @Override
    public Sentence get(Sentence var1) {
        return sentenceMapper.get(var1);
    }

    @Override
    public Sentence getById(Long var1) {
        return sentenceMapper.getById(1L);
    }

    @Override
    public List<Sentence> query(Sentence var1) {
        return sentenceMapper.query(var1);
    }

    @Override
    public int delete(Long var1) {
        return sentenceMapper.delete(var1);
    }

    @Override
    public int count(Sentence var1) {
        return sentenceMapper.count(var1);
    }
}
