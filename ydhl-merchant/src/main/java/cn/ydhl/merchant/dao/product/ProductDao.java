package cn.ydhl.merchant.dao.product;

import cn.ydhl.framework.mybatis.mapper.ExpandMapper;
import cn.ydhl.merchant.domain.product.Product;
import cn.ydhl.merchant.helper.dto.product.vo.McProductDto;
import cn.ydhl.merchant.helper.enums.ProductTypeEnum;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:32
 * @description
 */
public interface ProductDao extends ExpandMapper<Product> {


    @Select("select p.id, p.name, m.name as memberName from product p, "
            + "member m where p.member_id = m.id and p.type = #{type} and m.age > #{memberAge} ")
    Page<McProductDto> findProductByTypeAndMemberAge(@Param("type") ProductTypeEnum type, @Param("memberAge") Integer memberAge);

}
