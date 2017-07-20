package cn.ydhl.merchant.helper.example.product;

import cn.ydhl.framework.mybatis.Page.PageModel;
import cn.ydhl.framework.utils.SqlFilter;
import cn.ydhl.framework.utils.StringUtilPlus;
import cn.ydhl.merchant.domain.product.Product;
import cn.ydhl.merchant.helper.dto.product.query.ProductQueryDto;
import com.github.pagehelper.PageHelper;
import tk.mybatis.mapper.entity.Example;
/**
 * @author Junpeng.Su
 * @create 2017-07-15 下午 11:33
 * @description
 */
public class ProductExample {

    public static Example getExample(ProductQueryDto productQueryDto, PageModel pageModel){
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        Example example = new Example(Product.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtilPlus.isNotEmpty(productQueryDto.getName())){
            String inputName = SqlFilter.filterForLike(productQueryDto.getName().toUpperCase());
            criteria.andLike("name", inputName);
        }
        if(productQueryDto.getType() != null){
            criteria.andEqualTo(productQueryDto.getType());
        }
        if(productQueryDto.getMemberIdList() != null){
            criteria.andIn("memberId", productQueryDto.getMemberIdList());
        }
        example.orderBy("id").desc();
        return example ;
    }


}
