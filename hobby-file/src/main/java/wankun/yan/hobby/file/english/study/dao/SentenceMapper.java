package wankun.yan.hobby.file.english.study.dao;


import wankun.yan.hobby.file.english.study.bean.Sentence;

import java.util.List;

public interface SentenceMapper {
    void add(Sentence var1);

    int update(Sentence var1);

    Sentence get(Sentence var1);

    Sentence getById(Long var1);

    List<Sentence> query(Sentence var1);

    int delete(Long var1);

    int count(Sentence var1);
}
