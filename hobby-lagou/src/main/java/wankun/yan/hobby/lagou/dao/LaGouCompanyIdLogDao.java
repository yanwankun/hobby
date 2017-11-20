package wankun.yan.hobby.lagou.dao;

import wankun.yan.hobby.lagou.bean.LaGouCompanyIdLog;

import java.util.List;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  21:18
 */
public class LaGouCompanyIdLogDao extends BaseDao implements LaGouCompanyIdLogMapper {

    private static LaGouCompanyIdLogMapper laGouCompanyIdLogMapper;

    static {
        laGouCompanyIdLogMapper = sqlSession.getMapper(LaGouCompanyIdLogMapper.class);
    }
    @Override
    public void add(LaGouCompanyIdLog var1) {
        laGouCompanyIdLogMapper.add(var1);
    }

    @Override
    public int update(LaGouCompanyIdLog var1) {
        return laGouCompanyIdLogMapper.update(var1);
    }

    @Override
    public LaGouCompanyIdLog get(LaGouCompanyIdLog var1) {
        return laGouCompanyIdLogMapper.get(var1);
    }

    @Override
    public LaGouCompanyIdLog getById(Long var1) {
        return laGouCompanyIdLogMapper.getById(1L);
    }

    @Override
    public List<LaGouCompanyIdLog> query(LaGouCompanyIdLog var1) {
        return laGouCompanyIdLogMapper.query(var1);
    }

    @Override
    public int delete(Long var1) {
        return laGouCompanyIdLogMapper.delete(var1);
    }

    @Override
    public int count(LaGouCompanyIdLog var1) {
        return laGouCompanyIdLogMapper.count(var1);
    }
}
