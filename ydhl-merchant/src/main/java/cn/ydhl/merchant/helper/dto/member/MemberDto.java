package cn.ydhl.merchant.helper.dto.member;


import cn.ydhl.merchant.helper.dto.BaseDto;

/**
 * @author Junpeng.Su
 * @create 2017-07-16 上午 12:28
 * @description
 */
public class MemberDto extends BaseDto<Long> {

    private static final long serialVersionUID = -3761675344684204302L;

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
