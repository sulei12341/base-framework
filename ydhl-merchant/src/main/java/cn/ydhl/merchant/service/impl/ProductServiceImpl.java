package cn.ydhl.merchant.service.impl;

import cn.ydhl.framework.exception.model.BusinessException;
import cn.ydhl.framework.mybatis.Page.PageModel;
import cn.ydhl.framework.mybatis.Page.PageResult;
import cn.ydhl.framework.utils.SqlFilter;
import cn.ydhl.merchant.dao.member.MemberDao;
import cn.ydhl.merchant.dao.product.ProductDao;
import cn.ydhl.merchant.domain.member.Member;
import cn.ydhl.merchant.domain.product.Product;
import cn.ydhl.merchant.helper.constant.ProductConstantModule;
import cn.ydhl.merchant.helper.dto.product.query.ProductQueryDto;
import cn.ydhl.merchant.helper.dto.product.vo.McProductDto;
import cn.ydhl.merchant.helper.example.product.ProductExample;
import cn.ydhl.merchant.service.ProductService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junpeng.Su
 * @create 2017-07-14 下午 5:33
 * @description
 */
@Service("productService")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao ;

    @Autowired
    private MemberDao memberDao ;

    @Override
    public void insetProduct(McProductDto mcProductDto) {
        Product product = new Product();
        product.setMemberId(mcProductDto.getMemberId());
        product.setName(mcProductDto.getName());
        product.setType(mcProductDto.getType());
        productDao.insert(product);
    }

    @Override
    @Transactional(rollbackFor = BusinessException.class)
    public void updateProduct(McProductDto mcProductDto) throws BusinessException {
        Product product = productDao.selectByPrimaryKey(mcProductDto.getId());
        if (product == null) {
            throw new BusinessException(ProductConstantModule.NOT_FOUND.getCode(), ProductConstantModule.NOT_FOUND.getMessage());
        }
        product.setMemberId(mcProductDto.getMemberId());
        product.setName(mcProductDto.getName());
        product.setType(mcProductDto.getType());
        productDao.updateByPrimaryKey(product);
    }

    @Override
    public McProductDto getProductById(Long id) {
        Product product = productDao.selectByPrimaryKey(id);
        McProductDto mcProductDto = new McProductDto();
        mcProductDto.setId(product.getId());
        mcProductDto.setMemberId(product.getMemberId());
        mcProductDto.setName(product.getName());
        mcProductDto.setType(product.getType());
        return mcProductDto;
    }

    @Override
    public PageResult<McProductDto> findProductPage(ProductQueryDto productQueryDto, PageModel pageModel) {
        List<Long> memberIdList = memberDao.getMemberIdListByName(SqlFilter.filterForLike(productQueryDto.getMemberName().toUpperCase()));
        productQueryDto.setMemberIdList(memberIdList);
        Example example = ProductExample.getExample(productQueryDto, pageModel);
        Page<Product> productPage = (Page<Product>) productDao.selectByExample(example);
        List<McProductDto> mcProductDtoList = new ArrayList<>();
        for (Product product : productPage  ) {
            McProductDto mcProductDto = new McProductDto();
            mcProductDto.setId(product.getId());
            Member member = memberDao.selectByPrimaryKey(Long.valueOf(product.getMemberId()));
            mcProductDto.setMemberName(member.getName());
            mcProductDto.setName(product.getName());
            mcProductDto.setType(product.getType());
            mcProductDtoList.add(mcProductDto);
        }
        return new PageResult<>(mcProductDtoList, productPage.getTotal());
    }

    @Override
    public PageResult<McProductDto> findProductPageByTypeAndMemberAge(ProductQueryDto productQueryDto, PageModel pageModel){
        PageHelper.startPage(pageModel.getPage(),pageModel.getPageSize());
        Page<McProductDto> mcProductDtoPage = productDao.findProductByTypeAndMemberAge(productQueryDto.getType(), productQueryDto.getMemberAge());
        return new PageResult<>(mcProductDtoPage, mcProductDtoPage.getTotal());
    }

}
