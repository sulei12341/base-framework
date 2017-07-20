package cn.ydhl.merchant.controller.product;

import cn.ydhl.framework.exception.BindingResultHelper;
import cn.ydhl.framework.exception.model.BusinessException;
import cn.ydhl.framework.mybatis.Page.PageModel;
import cn.ydhl.framework.mybatis.Page.PageResult;
import cn.ydhl.merchant.helper.dto.product.query.ProductQueryDto;
import cn.ydhl.merchant.helper.dto.product.vo.McProductDto;
import cn.ydhl.merchant.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:45
 * @description
 */
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * 新增商品信息
     *
     * @param mcProductDto 商品信息
     * @param bindingResult 验证信息
     */
    @RequestMapping(value = "/insertProduct", method = RequestMethod.POST)
    public void insertProduct(@Validated(McProductDto.Add.class) McProductDto mcProductDto, BindingResult bindingResult) {
        BindingResultHelper.checkAndThrowErrors(bindingResult);
        productService.insetProduct(mcProductDto);
    }

    @RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
    public void updateProduct(@Validated(McProductDto.Edit.class) McProductDto mcProductDto, BindingResult bindingResult) throws BusinessException {
        BindingResultHelper.checkAndThrowErrors(bindingResult);
        productService.updateProduct(mcProductDto);
    }

    @RequestMapping(value = "/getProductById", method = RequestMethod.POST)
    public McProductDto getProductById(@NotNull Long productId) {

        return productService.getProductById(productId);
    }

    /**
     * 分页查询商品信息
     *
     * @param productQueryDto 查询条件
     * @param pageModel 分页对象
     * @return 分页结果
     */
    @RequestMapping(value ="/findProductPage", method = RequestMethod.POST)
    public PageResult<McProductDto> findProductPage(ProductQueryDto productQueryDto, PageModel pageModel) throws Exception {
        //调用service查询商品列表
        return productService.findProductPage(productQueryDto, pageModel);
    }

    /**
     * 根据类型和会员年龄分页查询商品信息
     *
     * @param productQueryDto 查询条件
     * @param pageModel 分页对象
     * @return 分页结果
     */
    @RequestMapping(value = "/findProductPageByTypeAndMemberAge", method = RequestMethod.POST)
    public PageResult<McProductDto> findProductPageByTypeAndMemberAge(ProductQueryDto productQueryDto, PageModel pageModel) {
        return productService.findProductPageByTypeAndMemberAge(productQueryDto, pageModel);
    }


}
