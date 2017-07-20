package cn.ydhl.merchant.helper.dto.product;


import cn.ydhl.merchant.helper.dto.BaseDto;
import cn.ydhl.merchant.helper.enums.ProductTypeEnum;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:37
 * @description
 */
public class ProductDto extends BaseDto<Long> {

    private static final long serialVersionUID = -3761675344684204302L;

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
