package cn.ydhl.merchant.helper.dto.product.query;


import cn.ydhl.merchant.helper.dto.product.ProductDto;

import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-07-15 下午 11:36
 * @description
 */
public class ProductQueryDto extends ProductDto {

    private static final long serialVersionUID = -128680031956894080L;


    private String memberName ;

    private List<Long> memberIdList ;

    private Integer memberAge ;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public List<Long> getMemberIdList() {
        return memberIdList;
    }

    public void setMemberIdList(List<Long> memberIdList) {
        this.memberIdList = memberIdList;
    }

    public Integer getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(Integer memberAge) {
        this.memberAge = memberAge;
    }

}
