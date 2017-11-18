package wankun.yan.hobby.file.english.study.bean;

import wankun.yan.base.tool.ObjectUtils;

import java.util.Date;

/**
 * Created
 * User  wankunYan
 * Date  2017/11/18
 * Time  10:56
 */
public class Sentence {
    private Long id;
    private String englishDesc;
    private String chineseDesc;
    private String desc;
    private Date createTime;
    private Date updateTime;


    @Override
    public String toString() {
        String temp = "序号：" + id + "\r\n" +
                "英语：" + englishDesc + "\r\n" +
                "中文：" + chineseDesc + "\r\n";
        if (ObjectUtils.notEmpty(this.desc)) {
            temp += "描述: " + desc + "\r\n";
        }
        return temp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnglishDesc() {
        return englishDesc;
    }

    public void setEnglishDesc(String englishDesc) {
        this.englishDesc = englishDesc;
    }

    public String getChineseDesc() {
        return chineseDesc;
    }

    public void setChineseDesc(String chineseDesc) {
        this.chineseDesc = chineseDesc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
