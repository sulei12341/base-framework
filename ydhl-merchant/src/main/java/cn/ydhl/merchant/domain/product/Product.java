package cn.ydhl.merchant.domain.product;


import cn.ydhl.merchant.domain.base.BaseDomain;
import cn.ydhl.merchant.helper.enums.ProductTypeEnum;

import javax.persistence.Table;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:03
 * @description
 */
@Table(name = "product")
public class Product extends BaseDomain<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 商品名
     */
    private String name;

    private String memberId;

    private ProductTypeEnum type ;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public ProductTypeEnum getType() {
        return type;
    }

    public void setType(ProductTypeEnum type) {
        this.type = type;
    }
}
