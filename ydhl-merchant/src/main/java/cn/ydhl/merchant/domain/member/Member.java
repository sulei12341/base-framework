package cn.ydhl.merchant.domain.member;


import cn.ydhl.merchant.domain.base.BaseDomain;

import javax.persistence.Table;

/**
 * @author Junpeng.Su
 * @create 2017-07-16 上午 12:06
 * @description
 */
@Table(name = "member")
public class Member extends BaseDomain<Long> {

    private String name;

    private Integer age ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
