package wankun.yan.hobby.lagou.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created 主要目的是获取拉钩网所有公司的id
 * User  wankunYan
 * Date  2017/11/19
 * Time  0:10
 */
public class CompanyLogPageProcessor implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {

//        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        // 获取公司id
        String urlString = page.getHtml().xpath("/head/link[1]").toString();
        System.out.println("urlString ：" + urlString);
        page.putField("urlString", urlString);
        // 获取公司名称
        System.out.println(page.getHtml());
        String companyName = page.getHtml().xpath("//div[@class='company_main']/h1/a/text()").toString();
        System.out.println("companyName :" + companyName);
        //
//        page.putField("companyName", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
//        page.putField("companyId", "");
//        page.putField("companyId", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
//        if (page.getResultItems().get("name")==null){
//            //skip this page
//            page.setSkip(true);
//        }
//        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
//        Spider.create(new GithubRepoPageProcessor()).addUrl(getAllAbleUrl().toArray(new String[99999])).thread(5).run();
//        Spider.create(new CompanyLogPageProcessor()).addUrl("https://www.lagou.com/gongsi/35166.html").thread(1).run();
//        Spider.create(new CompanyLogPageProcessor()).addUrl("https://github.com/yanwankun/hobby").thread(1).run();
        Spider.create(new CompanyLogPageProcessor()).addUrl("https://www.lagou.com/jobs/list_java?city=杭州").thread(1).run();
//        System.out.println(getCompanyId("https://www.lagou.com/gongsi/3444.html"));
    }

    /**
     *  生成所有有可能的拉钩的公司主页的url
     * @return
     */
    public static List<String> getAllAbleUrl() {
        List<String> allAbleCompanyUrlList = new ArrayList<>(99999);
        for (int i = 1; i < 99999; i++) {
            allAbleCompanyUrlList.add("https://www.lagou.com/gongsi/j" + i + ".html");
        }

        return allAbleCompanyUrlList;
    }

    /**
     * 获取公司的id
     * @param companyUrl
     * @return
     */
    public static Integer getCompanyId(String companyUrl) {
        String temp = companyUrl.substring(companyUrl.lastIndexOf("/") + 1, companyUrl.lastIndexOf("."));
        return new Integer(temp);
    }
}
