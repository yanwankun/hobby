package wankun.yan.hobby.lagou.dao;

import wankun.yan.hobby.lagou.bean.LaGouCompanyIdLog;

import java.util.List;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  23:56
 */
public interface LaGouCompanyIdLogMapper {
    void add(LaGouCompanyIdLog var1);

    int update(LaGouCompanyIdLog var1);

    LaGouCompanyIdLog get(LaGouCompanyIdLog var1);

    LaGouCompanyIdLog getById(Long var1);

    List<LaGouCompanyIdLog> query(LaGouCompanyIdLog var1);

    int delete(Long var1);

    int count(LaGouCompanyIdLog var1);
}
