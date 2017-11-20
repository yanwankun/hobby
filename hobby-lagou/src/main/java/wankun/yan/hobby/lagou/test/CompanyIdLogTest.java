package wankun.yan.hobby.lagou.test;

import wankun.yan.hobby.lagou.bean.LaGouCompanyIdLog;
import wankun.yan.hobby.lagou.dao.LaGouCompanyIdLogDao;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/19
 * Time  0:06
 */
public class CompanyIdLogTest {

    public static void main(String[] args) {
        daoTest();
    }

    private static void daoTest() {
        LaGouCompanyIdLogDao dao = new LaGouCompanyIdLogDao();
        LaGouCompanyIdLog log = dao.getById(1L);
        System.out.println(log);
    }
}
