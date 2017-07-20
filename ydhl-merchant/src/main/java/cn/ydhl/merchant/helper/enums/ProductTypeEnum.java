package cn.ydhl.merchant.helper.enums;

/**
 * @author Junpeng.Su
 * @create 2017-07-15 下午 11:16
 * @description
 */
public enum ProductTypeEnum {


    /**
     * 实物
     */
    S("实物"),

    /**
     * 购买
     */
    X("虚拟");


    private String desc;

    ProductTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }


}
