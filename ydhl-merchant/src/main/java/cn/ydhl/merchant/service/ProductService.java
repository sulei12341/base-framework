package cn.ydhl.merchant.service;


import cn.ydhl.framework.exception.model.BusinessException;
import cn.ydhl.framework.mybatis.Page.PageModel;
import cn.ydhl.framework.mybatis.Page.PageResult;
import cn.ydhl.merchant.helper.dto.product.query.ProductQueryDto;
import cn.ydhl.merchant.helper.dto.product.vo.McProductDto;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:33
 * @description
 */
public interface ProductService {

    void insetProduct(McProductDto mcProductDto);

    void updateProduct(McProductDto mcProductDto) throws BusinessException;

    McProductDto getProductById(Long id);

    /**
     * 分页查询商品信息
     *
     * @param productQueryDto 查询条件
     * @param pageModel 分页对象
     * @return 分页结果
     */
    PageResult<McProductDto> findProductPage(ProductQueryDto productQueryDto, PageModel pageModel);

    PageResult<McProductDto> findProductPageByTypeAndMemberAge(ProductQueryDto productQueryDto, PageModel pageModel);

}
