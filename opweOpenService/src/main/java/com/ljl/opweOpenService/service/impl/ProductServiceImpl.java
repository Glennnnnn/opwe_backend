package com.ljl.opweOpenService.service.impl;

import com.ljl.opweOpenService.dao.ProductMapper;
import com.ljl.opweOpenService.entity.constants.ProductExceptionConst;
import com.ljl.opweOpenService.entity.dtos.ProductWithImgDto;
import com.ljl.opweOpenService.entity.pos.ProductPo;
import com.ljl.opweOpenService.exceptions.GeneralException;
import com.ljl.opweOpenService.service.ProductService;
import com.ljl.opweOpenService.utils.SnowflakeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Liu Jialin
 * @Date 2024/6/1 21:45
 * @PackageName com.ljl.opweOpenService.service
 * @ClassName ProductServiceImpl
 * @Description TODO
 * @Version 1.0.0
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    SnowflakeUtil snowflakeUtil;

    @Override
    public void insertSingleProduct(ProductPo productPo) {
        productPo.setProductId(snowflakeUtil.getNextId());
        productMapper.insertSingleProduct(productPo);
    }

    @Override
    public List<ProductPo> queryAllProduct() {
        return productMapper.queryAllProduct();
    }

    @Override
    public ProductPo queryProductStockAndOnHold(Long productId) {
        ProductPo result = productMapper.queryProductStockAndOnHold(productId);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int processProductPreOrder(Long productId, int amount) throws GeneralException {
        //TODO check the logic
        ProductPo postProduct = productMapper.queryProductStockAndOnHold(productId);
        if(postProduct.getProductOnHold() + amount > postProduct.getProductStock()){
            throw new GeneralException(ProductExceptionConst.ExceedAmountException, null, "Exceed product amount limit.");
        }
        productMapper.updateProductOnHold(productId, postProduct.getProductOnHold() + amount);
        return 0;
    }

    @Override
    public void insertProductWithImg(ProductWithImgDto productWithTagsDto, List<MultipartFile> productImgs) {

    }
}
