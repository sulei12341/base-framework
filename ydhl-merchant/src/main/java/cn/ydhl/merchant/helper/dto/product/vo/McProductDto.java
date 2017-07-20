package cn.ydhl.merchant.helper.dto.product.vo;


import cn.ydhl.merchant.helper.dto.product.ProductDto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:38
 * @description
 */
public class McProductDto extends ProductDto {

    private static final long serialVersionUID = -128680031956894080L;

    public interface Add {}
    public interface Edit {}

    private String memberName ;


    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    @Override
    @NotNull(groups = { Edit.class})
    public Long getId() {
        return super.getId();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class})
    public String getMemberId() {
        return super.getMemberId();
    }

    @Override
    @NotBlank(groups = {Add.class, Edit.class})
    public String getName() {
        return super.getName();
    }
}
